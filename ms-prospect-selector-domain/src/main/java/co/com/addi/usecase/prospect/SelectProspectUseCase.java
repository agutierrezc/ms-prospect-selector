package co.com.addi.usecase.prospect;

import java.util.UUID;

import co.com.addi.port.out.leadqualification.LeadQualificationPort;
import co.com.addi.port.out.nacionalarchives.NacionalArchivesPort;
import co.com.addi.port.out.nacionalregistry.NacionalRegistryPort;
import co.com.addi.port.out.repository.prospect.ProspectRepositoryPort;
import co.com.addi.port.out.repository.prospect.dto.ProspectCreatedEvent;
import co.com.addi.usecase.common.UseCaseError;
import co.com.addi.usecase.prospect.command.ProspectCreatedResponse;
import co.com.addi.usecase.prospect.command.SelectProspectCommand;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SelectProspectUseCase {
	
	private static final double MINIMUN_SCORE = 60;
	
	private final NacionalRegistryPort nacionalRegistryPort;
	private final NacionalArchivesPort nacionalArchivesPort;
	private final LeadQualificationPort leadQualificationPort;
	private final ProspectRepositoryPort prospectRepository;
	
	public Either<UseCaseError, ProspectCreatedResponse> executeUseCase(SelectProspectCommand selectProspectCommand) {
		
		log.info("[SelectProspectUseCase] executeUseCase()");
		return Future.of(() -> validatePersonalInformation(selectProspectCommand))
				.zip(Future.of(() -> validateJudicalRecords(selectProspectCommand.getDocumentId())))
				.await()
				.toEither(buildUseCaseError("04", "Unxpected Error"))
				.filterOrElse(tuple -> tuple._1().isRight(), tuple -> tuple._1().getLeft())
				.filterOrElse(tuple -> tuple._2().isRight(), tuple -> tuple._2().getLeft())
				.flatMap(ignored -> validateLeadScore(selectProspectCommand.getLeadId()))
				.map(leadId -> buildProspectCreatedEvent(selectProspectCommand))
				.flatMap(prospectCreatedEvent -> prospectRepository.saveProspect(prospectCreatedEvent))
				.map(this::buildProspect);
	}
	
	private ProspectCreatedResponse buildProspect(UUID prospectId) {
		return ProspectCreatedResponse.builder()
				.prostectId(prospectId)
				.build();
	}
	
	private ProspectCreatedEvent buildProspectCreatedEvent(SelectProspectCommand selectProspectCommand) {
		return ProspectCreatedEvent.builder()
				.leadId(selectProspectCommand.getLeadId())
				.name(selectProspectCommand.getName())
				.lastName(selectProspectCommand.getLastName())
				.documentId(selectProspectCommand.getDocumentId())
				.email(selectProspectCommand.getEmail())
				.birthdate(selectProspectCommand.getBirthdate())
				.build();
	}
	
	private Either<UseCaseError, UUID> validateLeadScore(UUID leadId){
		return leadQualificationPort.getLeadQualification(leadId)
				.filterOrElse(score -> MINIMUN_SCORE < score.getScore(), score -> buildUseCaseError("03", "Lead's score is under 60"))
				.map(score -> leadId);
	}
	
	private Either<UseCaseError, Boolean> validatePersonalInformation(SelectProspectCommand selectProspectCommand){
		return nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())
				.filterOrElse(nacionalRegistryResponse -> selectProspectCommand.getDocumentId().equalsIgnoreCase(nacionalRegistryResponse.getDocumentId()),
						ignored -> buildUseCaseError("01", "Lead's personal information does not match"))
				.filterOrElse(nacionalRegistryResponse -> selectProspectCommand.getName().equalsIgnoreCase(nacionalRegistryResponse.getFirstname()),
						ignored -> buildUseCaseError("01", "Lead's personal information does not match"))
				.filterOrElse(nacionalRegistryResponse -> selectProspectCommand.getLastName().equalsIgnoreCase(nacionalRegistryResponse.getLastName()),
						ignored -> buildUseCaseError("01", "Lead's personal information does not match"))
				.filterOrElse(nacionalRegistryResponse -> selectProspectCommand.getEmail().equalsIgnoreCase(nacionalRegistryResponse.getEmail()),
						ignored -> buildUseCaseError("01", "Lead's personal information does not match"))
				.filterOrElse(nacionalRegistryResponse -> selectProspectCommand.getBirthdate().isEqual(nacionalRegistryResponse.getBirthdate()),
						ignored -> buildUseCaseError("01", "Lead's personal information does not match"))
				.map(nacionalRegistryResponse -> true);
	}
	
	private Either<UseCaseError, Boolean> validateJudicalRecords(String documentId) {
		return nacionalArchivesPort.getNacionalRecord(documentId)
				.filterOrElse(nacionalArchivesResponse -> nacionalArchivesResponse.getRecords().isEmpty(),
						ignored -> buildUseCaseError("02", "Lead has judicial Record"))
				.map(nacionalArchivesResponse -> true);
	}
	
	private UseCaseError buildUseCaseError(String code, String desc) {
		return UseCaseError.builder()
				.errorCode(code)
				.errorDescription(desc)
				.build();
	}
}
