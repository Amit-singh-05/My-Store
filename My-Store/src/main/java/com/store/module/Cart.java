package com.store.module;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;

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
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	
//	@OneToOne
//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	private Customer customer;
	
	@ElementCollection()
	@JoinTable(name = "cartProducts")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Map<String,Integer> products = new HashMap<>();
}
