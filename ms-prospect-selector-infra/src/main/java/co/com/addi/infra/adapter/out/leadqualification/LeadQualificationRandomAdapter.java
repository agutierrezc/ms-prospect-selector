package co.com.addi.infra.adapter.out.leadqualification;

import java.util.UUID;

import co.com.addi.infra.common.TimerUtil;
import co.com.addi.port.out.leadqualification.LeadQualificationPort;
import co.com.addi.port.out.leadqualification.dto.LeadQualificationResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeadQualificationRandomAdapter implements LeadQualificationPort {

	@Override
	public Either<UseCaseError, LeadQualificationResponse> getLeadQualification(UUID leadId) {
		log.info("[LeadQualificationRandomAdapter] getLeadQualification()");
		return Try.of(() -> Math.random() * 100)
				.map(number -> number.intValue())
				.map(this::buildLeadQualificationResponse)
				.toEither(buildUseCaseError())
				.peek(leadQualificationResponse -> TimerUtil.randomSleep())
				.peek(leadQualificationResponse -> log.info("[LeadQualificationRandomAdapter] getLeadQualification is done"));
	}
	
	private LeadQualificationResponse buildLeadQualificationResponse(int score) {
		return LeadQualificationResponse.builder()
				.score(score)
				.build();
	}

	private UseCaseError buildUseCaseError() {
		return UseCaseError.builder()
				.errorCode("31")
				.errorDescription("Error getting Lead Qualification")
				.build();
	}
}
