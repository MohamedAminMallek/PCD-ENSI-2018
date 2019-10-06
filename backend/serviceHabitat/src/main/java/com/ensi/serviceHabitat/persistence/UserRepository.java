package com.ensi.serviceHabitat.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensi.serviceHabitat.entity.User;

@Repository("userRepository")
public interface UserRepository  extends JpaRepository<User, Long>{
	
}
