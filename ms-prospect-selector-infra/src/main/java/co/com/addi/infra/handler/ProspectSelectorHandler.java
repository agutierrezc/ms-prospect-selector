package co.com.addi.infra.handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.com.addi.infra.adapter.in.selector.dto.ProspectSelectorRequest;
import co.com.addi.infra.adapter.in.selector.dto.ProspectSelectorResponse;
import co.com.addi.usecase.common.UseCaseError;
import co.com.addi.usecase.prospect.SelectProspectUseCase;
import co.com.addi.usecase.prospect.command.ProspectCreatedResponse;
import co.com.addi.usecase.prospect.command.SelectProspectCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ProspectSelectorHandler {
	
	private SelectProspectUseCase selectProspectUseCase;

	public ResponseEntity<Object> selectProspect(ProspectSelectorRequest request) {
		log.debug("[ProspectSelectorHandler] selectProspect()");
		return selectProspectUseCase.executeUseCase(buildSelectProspectCommand(request))
				.fold(this::mapError, this::mapResponse );
	}
	
	private ResponseEntity<Object> mapResponse(ProspectCreatedResponse prospectCreatedResponse) {
		
		return new ResponseEntity<>(
				ProspectSelectorResponse.builder()
				.prostectId(prospectCreatedResponse.getProstectId())
				.build(),
				HttpStatus.CREATED);
	}
	
	private ResponseEntity<Object> mapError(UseCaseError useCaseError) {
		return new ResponseEntity<>(useCaseError, HttpStatus.NOT_ACCEPTABLE);
	}

	private SelectProspectCommand buildSelectProspectCommand(ProspectSelectorRequest request) {
		return SelectProspectCommand.builder()
				.leadId(request.getLeadId())
				.documentId(request.getDocumentId())
				.name(request.getName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.birthdate(LocalDate.parse(request.getBirthdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.build();
	}
}
