package com.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.dto.CurrentUserSession;

@Repository
public interface CurrentUserSessionRepo extends JpaRepository<CurrentUserSession, Integer>{
	public CurrentUserSession findByUniqueID(String uniqueID);
}
