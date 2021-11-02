package co.com.addi.infra.adapter.out.nacionalregistry.config;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.addi.infra.adapter.out.nacionalregistry.NacionalRegistryInMemoryAdpater;
import co.com.addi.infra.adapter.out.nacionalregistry.dto.PersonalInformation;
import co.com.addi.port.out.nacionalregistry.NacionalRegistryPort;

@Configuration
public class NacionalRegistryInMemoryAdpaterConfig {

	@Bean
	public NacionalRegistryPort getNacionalRegistryPort() {
		return new NacionalRegistryInMemoryAdpater(buildPersonalInformationInMemoryDB());
	}

	private Map<String, PersonalInformation> buildPersonalInformationInMemoryDB() {
		return Map.of("1012023314", buildPersonalInformation("1012023314", "Javier", "Perez", "javier@email.com", LocalDate.of(1985, 3, 27)),
				"1018021365", buildPersonalInformation("1018021365", "Andres", "Gomez", "andres@email.com", LocalDate.of(1986, 4, 26)),
				"1020013236", buildPersonalInformation("1020013236", "Camilo", "Velez", "camilo@email.com", LocalDate.of(1990, 7, 16)),
				"1021011247", buildPersonalInformation("1021011247", "Pablo", "Hurtado", "pablo@email.com", LocalDate.of(1987, 1, 2)),
				"1021017982", buildPersonalInformation("1021017982", "Julian", "Camacho", "julian@email.com", LocalDate.of(1994, 8, 9))
				);
	}
	
	private PersonalInformation buildPersonalInformation(String documentId, String firstname, String lastname,
			String email, LocalDate birthdate) {
		return PersonalInformation.builder()
				.documentId(documentId)
				.firstname(firstname)
				.lastname(lastname)
				.email(email)
				.birthdate(birthdate)
				.build();
	}
}
