package com.deb.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deb.dslist.entities.GameList;

public interface GameListRepository extends JpaRepository<GameList, Long>{

}
