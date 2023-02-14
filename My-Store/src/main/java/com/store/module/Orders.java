package com.store.module;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	@ElementCollection
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Map<Product,Integer> products = new HashMap<>();
	
	@NotNull(message="Order status can't be null")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	private LocalDate orderDate = LocalDate.now();
	
	private LocalDate deliveryDate  = orderDate.plusDays(7);
}
