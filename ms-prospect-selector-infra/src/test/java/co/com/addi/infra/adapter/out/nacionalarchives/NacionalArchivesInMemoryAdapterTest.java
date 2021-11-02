package co.com.addi.infra.adapter.out.nacionalarchives;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.com.addi.infra.adapter.out.nacionalarchives.dto.Record;
import co.com.addi.port.out.nacionalarchives.dto.NacionalArchivesResponse;
import co.com.addi.usecase.common.UseCaseError;
import io.vavr.control.Either;

public class NacionalArchivesInMemoryAdapterTest {
	
	private NacionalArchivesInMemoryAdapter nacionalArchivesInMemoryAdapter;
	
	@Mock
	private Map<String, Record> PersonalRecordsInMemoryDB;
	
	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		nacionalArchivesInMemoryAdapter = new NacionalArchivesInMemoryAdapter(PersonalRecordsInMemoryDB);
	}
	
	@Test
	public void getNacionalRecordShouldReturnRightWithRecords() {
		String documentId = "457898";
		Record record = buildRecord();
		when(PersonalRecordsInMemoryDB.get(documentId)).thenReturn(record);
		Either<UseCaseError, NacionalArchivesResponse> response = nacionalArchivesInMemoryAdapter.getNacionalRecord(documentId);
		assertEquals(true, response.isRight());
		assertEquals(record.getRecords().size(), response.get().getRecords().size());
	}
	
	@Test
	public void getNacionalRecordShouldReturnRightWithoutRecords() {
		String documentId = "457898";
		Record record = buildEmptyRecord();
		when(PersonalRecordsInMemoryDB.get(documentId)).thenReturn(record);
		Either<UseCaseError, NacionalArchivesResponse> response = nacionalArchivesInMemoryAdapter.getNacionalRecord(documentId);
		assertEquals(true, response.isRight());
		assertEquals(record.getRecords().size(), response.get().getRecords().size());
	}
	
	@Test
	public void recordsNotFound() {
		String documentId = "457898";
		when(PersonalRecordsInMemoryDB.get(documentId)).thenThrow(new NullPointerException());
		Either<UseCaseError, NacionalArchivesResponse> response = nacionalArchivesInMemoryAdapter.getNacionalRecord(documentId);
		assertEquals(true, response.isLeft());
		assertEquals("21", response.getLeft().getErrorCode());
	}

	private Record buildRecord() {
		return Record.builder()
				.records(List.of("record"))
				.build();
	}
	
	private Record buildEmptyRecord() {
		return Record.builder()
				.records(List.of())
				.build();
	}

}
