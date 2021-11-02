package co.com.addi.port.out.leadqualification;

import java.util.UUID;

import co.com.addi.port.out.leadqualification.dto.LeadQualificationResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public interface LeadQualificationPort {

	Either<UseCaseError, LeadQualificationResponse> getLeadQualification(UUID leadId);
}
