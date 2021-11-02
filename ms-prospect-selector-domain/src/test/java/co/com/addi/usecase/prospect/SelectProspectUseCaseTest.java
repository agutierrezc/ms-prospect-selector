package co.com.addi.usecase.prospect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.com.addi.port.out.leadqualification.LeadQualificationPort;
import co.com.addi.port.out.leadqualification.dto.LeadQualificationResponse;
import co.com.addi.port.out.nacionalarchives.NacionalArchivesPort;
import co.com.addi.port.out.nacionalarchives.dto.NacionalArchivesResponse;
import co.com.addi.port.out.nacionalregistry.NacionalRegistryPort;
import co.com.addi.port.out.nacionalregistry.dto.NacionalRegistryResponse;
import co.com.addi.port.out.repository.prospect.ProspectRepositoryPort;
import co.com.addi.port.out.repository.prospect.dto.ProspectCreatedEvent;
import co.com.addi.usecase.common.UseCaseError;
import co.com.addi.usecase.prospect.command.ProspectCreatedResponse;
import co.com.addi.usecase.prospect.command.SelectProspectCommand;
import io.vavr.control.Either;

public class SelectProspectUseCaseTest {

	private SelectProspectUseCase selectProspectUseCase;
	@Mock
	private NacionalRegistryPort nacionalRegistryPort;
	@Mock
	private NacionalArchivesPort nacionalArchivesPort;
	@Mock
	private LeadQualificationPort leadQualificationPort;
	@Mock
	private ProspectRepositoryPort prospectRepository;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		selectProspectUseCase = new SelectProspectUseCase(nacionalRegistryPort, nacionalArchivesPort,
				leadQualificationPort, prospectRepository);
	}
	
	@Test
	public void selectProspectshouldReturnRight() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponseFromProspect(selectProspectCommand);
		NacionalArchivesResponse nacionalArchivesResponse = buildEmptyNacionalArchivesResponse();
		LeadQualificationResponse leadQualificationResponse = buildLeadQualificationResponseHigher();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		when(leadQualificationPort.getLeadQualification(selectProspectCommand.getLeadId())).thenReturn(Either.right(leadQualificationResponse));
		when(prospectRepository.saveProspect(any(ProspectCreatedEvent.class))).thenReturn(Either.right(UUID.randomUUID()));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isRight());
		assertNotNull(response.get().getProstectId());
	}
	
	@Test
	public void nacionalRegistryShouldNotMatch() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponse();
		NacionalArchivesResponse nacionalArchivesResponse = buildEmptyNacionalArchivesResponse();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("01", response.getLeft().getErrorCode());
	}
	
	@Test
	public void nacionalRegistryShouldFailed() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalArchivesResponse nacionalArchivesResponse = buildEmptyNacionalArchivesResponse();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.left(UseCaseError.builder().errorCode("10").build()));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("10", response.getLeft().getErrorCode());
	}
	
	@Test
	public void nacionalArchivesShouldReturnRecords() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponseFromProspect(selectProspectCommand);
		NacionalArchivesResponse nacionalArchivesResponse = buildNacionalArchivesResponseRecords();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("02", response.getLeft().getErrorCode());
	}
	
	@Test
	public void nacionalArchivesShouldFailed() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponseFromProspect(selectProspectCommand);
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.left(UseCaseError.builder().errorCode("20").build()));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("20", response.getLeft().getErrorCode());
	}
	
	@Test
	public void nacionalRegistryShouldNotMatchAndNacionalArchivesRecords() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponse();
		NacionalArchivesResponse nacionalArchivesResponse = buildNacionalArchivesResponseRecords();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("01", response.getLeft().getErrorCode());
	}
	
	@Test
	public void nacionalRegistryShouldFailedAndNacionalArchivesShouldFailed() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.left(UseCaseError.builder().errorCode("10").build()));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.left(UseCaseError.builder().errorCode("20").build()));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("10", response.getLeft().getErrorCode());
	}
	
	@Test
	public void leadQualificationShouldResponseLess() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponseFromProspect(selectProspectCommand);
		NacionalArchivesResponse nacionalArchivesResponse = buildEmptyNacionalArchivesResponse();
		LeadQualificationResponse leadQualificationResponse = buildLeadQualificationResponseLess();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		when(leadQualificationPort.getLeadQualification(selectProspectCommand.getLeadId())).thenReturn(Either.right(leadQualificationResponse));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("03", response.getLeft().getErrorCode());
	}
	
	@Test
	public void leadQualificationShouldFailed() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponseFromProspect(selectProspectCommand);
		NacionalArchivesResponse nacionalArchivesResponse = buildEmptyNacionalArchivesResponse();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		when(leadQualificationPort.getLeadQualification(selectProspectCommand.getLeadId())).thenReturn(Either.left(UseCaseError.builder().errorCode("30").build()));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("30", response.getLeft().getErrorCode());
	}
	
	@Test
	public void prospectRepositoryShouldFailed() {
		SelectProspectCommand selectProspectCommand = buildSelectProspectCommand();
		NacionalRegistryResponse nacionalRegistryResponse = buildNacionalRegistryResponseFromProspect(selectProspectCommand);
		NacionalArchivesResponse nacionalArchivesResponse = buildEmptyNacionalArchivesResponse();
		LeadQualificationResponse leadQualificationResponse = buildLeadQualificationResponseHigher();
		when(nacionalRegistryPort.getPersonalInformation(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalRegistryResponse));
		when(nacionalArchivesPort.getNacionalRecord(selectProspectCommand.getDocumentId())).thenReturn(Either.right(nacionalArchivesResponse));
		when(leadQualificationPort.getLeadQualification(selectProspectCommand.getLeadId())).thenReturn(Either.right(leadQualificationResponse));
		when(prospectRepository.saveProspect(any(ProspectCreatedEvent.class))).thenReturn(Either.left(UseCaseError.builder().errorCode("40").build()));
		Either<UseCaseError, ProspectCreatedResponse> response = selectProspectUseCase.executeUseCase(selectProspectCommand);
		assertEquals(true, response.isLeft());
		assertEquals("40", response.getLeft().getErrorCode());
	}

	private LeadQualificationResponse buildLeadQualificationResponseHigher() {
		return LeadQualificationResponse.builder()
				.score(61)
				.build();
	}
	
	private LeadQualificationResponse buildLeadQualificationResponseLess() {
		return LeadQualificationResponse.builder()
				.score(60)
				.build();
	}

	private NacionalArchivesResponse buildNacionalArchivesResponseRecords() {
		return NacionalArchivesResponse.builder()
				.records(List.of("record"))
				.build();
	}
	
	private NacionalArchivesResponse buildEmptyNacionalArchivesResponse() {
		return NacionalArchivesResponse.builder()
				.records(List.of())
				.build();
	}
	
	private NacionalRegistryResponse buildNacionalRegistryResponse() {
		return NacionalRegistryResponse.builder()
				.documentId("223423")
				.firstname("firstname")
				.lastName("lastName")
				.email("email")
				.birthdate(LocalDate.now())
				.build();
	}

	private NacionalRegistryResponse buildNacionalRegistryResponseFromProspect(
			SelectProspectCommand selectProspectCommand) {
		return NacionalRegistryResponse.builder()
				.documentId(selectProspectCommand.getDocumentId())
				.firstname(selectProspectCommand.getName())
				.lastName(selectProspectCommand.getLastName())
				.email(selectProspectCommand.getEmail())
				.birthdate(selectProspectCommand.getBirthdate())
				.build();
	}

	private SelectProspectCommand buildSelectProspectCommand() {
		return SelectProspectCommand.builder()
				.leadId(UUID.randomUUID())
				.documentId("documentId")
				.name("name")
				.lastName("lastName")
				.email("email")
				.birthdate(LocalDate.now())
				.build();
	}

}
