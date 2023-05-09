package edu.uptc.proyecto.services;

import edu.uptc.proyecto.entities.Administrator;
import edu.uptc.proyecto.entities.Customer;
import edu.uptc.proyecto.repositories.AdministratorRepository;
import edu.uptc.proyecto.repositories.CustomerRepository;
import edu.uptc.proyecto.request.CredentialRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdministratorRepository administratorRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(AdministratorRepository administratorRepository, CustomerRepository customerRepository) {
        this.administratorRepository = administratorRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Object login(CredentialRequest credentialRequest) {

        for (Administrator administrator : administratorRepository.findAll()) {

            if (administrator.getMail().equals(credentialRequest.getUser())) {

                if (passwordEncoder.matches(credentialRequest.getPassword(), administrator.getPassword())) {
                    return administrator;
                }
            }
        }

        for (Customer customer : customerRepository.findAll()) {
            if (customer.getNickName().equals(credentialRequest.getUser())) {
                if (passwordEncoder.matches(credentialRequest.getPassword(), customer.getPassword())) {
                    return customer;
                }
            }
        }

        return null;
    }

}
