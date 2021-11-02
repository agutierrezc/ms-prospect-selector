package co.com.addi.infra.adapter.out.leadqualification;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import co.com.addi.port.out.leadqualification.dto.LeadQualificationResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public class LeadQualificationRandomAdapterTest {
	
	private LeadQualificationRandomAdapter leadQualificationRandomAdapter;
	
	@Before
	public void setup() {
		leadQualificationRandomAdapter = new LeadQualificationRandomAdapter();
	}
	
	@Test
	public void getLeadQualificationShouldRight() {
		Either<UseCaseError, LeadQualificationResponse> response = leadQualificationRandomAdapter.getLeadQualification(UUID.randomUUID());
		assertEquals(true, response.isRight());
		assertEquals(true, response.get().getScore()>0);
		assertEquals(true, response.get().getScore()<100);
	}
}
