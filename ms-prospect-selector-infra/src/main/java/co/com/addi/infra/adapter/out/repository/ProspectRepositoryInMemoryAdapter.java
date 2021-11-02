package co.com.addi.infra.adapter.out.repository;

import java.util.UUID;

import co.com.addi.infra.common.TimerUtil;
import co.com.addi.port.out.repository.prospect.ProspectRepositoryPort;
import co.com.addi.port.out.repository.prospect.dto.ProspectCreatedEvent;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProspectRepositoryInMemoryAdapter implements ProspectRepositoryPort {

	@Override
	public Either<UseCaseError, UUID> saveProspect(ProspectCreatedEvent prospectCreatedEvent) {
		log.info("[ProspectRepositoryInMemoryAdapter] saveProspect()");
		return Either.right(UUID.randomUUID())
				.mapLeft(error -> buildUseCaseError())
				.peek(uuid -> TimerUtil.randomSleep())
				.peek(uuid -> log.info("[ProspectRepositoryInMemoryAdapter] saveProspect is done"));
	}
	
	private UseCaseError buildUseCaseError() {
		return UseCaseError.builder()
				.errorCode("41")
				.errorDescription("Error saving prospect")
				.build();
	}

}
