package co.com.addi.port.out.nacionalarchives.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NacionalArchivesResponse {
	
	private final List<String> records;
}
