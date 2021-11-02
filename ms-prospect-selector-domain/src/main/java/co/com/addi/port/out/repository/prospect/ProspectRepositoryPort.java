package co.com.addi.port.out.repository.prospect;

import java.util.UUID;

import co.com.addi.port.out.repository.prospect.dto.ProspectCreatedEvent;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public interface ProspectRepositoryPort {

	Either<UseCaseError, UUID> saveProspect(ProspectCreatedEvent prospectCreatedEvent);
}
