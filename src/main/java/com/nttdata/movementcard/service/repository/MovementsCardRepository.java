package com.nttdata.movementcard.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.movementcard.service.entity.MovementsCard;

@Repository
public interface MovementsCardRepository extends ReactiveMongoRepository<MovementsCard,Long> {

}
