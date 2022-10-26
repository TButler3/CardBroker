package com.tombutler.cardbroker.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tombutler.cardbroker.models.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

	List<Card> findByPlayerName(String playerName);
	List<Card> findByPlayerNameAndIsAuto(String playerName, boolean isAuto);
}
