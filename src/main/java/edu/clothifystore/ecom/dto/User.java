package edu.clothifystore.ecom.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class User {
	private Integer id;
	private String userName;
	private String initials;
	private String firstName;
	private String lastName;
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
