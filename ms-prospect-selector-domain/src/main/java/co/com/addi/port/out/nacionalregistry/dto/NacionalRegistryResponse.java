package co.com.addi.port.out.nacionalregistry.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NacionalRegistryResponse {
	private final String documentId;
	private final String firstname;
	private final String lastName;
	private final String email;
	private final LocalDate birthdate;
}
