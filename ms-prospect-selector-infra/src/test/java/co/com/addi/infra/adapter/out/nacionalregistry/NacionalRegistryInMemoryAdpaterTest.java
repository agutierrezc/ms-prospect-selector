package co.com.addi.infra.adapter.out.nacionalregistry;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.com.addi.infra.adapter.out.nacionalregistry.dto.PersonalInformation;
import co.com.addi.port.out.nacionalregistry.dto.NacionalRegistryResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public class NacionalRegistryInMemoryAdpaterTest {
	
	private NacionalRegistryInMemoryAdpater nacionalRegistryInMemoryAdpater;
	
	@Mock
	private Map<String, PersonalInformation> personalInformationInMemory;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		nacionalRegistryInMemoryAdpater = new NacionalRegistryInMemoryAdpater(personalInformationInMemory);
	}
	
	@Test
	public void getPersonalInformationShouldReturnRight() {
		String documentId = "457898";
		PersonalInformation personalInformation = buildPersonalInformation(documentId);
		when(personalInformationInMemory.get(documentId)).thenReturn(personalInformation);
		Either<UseCaseError, NacionalRegistryResponse> response = nacionalRegistryInMemoryAdpater.getPersonalInformation(documentId);
		assertEquals(true, response.isRight());
		assertEquals(personalInformation.getFirstname(), response.get().getFirstname());
		assertEquals(personalInformation.getLastname(), response.get().getLastName());
		assertEquals(personalInformation.getEmail(), response.get().getEmail());
		assertEquals(personalInformation.getBirthdate(), response.get().getBirthdate());
		assertEquals(personalInformation.getDocumentId(), response.get().getDocumentId());
	}
	
	@Test
	public void personalInformationNotFound() {
		String documentId = "457898";
		when(personalInformationInMemory.get(documentId)).thenThrow(new NullPointerException());
		Either<UseCaseError, NacionalRegistryResponse> response = nacionalRegistryInMemoryAdpater.getPersonalInformation(documentId);
		assertEquals(true, response.isLeft());
		assertEquals("11", response.getLeft().getErrorCode());
	}

	private PersonalInformation buildPersonalInformation(String documentId) {
		return PersonalInformation.builder()
				.documentId(documentId)
				.firstname("firstname")
				.lastname("lastname")
				.email("email")
				.birthdate(LocalDate.now())
				.build();
	}
}
