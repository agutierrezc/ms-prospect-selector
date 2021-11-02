package co.com.addi.infra.adapter.out.nacionalarchives;

import java.util.Map;

import co.com.addi.infra.adapter.out.nacionalarchives.dto.Record;
import co.com.addi.infra.common.TimerUtil;
import co.com.addi.port.out.nacionalarchives.NacionalArchivesPort;
import co.com.addi.port.out.nacionalarchives.dto.NacionalArchivesResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class NacionalArchivesInMemoryAdapter implements NacionalArchivesPort {
	
	private final Map<String, Record> PersonalRecordsInMemoryDB;

	@Override
	public Either<UseCaseError, NacionalArchivesResponse> getNacionalRecord(String documentId) {
		log.info("[NacionalArchivesInMemoryAdapter] getNacionalRecord()");
		return Try.of(() -> PersonalRecordsInMemoryDB.get(documentId))
				.map(personalRecords -> buildNacionalArchivesResponse(personalRecords))
				.toEither(buildUseCaseError())
				.peek(personalRecords -> TimerUtil.randomSleep())
				.peek(personalRecords -> log.info("[NacionalArchivesInMemoryAdapter] getNacionalRecord is done"));
	}
	
	private UseCaseError buildUseCaseError() {
		return UseCaseError.builder()
				.errorCode("21")
				.errorDescription("Error getting data from Nacional Archives")
				.build();
	}
	
	private NacionalArchivesResponse buildNacionalArchivesResponse(Record personalRecords) {
		return NacionalArchivesResponse.builder()
				.records(personalRecords.getRecords())
				.build();
	}
}
