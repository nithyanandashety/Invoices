package io.dropwizard.invoices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProcessInvoiceDto {
	private double lateFee;
	private Integer overdue;

}
