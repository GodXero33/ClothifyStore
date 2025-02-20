package edu.clothifystore.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {
	private Integer id;
	private String date;
	private String time;
	private Integer employeeID;
	private Integer customerID;
	private List<OrderItem> orderItems;
}
