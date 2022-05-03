package com.nttdata.movementcard.service.service.impl;

import java.util.Calendar;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nttdata.movementcard.service.entity.MovementsCard;
import com.nttdata.movementcard.service.repository.MovementsCardRepository;
import com.nttdata.movementcard.service.service.MovementsCardService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementsCardServiceImpl implements MovementsCardService {
	@Autowired
	MovementsCardRepository cardRepository;

	@Override
	public Flux<MovementsCard> findAll() {
		// TODO Auto-generated method stub
		return cardRepository.findAll().sort((o, o1) -> o.getIdMovementCard().compareTo(o1.getIdMovementCard()));
	}

	@Override
	public Mono<MovementsCard> findById(Long idMovementCard) {
		// TODO Auto-generated method stub
		return cardRepository.findById(idMovementCard);
	}

	@Override
	public Mono<MovementsCard> save(MovementsCard movementsCard) {
		// TODO Auto-generated method stub
		Long count = this.findAll().collect(Collectors.counting()).blockOptional().get();
		Long idMovementCard;

		if (count != null) {
			if (count <= 0) {
				idMovementCard = Long.valueOf(0);
			} else {
				idMovementCard = this.findAll()
						.collect(Collectors.maxBy(Comparator.comparing(MovementsCard::getIdMovementCard)))
						.blockOptional().get().get().getIdMovementCard();
			}

		} else {
			idMovementCard = Long.valueOf(0);
		}
		movementsCard.setIdMovementCard(idMovementCard + 1);
		movementsCard.setCreationDate(Calendar.getInstance().getTime());
		return cardRepository.insert(movementsCard);
	}

	@Override
	public Mono<MovementsCard> update(MovementsCard movementsCard) {
		// TODO Auto-generated method stub
		movementsCard.setDateModified(Calendar.getInstance().getTime());
		return cardRepository.save(movementsCard);
	}

	@Override
	public Mono<Void> delete(Long idMovementCard) {
		// TODO Auto-generated method stub
		return cardRepository.deleteById(idMovementCard);
	}
}
