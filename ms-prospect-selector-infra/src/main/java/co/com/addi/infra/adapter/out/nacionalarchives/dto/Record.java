package co.com.addi.infra.adapter.out.nacionalarchives.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Record {
	private final List<String> records;
}
