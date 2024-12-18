package com.deb.dslist.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deb.dslist.dto.GameListDTO;
import com.deb.dslist.dto.GameMinDTO;
import com.deb.dslist.dto.ReplacementDTO;
import com.deb.dslist.entities.GameList;
import com.deb.dslist.services.GameListService;
import com.deb.dslist.services.GameService;

@RestController
@RequestMapping(value = "/lists")
public class GameListController {

	@Autowired
	private GameListService gameListService;
	
	@Autowired
	private GameService gameService;


	@GetMapping
	public ResponseEntity<List<GameListDTO>> findAll() {
		var result = gameListService.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/{listId}")
	public ResponseEntity<GameListDTO> findByIdList(@PathVariable Long listId){
		GameList re = gameListService.findByIdList(listId);
		GameListDTO result = new GameListDTO(re);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping(value = "/{listId}/games")
	public ResponseEntity<List<GameMinDTO>> findByList(@PathVariable Long listId){
		gameListService.findByIdList(listId);
		var result = gameService.findByList(listId);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping(value = "/{listId}/replacement")
	public void move(@PathVariable Long listId, @RequestBody ReplacementDTO body){
		gameListService.move(listId, body.getSourceIndex(), body.getTargetIndex());
	}
	
	@PostMapping
	public ResponseEntity<GameListDTO> insertList(@RequestBody GameListDTO gameListDto) {
		gameListService.insertList(gameListDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gameListDto.getId()).toUri();
		return ResponseEntity.created(uri).body(gameListDto);
	}
	
	@DeleteMapping(value = "/{listId}")
	public ResponseEntity<Void> deleteList(@PathVariable Long listId) {
		gameListService.deleteList(listId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{listId}")
	public ResponseEntity<GameListDTO> updateGame(@PathVariable Long listId, @RequestBody GameListDTO gameListDto){
		gameListService.updateGame(listId, gameListDto);
		return ResponseEntity.ok().body(findByIdList(listId).getBody());
	}
}
