package co.com.addi.infra.adapter.out.repository.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.addi.infra.adapter.out.repository.ProspectRepositoryInMemoryAdapter;
import co.com.addi.port.out.repository.prospect.ProspectRepositoryPort;

@Configuration
public class ProspectRepositoryInMemoryAdapterConfig {

	@Bean
	public ProspectRepositoryPort getProspectRepositoryPort() {
		return new ProspectRepositoryInMemoryAdapter();
	}
}
