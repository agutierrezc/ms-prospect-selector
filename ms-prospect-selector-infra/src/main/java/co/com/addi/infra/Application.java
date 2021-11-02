package co.com.addi.infra;

import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Application {

	public static void main(final String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
		Locale.setDefault(Locale.forLanguageTag("es_CO"));
		SpringApplication.run(Application.class, args);
	}
}
