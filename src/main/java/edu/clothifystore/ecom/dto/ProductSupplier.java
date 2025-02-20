package edu.clothifystore.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductSupplier {
	private Integer supplierID;
	private Integer productID;
	private String date;
	private String time;
	private Integer quantity;
	private Double supplierPrice;
	private String paymentStatus;
	private String paymentDate;
}
