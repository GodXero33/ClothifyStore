package edu.clothifystore.ecom.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class EmployeeEntity {
	private String userName;
	private String fullName;
	private String NIC;
	private String email;
	private String address;
	private String DOB;
	private String password;
	private Double salary;
	private String type;
	private String role;
	private Integer adminID;
}
