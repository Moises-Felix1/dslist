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

import com.deb.dslist.dto.GameDTO;
import com.deb.dslist.dto.GameMinDTO;
import com.deb.dslist.services.GameService;


@RestController
@RequestMapping(value = "/games")
public class GameController {

	@Autowired
	private GameService gameService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<GameDTO> findById(@PathVariable Long id){
		GameDTO result = gameService.findById(id);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping
	public ResponseEntity<List<GameMinDTO>> findAll(){
		var result = gameService.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<GameDTO> insertGame(@RequestBody GameDTO gameDto){
		gameService.insertGames(gameDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gameDto.getId()).toUri();
		return ResponseEntity.created(uri).body(gameDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteGame(@PathVariable Long id){
		 gameService.deleteGame(id);
		 return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<GameDTO> updateGame(@PathVariable Long id, @RequestBody GameDTO gameDto){
		gameService.updateGame(id, gameDto);
		return ResponseEntity.ok().body(findById(id).getBody());
	}
}
