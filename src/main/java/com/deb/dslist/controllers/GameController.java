package com.deb.dslist.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deb.dslist.dto.GameDTO;
import com.deb.dslist.dto.GameMinDTO;
import com.deb.dslist.entities.Game;
import com.deb.dslist.services.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/games")
public class GameController {

	@Autowired
	private GameService gameService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<GameDTO> findById(@PathVariable Long id){
		Game obj = gameService.findById(id);
		var result = new GameDTO(obj);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping
	public ResponseEntity<List<GameMinDTO>> findAll(){
		var result = gameService.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<List<GameMinDTO>> insertGame(@RequestBody GameDTO gameDto){
		gameService.insertGames(gameDto);
		return findAll();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteGame(@PathVariable Long id){
		 gameService.deleteGame(id);
		 return ResponseEntity.noContent().build();
	}
}
