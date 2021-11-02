package co.com.addi.infra.adapter.out.nacionalregistry;

import java.util.Map;

import co.com.addi.infra.adapter.out.nacionalregistry.dto.PersonalInformation;
import co.com.addi.infra.common.TimerUtil;
import co.com.addi.port.out.nacionalregistry.NacionalRegistryPort;
import co.com.addi.port.out.nacionalregistry.dto.NacionalRegistryResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class NacionalRegistryInMemoryAdpater implements NacionalRegistryPort {

	private final Map<String, PersonalInformation> PersonalInformationInMemoryDB;
	
	@Override
	public Either<UseCaseError, NacionalRegistryResponse> getPersonalInformation(String documentId) {
		log.info("[NacionalRegistryInMemoryAdpater] getPersonalInformation()");
		return Try.of(() -> PersonalInformationInMemoryDB.get(documentId))
				.map(personalInformation -> buildNacionalRegistryResponse(personalInformation))
				.toEither(buildUseCaseError())
				.peek(personalInformation -> TimerUtil.randomSleep())
				.peek(personalInformation -> log.info("[NacionalRegistryInMemoryAdpater] getPersonalInformation is done"));
	}
	
	private UseCaseError buildUseCaseError() {
		return UseCaseError.builder()
				.errorCode("11")
				.errorDescription("Error getting data from Nacional Registry")
				.build();
	}
	
	private NacionalRegistryResponse buildNacionalRegistryResponse(PersonalInformation personalInformation) {
		return NacionalRegistryResponse.builder()
				.documentId(personalInformation.getDocumentId())
				.firstname(personalInformation.getFirstname())
				.lastName(personalInformation.getLastname())
				.email(personalInformation.getEmail())
				.birthdate(personalInformation.getBirthdate())
				.build();
	}
}
