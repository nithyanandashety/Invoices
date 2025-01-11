package io.dropwizard.invoices.repository;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dalesbred.Database;
import org.dalesbred.query.SqlQuery;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import io.dropwizard.invoices.dao.InvoiceDao;
import io.dropwizard.invoices.dto.CreatingInvoiceDto;
import io.dropwizard.invoices.dto.PayingInvoiceDto;
import io.dropwizard.invoices.entity.Invoice;
import io.dropwizard.invoices.enums.InvoiceEnum;

public class InvoiceRepo implements InvoiceDao {

	private Database database;
	private static final String DATA_SOURCE = "db/invoice.sql";

	public InvoiceRepo(Database database) {
		this.database = database;
	}

	private String generateTableIfExists() throws IOException {
		URL url = Resources.getResource(DATA_SOURCE);
		String tables = Resources.toString(url, Charsets.UTF_8);
		return tables;
	}

	private Invoice getInvoice(int id) {
		final String GET_INVOICE = "SELECT * from INVOICE WHERE id = ?";

		return database.findUnique(Invoice.class, GET_INVOICE, id);
	}

	@Override
	public void updateStatus(Invoice invoice, int id) {

		final String UPDATE_STATUS = "UPDATE INVOICE SET status = :status WHERE id = :id";

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", invoice.getPaidAmount() > 0 ? InvoiceEnum.PAID : InvoiceEnum.VOID);
		parameters.put("id", id);

		database.update(SqlQuery.namedQuery(UPDATE_STATUS, parameters));
	}

	@Override
	public void createTable() {
		try {
			String tables = generateTableIfExists();
			database.update(tables);

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@Override
	public Invoice addInvoice(CreatingInvoiceDto addInvoice) {
		final String ADD_INVOICE = "INSERT INTO INVOICE (amount, due_date) VALUES (:amount, :dueDate) RETURNING *";

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("amount", addInvoice.getAmount());
		parameters.put("dueDate", addInvoice.getDueDate());

		Invoice createdInvoice = database.findUnique(Invoice.class, SqlQuery.namedQuery(ADD_INVOICE, parameters));

		return createdInvoice;
	}

	@Override
	public List<Invoice> getAllInvoices() {
		final String GET_ALL_INVOICES = "SELECT * FROM INVOICE";
		List<Invoice> allInvoices = database.findAll(Invoice.class, GET_ALL_INVOICES);

		return allInvoices;
	}

	@Override
	public void updateAmount(PayingInvoiceDto invoiceDetails) throws Exception {
		final String UPDATE_AMOUNT = "UPDATE INVOICE SET paid_amount = :paidAmount, status = :status, amount = :amount WHERE id = :id";

		Invoice getAmount = getInvoice(invoiceDetails.getId());

		if (getAmount.getAmount() < invoiceDetails.getPaidAmount())
			throw new Exception("Amount being paid is more than amount to be paid");

		InvoiceEnum status = getAmount.getAmount() <= invoiceDetails.getPaidAmount() ? InvoiceEnum.PAID
				: InvoiceEnum.PENDING;

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paidAmount", getAmount.getPaidAmount() + invoiceDetails.getPaidAmount());
		parameters.put("id", invoiceDetails.getId());
		parameters.put("status", status);
		parameters.put("amount", getAmount.getAmount() - invoiceDetails.getPaidAmount());

		database.update(SqlQuery.namedQuery(UPDATE_AMOUNT, parameters));

	}

	@Override
	public List<Invoice> processOverdue(Date date) {
		final String GET_PROCESS_OVERDUE = "SELECT * FROM INVOICE WHERE due_date <? AND status = 'PENDING'";

		return database.findAll(Invoice.class, SqlQuery.query(GET_PROCESS_OVERDUE, date));

	}

}