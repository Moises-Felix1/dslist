package com.deb.dslist.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deb.dslist.dto.GameDTO;
import com.deb.dslist.dto.GameMinDTO;
import com.deb.dslist.entities.Game;
import com.deb.dslist.projections.GameMinProjection;
import com.deb.dslist.repositories.GameRepository;
import com.deb.dslist.services.exceptions.DataBasesException;
import com.deb.dslist.services.exceptions.ObjectNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Transactional(readOnly = true)
	public GameDTO findById(Long id) {
		if(!gameRepository.existsById(id))
			throw new ObjectNotFoundException(id);
		Game result = gameRepository.findById(id).get();
		return new GameDTO(result);
	}
	
	@Transactional(readOnly = true)
	public List<GameMinDTO> findAll(){
		List<Game> result = gameRepository.findAll();
		return result.stream().map(x -> new GameMinDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public List<GameMinDTO> findByList(Long listId){
		List<GameMinProjection> result = gameRepository.searchByList(listId);
		return result.stream().map(x -> new GameMinDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public GameDTO insertGames(GameDTO gamedto) {
		Game game = new Game();
		BeanUtils.copyProperties(gamedto, game);
		gameRepository.save(game);
		return gamedto;
	}
	
	//@Transactional(readOnly = true)
	public void deleteGame(Long id) {
		if(!gameRepository.existsById(id)) 
			throw new ObjectNotFoundException(id);
		try {
			gameRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataBasesException(e.getMessage());
		}
	}
	
	@Transactional
	public void updateGame(Long id, GameDTO gameDto) {
		try{
			Game game = gameRepository.getReferenceById(id);
			update(game, gameDto);
			gameRepository.save(game);
		}catch(EntityNotFoundException e) {
			throw new ObjectNotFoundException(id);
		}
	}

	private void update(Game game, GameDTO gameDto) {
		game.setPlatforms(gameDto.getPlatforms());
		game.setScore(gameDto.getScore());
		game.setImgUrl(gameDto.getImgUrl());
	}
	
}
