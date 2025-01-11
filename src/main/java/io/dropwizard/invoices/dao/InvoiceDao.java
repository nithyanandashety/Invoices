package io.dropwizard.invoices.dao;

import java.util.Date;
import java.util.List;

import io.dropwizard.invoices.dto.CreatingInvoiceDto;
import io.dropwizard.invoices.dto.PayingInvoiceDto;
import io.dropwizard.invoices.dto.ProcessInvoiceDto;
import io.dropwizard.invoices.entity.Invoice;

public interface InvoiceDao {

	void createTable();
	Invoice addInvoice(CreatingInvoiceDto addInvoice);
	

	List<Invoice> processOverdue(Date date);

	List<Invoice> getAllInvoices();


	void updateAmount(PayingInvoiceDto invoiceDetails) throws Exception;


	void updateStatus(Invoice invoice, int id);

}
