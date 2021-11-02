package co.com.addi.infra.adapter.in.selector.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProspectSelectorRequest {
	private UUID leadId;
	private String documentId;
	private String name;
	private String lastName;
	private String email;
	private String birthdate;
}
