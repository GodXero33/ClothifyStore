package edu.clothifystore.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SupplierEntity {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String type;
	private String description;
}
