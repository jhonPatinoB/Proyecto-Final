package edu.uptc.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.uptc.proyecto.enums.Clasification;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(length = 50, nullable = false)
    private String description;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Clasification clasification;

    @Column(nullable = false)
    private int storage;

    @Column(nullable = false)
    private int memory;

    @ManyToOne
    @JsonIgnore
    private Administrator administrator;

    @ManyToMany(mappedBy = "games", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Customer> customers;

    public Game() {

    }

    public Game(String name, BigDecimal price, String description, LocalDate releaseDate, Clasification clasification, int storage, int memory) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
        this.clasification = clasification;
        this.storage = storage;
        this.memory = memory;
        this.customers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Clasification getClasification() {
        return clasification;
    }

    public void setClasification(Clasification clasification) {
        this.clasification = clasification;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", clasification=" + clasification +
                ", storage=" + storage +
                ", memory=" + memory +
                ", administrator=" + administrator +
                ", customers=" + customers +
                '}';
    }

}

