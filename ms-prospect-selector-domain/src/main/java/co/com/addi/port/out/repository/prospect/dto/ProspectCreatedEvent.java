package co.com.addi.port.out.repository.prospect.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProspectCreatedEvent {
	private final UUID leadId;
	private final String documentId;
	private final String name;
	private final String lastName;
	private final String email;
	private final LocalDate birthdate;
}
