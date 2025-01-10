package io.dropwizard.invoices.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingInvoiceDto {
	private Double amount;
	private Date dueDate;

}
