package com.store.module;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	
	@Size(min = 2, max = 20, message = "Length of name should not be more than 20 characters and less than 2 characters")
	private String customerName;
	
	@Pattern(regexp = "^[0-9]{10}", message = "Check the mobile number entered and Try again ")
	@Column(unique = true)
	private String customerMobileNumber;
	
	@Email(message = "Check the e-mail ID entered and try again ")
	@Column(unique = true)
	private String customerUsername;
	
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "Password should contain at least 1 uppercase character, 1 lower case character, 1 special character, numeric value and length of the password should be more than 8 characters ")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String customerPassword;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Address> addresses;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Cart cart;
}
