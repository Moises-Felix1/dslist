package com.deb.dslist.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deb.dslist.dto.GameDTO;
import com.deb.dslist.dto.GameMinDTO;
import com.deb.dslist.entities.Game;
import com.deb.dslist.projections.GameMinProjection;
import com.deb.dslist.repositories.GameListRepository;
import com.deb.dslist.repositories.GameRepository;
import com.deb.dslist.services.exceptions.ObjectNotFoundException;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	
	@Transactional(readOnly = true)
	public Game findById(Long id) {
		Optional<Game> result = gameRepository.findById(id);
		return result.orElseThrow(() -> new ObjectNotFoundException(id));
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
	public void insertGames(GameDTO gamedto) {
		Game game = new Game();
		BeanUtils.copyProperties(gamedto, game);
		gameRepository.save(game);
	}
	
	@Transactional
	public void deleteGame(Long id) {
		gameRepository.deleteById(id);
	}
	
}
