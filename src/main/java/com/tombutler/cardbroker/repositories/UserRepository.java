package com.tombutler.cardbroker.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tombutler.cardbroker.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
}
