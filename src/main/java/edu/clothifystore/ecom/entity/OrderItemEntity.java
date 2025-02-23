package edu.clothifystore.ecom.entity;

import edu.clothifystore.ecom.dto.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItemEntity {
	private Integer orderId;
	private Product product;
	private Integer quantity;
	private Double discount;
}
