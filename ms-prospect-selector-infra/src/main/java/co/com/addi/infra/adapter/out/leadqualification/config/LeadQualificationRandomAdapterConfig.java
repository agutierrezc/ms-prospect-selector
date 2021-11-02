package co.com.addi.infra.adapter.out.leadqualification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.addi.infra.adapter.out.leadqualification.LeadQualificationRandomAdapter;
import co.com.addi.port.out.leadqualification.LeadQualificationPort;

@Configuration
public class LeadQualificationRandomAdapterConfig {

	@Bean
	public LeadQualificationPort getLeadQualificationPort() {
		return new LeadQualificationRandomAdapter();
	}
}
