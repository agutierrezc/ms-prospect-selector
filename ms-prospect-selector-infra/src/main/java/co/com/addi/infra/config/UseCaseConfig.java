package co.com.addi.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import co.com.addi.infra.adapter.out.leadqualification.config.LeadQualificationRandomAdapterConfig;
import co.com.addi.port.out.leadqualification.LeadQualificationPort;
import co.com.addi.port.out.nacionalarchives.NacionalArchivesPort;
import co.com.addi.port.out.nacionalregistry.NacionalRegistryPort;
import co.com.addi.port.out.repository.prospect.ProspectRepositoryPort;
import co.com.addi.usecase.prospect.SelectProspectUseCase;

@Configuration
@Import({LeadQualificationRandomAdapterConfig.class})
public class UseCaseConfig {

	@Bean
	public SelectProspectUseCase getSelectProspectUseCase(NacionalRegistryPort nacionalRegistryPort,
			NacionalArchivesPort nacionalArchivesPort, LeadQualificationPort leadQualificationPort,
			ProspectRepositoryPort prospectRepository) {
		return new SelectProspectUseCase(nacionalRegistryPort, nacionalArchivesPort, leadQualificationPort,
				prospectRepository);
	}
}
