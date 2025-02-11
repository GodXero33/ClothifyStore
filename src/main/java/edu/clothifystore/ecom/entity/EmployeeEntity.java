package edu.clothifystore.ecom.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeEntity {
	private Integer id;
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
	private List<EmployeePhoneEntity> phone;
}
