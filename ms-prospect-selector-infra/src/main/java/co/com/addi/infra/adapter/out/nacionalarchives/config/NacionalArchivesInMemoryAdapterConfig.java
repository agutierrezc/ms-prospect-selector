package co.com.addi.infra.adapter.out.nacionalarchives.config;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.addi.infra.adapter.out.nacionalarchives.NacionalArchivesInMemoryAdapter;
import co.com.addi.infra.adapter.out.nacionalarchives.dto.Record;
import co.com.addi.port.out.nacionalarchives.NacionalArchivesPort;
import io.vavr.control.Option;

@Configuration
public class NacionalArchivesInMemoryAdapterConfig {
	
	@Bean
	public NacionalArchivesPort getNacionalArchivesPort() {
		return new NacionalArchivesInMemoryAdapter(buildPersonalRecordsInMemoryDB());
	}
	
	private Map<String, Record> buildPersonalRecordsInMemoryDB() {
		return Map.of("1012023314", buildRecord("Policia"), 
				"1018021365", buildRecord(null), 
				"1020013236", buildRecord(null), 
				"1021011247", buildRecord(null), 
				"1021017982", buildRecord(null));
	}
	
	private Record buildRecord(String record) {
		return Record.builder()
				.records(Option.of(record).fold(() -> List.of(), data -> List.of(data)))
				.build();
	}
}
