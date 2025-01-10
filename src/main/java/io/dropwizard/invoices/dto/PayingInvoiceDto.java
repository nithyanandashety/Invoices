package io.dropwizard.invoices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayingInvoiceDto {
	private Integer id;
	private Double paidAmount;
}
