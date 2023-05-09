package edu.uptc.proyecto.services;

import edu.uptc.proyecto.request.UserRequest;
import edu.uptc.proyecto.entities.Administrator;
import edu.uptc.proyecto.repositories.AdministratorRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Administrator> getAll() {
        return administratorRepository.findAll();
    }

    public Administrator findById(int id) {
        Optional<Administrator> optional = administratorRepository.findById(id);
        return optional.orElse(null);
    }

    public Administrator update(UserRequest userRequest, int id) {

        Administrator administrator = findById(id);

        if (administrator == null) {
            return null;
        }

        String name = userRequest.getName();
        String lastName = userRequest.getLastName();
        String mail = userRequest.getMail();
        String password = passwordEncoder.encode(userRequest.getPassword());

        administrator.setName(name);
        administrator.setLastName(lastName);
        administrator.setMail(mail);
        administrator.setPassword(password);

        return administratorRepository.save(administrator);
    }

}
