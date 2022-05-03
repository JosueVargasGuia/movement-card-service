package com.nttdata.movementcard.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nttdata.movementcard.service.entity.MovementsCard;
import com.nttdata.movementcard.service.service.MovementsCardService;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/movement-card")
public class MovementsCardController {
	@Autowired
	MovementsCardService cardService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<MovementsCard> findAll() {
		return cardService.findAll();

	}

	@PostMapping
	public Mono<ResponseEntity<MovementsCard>> save(@RequestBody MovementsCard movementsCard) {
		return cardService.save(movementsCard).map(_movementsCard -> ResponseEntity.ok().body(_movementsCard))
				.onErrorResume(e -> {
					log.info("Error:" + e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				});
	}

	@GetMapping("/{idMovementsCard}")
	public Mono<ResponseEntity<MovementsCard>> findById(@PathVariable(name = "idMovementsCard") long idMovementsCard) {
		return cardService.findById(idMovementsCard).map(movementsCard -> ResponseEntity.ok().body(movementsCard))
				.onErrorResume(e -> {
					log.info(e.getMessage());
					return Mono.just(ResponseEntity.badRequest().build());
				}).defaultIfEmpty(ResponseEntity.noContent().build());
	}

	@PutMapping
	public Mono<ResponseEntity<MovementsCard>> update(@RequestBody MovementsCard movementsCard) {

		Mono<MovementsCard> mono = cardService.findById(movementsCard.getIdMovementCard()).flatMap(objMovementsCard -> {
			log.info("Update:[new]" + movementsCard + " [Old]:" + objMovementsCard);

			return cardService.update(movementsCard);
		});

		return mono.map(_MovementsCard -> {
			log.info("Status:" + HttpStatus.OK);
			return ResponseEntity.ok().body(_MovementsCard);
		}).onErrorResume(e -> {
			log.info("Status:" + HttpStatus.BAD_REQUEST + " menssage" + e.getMessage());
			return Mono.just(ResponseEntity.badRequest().build());
		}).defaultIfEmpty(ResponseEntity.noContent().build());

	}

	@DeleteMapping("/{idMovementsCard}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(name = "idMovementsCard") long idMovementsCard) {
		return cardService.findById(idMovementsCard).flatMap(movementsCard -> {
			return cardService.delete(movementsCard.getIdMovementCard()).then(Mono.just(ResponseEntity.ok().build()));
		});
	}
}
