package com.store.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class CurrentUserSession {
	@Id
	@Column(unique = true)
	private Integer userId;

	private String uniqueID;

	private Boolean admin;

	private LocalDateTime localDateTime;
}
