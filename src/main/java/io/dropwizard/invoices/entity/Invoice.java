package io.dropwizard.invoices.entity;

import java.sql.Date;

import io.dropwizard.invoices.enums.InvoiceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
	private Integer id;
	private Double amount;
	private Date dueDate;
	private Double paidAmount;
	private InvoiceEnum status;
//	
//	public Invoice(Integer id, Double amount,Date dueDtae, Double paidAmount,InvoiceEnum status) {
//		this.id=id;
//		this.amount =amount;
//		this.duedate;

}
