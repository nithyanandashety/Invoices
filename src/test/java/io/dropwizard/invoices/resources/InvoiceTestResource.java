package io.dropwizard.invoices.resources;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dalesbred.Database;
import org.dalesbred.junit.TestDatabaseProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.invoices.dto.CreatingInvoiceDto;
import io.dropwizard.invoices.dto.PayingInvoiceDto;
import io.dropwizard.invoices.dto.ProcessInvoiceDto;
import io.dropwizard.invoices.entity.Invoice;
import io.dropwizard.invoices.service.imp.InvoiceServiceImplementation;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class InvoiceTestResource {
	static private InvoiceServiceImplementation serviceImplementation;

	static private Database db = TestDatabaseProvider.databaseForProperties("config.yml");

	@BeforeAll
	static void setup() {
		serviceImplementation = new InvoiceServiceImplementation(db);

		db.update("insert into invoice (amount,due_date) values(300,'2024-05-01')");
	}

	@AfterAll
	static void done() {
		db.update("truncate table invoice restart identity");
	}

	@Test
	void addInvoice() throws Exception {

		CreatingInvoiceDto createInvoiceDto = new CreatingInvoiceDto(100D, new Date());

		Map<String, Integer> result = serviceImplementation.addInvoice(createInvoiceDto);

		assertThat(result).containsKey("id");
		assertThat(result.get("id")).isGreaterThan(0);
	}

	@Test
	void addInvoiceError() {
		Exception exception = null;
		try {
			serviceImplementation.addInvoice(null);
		} catch (Exception e) {
			exception = e;
		}
		assertThat(exception).isNotNull();
		assertThat(exception.getMessage()).isEqualTo("No data available");
	}

	@Test
	void getAllInvoices() throws Exception {
		serviceImplementation.addInvoice(new CreatingInvoiceDto(100D, new Date()));
		serviceImplementation.addInvoice(new CreatingInvoiceDto(200D, new Date()));

		List<Invoice> result = serviceImplementation.getAllInvoices();

		assertThat(result).isNotEmpty();
	}

	@Test
	void updateAmountSuccessMessage() throws Exception {
		CreatingInvoiceDto createInvoiceDto = new CreatingInvoiceDto(100D, new Date());
		Map<String, Integer> addInvoiceResult = serviceImplementation.addInvoice(createInvoiceDto);
		int invoiceId = addInvoiceResult.get("id");

		PayingInvoiceDto payingInvoiceDto = new PayingInvoiceDto(invoiceId, 50D);

		Map<String, String> result = serviceImplementation.updateAmount(payingInvoiceDto);

		assertThat(result).containsEntry("message", "Successfully Updated");
	}

	@Test
	void updateAmount() {
		PayingInvoiceDto payingInvoiceDto = new PayingInvoiceDto(0, 100D);

		Exception exception = null;
		try {
			serviceImplementation.updateAmount(payingInvoiceDto);
		} catch (Exception e) {
			exception = e;
		}

		assertThat(exception).isNotNull();
		assertThat(exception.getMessage()).isEqualTo("No Id");
	}

	@Test
	void processOverdue() {
		ProcessInvoiceDto processInvoiceDto = new ProcessInvoiceDto(0, 0);

		List<Invoice> result = serviceImplementation.processOverdue(processInvoiceDto);

		assertThat(result).isNull();
	}

}
