package edu.clothifystore.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItemEntity {
	private Integer order_id;
	private Integer product_id;
	private Integer quantity;
	private Double discount;
}
