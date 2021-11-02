package co.com.addi.infra.adapter.out.nacionalregistry.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PersonalInformation {
	private final String documentId;
	private final String firstname;
	private final String lastname;
	private final String email;
	private final LocalDate birthdate;
}
