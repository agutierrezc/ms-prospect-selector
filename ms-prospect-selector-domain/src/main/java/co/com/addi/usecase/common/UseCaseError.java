package co.com.addi.usecase.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UseCaseError {
	private final String errorCode;
	private final String errorDescription;
}
