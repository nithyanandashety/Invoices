package io.dropwizard.invoices.service;

import java.util.List;
import java.util.Map;

import io.dropwizard.invoices.dto.CreatingInvoiceDto;
import io.dropwizard.invoices.dto.PayingInvoiceDto;
import io.dropwizard.invoices.dto.ProcessInvoiceDto;
import io.dropwizard.invoices.entity.Invoice;

public interface ServiceInterface {
	Map<String, Integer> addInvoice(CreatingInvoiceDto addInvoice) throws Exception;

	List<Invoice> getAllInvoices();

	Map<String, String> updateAmount(PayingInvoiceDto invoiceDetails) throws Exception;

	List<Invoice> processOverdue(ProcessInvoiceDto processDue);

}
