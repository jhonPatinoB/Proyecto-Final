package edu.uptc.proyecto.repositories;

import edu.uptc.proyecto.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
