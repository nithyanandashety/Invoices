package io.dropwizard.invoices.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.dalesbred.Database;

import io.dropwizard.invoices.dto.CreatingInvoiceDto;
import io.dropwizard.invoices.dto.PayingInvoiceDto;
import io.dropwizard.invoices.dto.ProcessInvoiceDto;
import io.dropwizard.invoices.entity.Invoice;
import io.dropwizard.invoices.repository.InvoiceRepo;
import io.dropwizard.invoices.service.ServiceInterface;

public class InvoiceServiceImplementation implements ServiceInterface {

	private InvoiceRepo repo;

	public InvoiceServiceImplementation(Database database) {
		this.repo = new InvoiceRepo(database);
		this.repo.createTable();
	}

	@Override
	public Map<String, Integer> addInvoice(CreatingInvoiceDto addInvoice) throws Exception {
		if (addInvoice == null)
			throw new Exception("No data available");

		if (addInvoice.getDueDate() == null)
			throw new Exception("No due date available");

		if (addInvoice.getAmount() <= 0)
			throw new Exception("Amount should be greator than 0");

		Invoice invoice = repo.addInvoice(addInvoice);

		Map<String, Integer> map = new HashMap<>();
		map.put("id", invoice.getId());

		return map;
	}

	@Override
	public List<Invoice> getAllInvoices() {
		return repo.getAllInvoices();
	}

	@Override
	public Map<String, String> updateAmount(PayingInvoiceDto invoiceDetails) throws Exception {
		if (invoiceDetails == null)
			throw new Exception("No data");

		if (invoiceDetails.getId() == 0)
			throw new Exception("No Id");

		Map<String, String> map = new HashMap<>();

		if (invoiceDetails.getPaidAmount() <= 0) {
			map.put("message", "Amount should be greater than 0");
			return map;
		}

		try {
			repo.updateAmount(invoiceDetails);
			map.put("message", "Successfully Updated");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	@Override
	public List<Invoice> processOverdue(ProcessInvoiceDto processDue) {
		if (processDue == null)
			return null;

		if (processDue.getLateFee() <= 0 || processDue.getOverdue() <= 0)
			return null;

		Date currentDate = new Date();

		List<Invoice> invoice = repo.processOverdue(currentDate);
		createAndUpdateInvoices(invoice, processDue);
		return invoice;
	}

	private Date addDays(int days) {
		Date currDate = new Date();
		currDate = DateUtils.addDays(currDate, days);

		return currDate;
	}

	private List<Invoice> createAndUpdateInvoices(List<Invoice> overDues, ProcessInvoiceDto processDue) {

		List<Invoice> addedInvoices = new ArrayList<>();

		for (Invoice invoice : overDues) {
			repo.updateStatus(invoice, invoice.getId());

			Invoice addedInvoice = repo.addInvoice(new CreatingInvoiceDto(invoice.getAmount() + processDue.getLateFee(),
					addDays(processDue.getOverdue())));
			addedInvoices.add(addedInvoice);
		}

		return addedInvoices;
	}
}
