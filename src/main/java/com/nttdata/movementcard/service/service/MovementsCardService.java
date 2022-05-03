package com.nttdata.movementcard.service.service;

import com.nttdata.movementcard.service.entity.MovementsCard;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementsCardService {

	Flux<MovementsCard> findAll();

	Mono<MovementsCard> findById(Long idMovementCard);

	Mono<MovementsCard> save(MovementsCard movementsCard);

	Mono<MovementsCard> update(MovementsCard movementsCard);

	Mono<Void> delete(Long idMovementCard);
}
