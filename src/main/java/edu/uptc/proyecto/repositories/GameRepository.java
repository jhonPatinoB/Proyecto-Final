package edu.uptc.proyecto.repositories;

import edu.uptc.proyecto.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
