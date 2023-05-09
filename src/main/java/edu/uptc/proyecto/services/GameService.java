package edu.uptc.proyecto.services;

import edu.uptc.proyecto.entities.Customer;
import edu.uptc.proyecto.request.GameRequest;
import edu.uptc.proyecto.entities.Game;
import edu.uptc.proyecto.entities.Administrator;
import edu.uptc.proyecto.enums.Clasification;
import edu.uptc.proyecto.repositories.GameRepository;
import edu.uptc.proyecto.repositories.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final AdministratorRepository administratorRepository;

    public GameService(GameRepository gameRepository, AdministratorRepository administratorRepository) {
        this.gameRepository = gameRepository;
        this.administratorRepository = administratorRepository;
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    public Game findById(int id) {
        Optional<Game> optional = gameRepository.findById(id);
        return optional.orElse(null);
    }

    public Game save(GameRequest gameRequest, int id) {

        Game game = new Game();
        Administrator administrator = findAdministratorById(id);
        List<Customer> customers = new ArrayList<>();

        if (administrator == null) {
            return null;
        }

        String name = gameRequest.getName();
        BigDecimal price = gameRequest.getPrice();
        String description = gameRequest.getDescription();
        LocalDate releaseDate = LocalDate.parse(gameRequest.getReleaseDate());
        Clasification clasification = Clasification.valueOf(gameRequest.getClasification());
        int storage = gameRequest.getStorage();
        int memory = gameRequest.getMemory();

        game.setName(name);
        game.setPrice(price);
        game.setDescription(description);
        game.setReleaseDate(releaseDate);
        game.setClasification(clasification);
        game.setStorage(storage);
        game.setMemory(memory);
        game.setAdministrator(administrator);
        game.setCustomers(customers);

        return gameRepository.save(game);
    }

    public Game update(GameRequest gameRequest, int id) {

        Game game = findById(id);

        if (game == null) {
            return null;
        }

        String name = gameRequest.getName();
        BigDecimal price = gameRequest.getPrice();
        String description = gameRequest.getDescription();
        LocalDate releaseDate = LocalDate.parse(gameRequest.getReleaseDate());
        Clasification clasification = Clasification.valueOf(gameRequest.getClasification());
        int storage = gameRequest.getStorage();
        int memory = gameRequest.getMemory();

        game.setName(name);
        game.setPrice(price);
        game.setDescription(description);
        game.setReleaseDate(releaseDate);
        game.setClasification(clasification);
        game.setStorage(storage);
        game.setMemory(memory);

        return gameRepository.save(game);
    }

    public Game delete(int id) {

        Game game = findById(id);

        if (game == null) {
            return null;
        }

        gameRepository.delete(game);
        return game;
    }

    private Administrator findAdministratorById(int id) {
        Optional<Administrator> optional = administratorRepository.findById(id);
        return optional.orElse(null);
    }

}
