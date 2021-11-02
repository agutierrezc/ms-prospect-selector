package co.com.addi.port.out.nacionalregistry;

import co.com.addi.port.out.nacionalregistry.dto.NacionalRegistryResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public interface NacionalRegistryPort {

	Either<UseCaseError, NacionalRegistryResponse> getPersonalInformation(String documentId);
}
