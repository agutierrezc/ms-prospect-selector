package co.com.addi.usecase.prospect.command;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProspectCreatedResponse {
	private final UUID prostectId;
}
