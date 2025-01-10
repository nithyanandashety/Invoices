package io.dropwizard.invoices.resources;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.dalesbred.Database;
import org.dalesbred.junit.TestDatabaseProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.invoices.dto.CreatingInvoiceDto;
import io.dropwizard.invoices.entity.Invoice;
import io.dropwizard.invoices.service.imp.InvoiceServiceImplementation;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class InvoiceTestResource {
	private InvoiceServiceImplementation serviceImplementation;
	
	private Database db = TestDatabaseProvider.databaseForProperties("config.yml");
	
	@BeforeEach
	void setup() {
		serviceImplementation = new InvoiceServiceImplementation(db);
		
	}
	


}
