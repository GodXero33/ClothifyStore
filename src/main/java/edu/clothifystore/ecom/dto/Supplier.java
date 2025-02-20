package edu.clothifystore.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Supplier {
	private Integer id;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String type;
	private String description;
}
