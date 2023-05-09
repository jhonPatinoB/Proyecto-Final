package edu.uptc.proyecto.services;

import edu.uptc.proyecto.entities.Customer;
import edu.uptc.proyecto.entities.Game;
import edu.uptc.proyecto.repositories.CustomerRepository;
import edu.uptc.proyecto.repositories.GameRepository;
import edu.uptc.proyecto.request.UserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final GameRepository gameRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, GameRepository gameRepository) {
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer findById(int id) {
        Optional<Customer> optional = customerRepository.findById(id);
        return optional.orElse(null);
    }

    public Customer save(UserRequest userRequest) {

        Customer customer = new Customer();
        List<Game> games = new ArrayList<>();

        String name = userRequest.getName();
        String lastName = userRequest.getLastName();
        String mail = userRequest.getMail();
        String nickName = (userRequest.getNickName() == null) ? mail : userRequest.getNickName();
        String password = passwordEncoder.encode(userRequest.getPassword());
        String rol = "customer";

        customer.setName(name);
        customer.setLastName(lastName);
        customer.setMail(mail);
        customer.setNickName(nickName);
        customer.setPassword(password);
        customer.setRol(rol);
        customer.setGames(games);

        return customerRepository.save(customer);
    }

    public Customer update(UserRequest userRequest, int id) {

        Customer customer = findById(id);

        if (customer == null) {
            return null;
        }

        String name = userRequest.getName();
        String lastName = userRequest.getLastName();
        String mail = userRequest.getMail();
        String nickName = (userRequest.getNickName() == null) ? mail : userRequest.getNickName();
        String password = passwordEncoder.encode(userRequest.getPassword());

        customer.setName(name);
        customer.setLastName(lastName);
        customer.setMail(mail);
        customer.setNickName(nickName);
        customer.setPassword(password);

        return customerRepository.save(customer);
    }

    public Customer delete(int id) {

        Customer customer = findById(id);

        if (customer == null) {
            return null;
        }

        customerRepository.delete(customer);
        return customer;
    }

    public Game findGameById(int id) {
        Optional<Game> optional = gameRepository.findById(id);
        return optional.orElse(null);
    }

    public Customer addGame(int customerId, int gameId) {

        Customer customer = findById(customerId);
        Game game = findGameById(gameId);

        for (Game auxGame : customer.getGames()) {
            if (auxGame.getId() == game.getId()) {
                return null;
            }
        }

        customer.getGames().add(game);
        game.getCustomers().add(customer);

        customerRepository.save(customer);
        gameRepository.save(game);

        return customer;
    }

    public Customer removeGame(int customerId, int gameId) {

        Customer customer = findById(customerId);
        Game game = findGameById(gameId);

        customer.getGames().removeIf(gameDelete -> gameDelete.getId() == game.getId());
        game.getCustomers().removeIf(customerDelete -> customerDelete.getId() == customer.getId());

        customerRepository.save(customer);
        gameRepository.save(game);

        return customer;
    }

}
