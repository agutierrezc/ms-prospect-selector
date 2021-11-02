package co.com.addi.port.out.nacionalarchives;

import co.com.addi.port.out.nacionalarchives.dto.NacionalArchivesResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public interface NacionalArchivesPort {

	Either<UseCaseError, NacionalArchivesResponse> getNacionalRecord(String documentId);
}
