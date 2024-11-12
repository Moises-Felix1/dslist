package com.deb.dslist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deb.dslist.entities.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
