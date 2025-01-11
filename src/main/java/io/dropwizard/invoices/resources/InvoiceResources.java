package io.dropwizard.invoices.resources;

import java.util.List;
import java.util.Map;

import org.dalesbred.Database;

import io.dropwizard.invoices.dto.CreatingInvoiceDto;
import io.dropwizard.invoices.dto.PayingInvoiceDto;
import io.dropwizard.invoices.dto.ProcessInvoiceDto;
import io.dropwizard.invoices.entity.Invoice;
import io.dropwizard.invoices.service.imp.InvoiceServiceImplementation;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceResources {
	private InvoiceServiceImplementation invoiceServiceImplementation;

	public InvoiceResources(Database database) {
		this.invoiceServiceImplementation = new InvoiceServiceImplementation(database);

	}

	@POST
	public Response addInvoiceDetails(CreatingInvoiceDto invoice) {
		{
			try {
				Map<String, Integer> map = invoiceServiceImplementation.addInvoice(invoice);
				return Response.status(Status.ACCEPTED).entity(map).build();

			} catch (Exception e) {
				return Response.status(Status.BAD_GATEWAY).entity(e.getMessage()).build();
			}
		}
	}

	@GET
	public Response getAllInvoice() {
		{
			try {
				List<Invoice> invoice = invoiceServiceImplementation.getAllInvoices();
				return Response.status(Status.OK).entity(invoice).build();

			} catch (Exception e) {
				return Response.status(Status.BAD_GATEWAY).entity(e.getMessage()).build();
			}
		}
	}

	@POST
	@Path("/{id}/payments")
	public Response updatePayingInvoice(@PathParam("id") int id, PayingInvoiceDto payingInvoiceDto) {
		payingInvoiceDto.setId(id);
		try {
			Map<String, String> paying = invoiceServiceImplementation.updateAmount(payingInvoiceDto);
			return Response.status(Status.OK).entity(paying).build();

		} catch (Exception e) {
			return Response.status(Status.BAD_GATEWAY).entity(e.getMessage()).build();
		}

	}

	@POST
	@Path("/process-overdue")
	public Response updateprocessOverDue(ProcessInvoiceDto processInvoiceDto) {

		try {
			List<Invoice> invoices = invoiceServiceImplementation.processOverdue(processInvoiceDto);
			return Response.status(Status.OK).entity(invoices).build();

		} catch (Exception e) {
			return Response.status(Status.BAD_GATEWAY).entity(e.getMessage()).build();
		}

	}

}
