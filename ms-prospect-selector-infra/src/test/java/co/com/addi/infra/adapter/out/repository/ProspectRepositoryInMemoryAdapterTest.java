package co.com.addi.infra.adapter.out.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import co.com.addi.port.out.repository.prospect.dto.ProspectCreatedEvent;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public class ProspectRepositoryInMemoryAdapterTest {
	
	private ProspectRepositoryInMemoryAdapter prospectRepositoryInMemoryAdapter;
	
	@Before
	public void setup() {
		prospectRepositoryInMemoryAdapter = new ProspectRepositoryInMemoryAdapter();
	}

	@Test
	public void saveProspectShouldRight() {
		ProspectCreatedEvent prospectCreatedEvent = buildProspectCreatedEvent();
		Either<UseCaseError, UUID> response = prospectRepositoryInMemoryAdapter.saveProspect(prospectCreatedEvent);
		assertEquals(true, response.isRight());
		assertNotNull(response.get());
	}

	private ProspectCreatedEvent buildProspectCreatedEvent() {
		return ProspectCreatedEvent.builder()
				.documentId("documentId")
				.name("name")
				.lastName("lastName")
				.build();
	}
}
