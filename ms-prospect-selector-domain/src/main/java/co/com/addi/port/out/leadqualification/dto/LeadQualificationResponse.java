package co.com.addi.port.out.leadqualification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LeadQualificationResponse {
	private final int score;
}
