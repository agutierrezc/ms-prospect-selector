package co.com.addi.usecase.prospect.command;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SelectProspectCommand {
	private final UUID leadId;
	private final String documentId;
	private final LocalDate birthdate;
	private final String name;
	private final String lastName;
	private final String email;
}
