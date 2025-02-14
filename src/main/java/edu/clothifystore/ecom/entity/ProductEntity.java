package edu.clothifystore.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductEntity {
	private Integer id;
	private String name;
	private String size;
	private Double price;
	private Double discount;
	private Integer quantity;
	private String brand;
	private String gender;
	private String description;
	private String type;
}
