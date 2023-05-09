package edu.uptc.proyecto.repositories;

import edu.uptc.proyecto.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {

}
