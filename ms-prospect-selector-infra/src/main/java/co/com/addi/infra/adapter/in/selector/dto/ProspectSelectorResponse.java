package co.com.addi.infra.adapter.in.selector.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProspectSelectorResponse {
	private final UUID prostectId;
}
