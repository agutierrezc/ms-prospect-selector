package co.com.addi.infra.adapter.in.selector;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.addi.infra.adapter.in.selector.dto.ProspectSelectorRequest;
import co.com.addi.infra.handler.ProspectSelectorHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class ProspectSelectorApi {

	private final ProspectSelectorHandler prospectSelectorHandler;

	@PostMapping(value = "prospect", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> selectProspect(@RequestHeader final HttpHeaders headers,
			@RequestBody final ProspectSelectorRequest request) {
		log.debug("[ProspectSelectorApi] selectProspect()");
		return prospectSelectorHandler.selectProspect(request);
	}
}
