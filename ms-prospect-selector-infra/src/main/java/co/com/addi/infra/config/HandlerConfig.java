package co.com.addi.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.addi.infra.handler.ProspectSelectorHandler;
import co.com.addi.usecase.prospect.SelectProspectUseCase;

@Configuration
public class HandlerConfig {

	@Bean
	public ProspectSelectorHandler getProspectSelectorHandler(SelectProspectUseCase selectProspectUseCase) {
		return new ProspectSelectorHandler(selectProspectUseCase);
	}
}
