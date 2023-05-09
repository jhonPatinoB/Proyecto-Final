package edu.uptc.proyecto.request;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class GameRequest {

    @NotNull(message = "El campo nombre no puede ser nulo")
    @NotBlank(message = "El campo nombre  no puede estar vacío")
    @Size(max = 50, message = "El campo nombre no puede tener más de 50 caracteres")
    private final String name;

    @NotNull(message = "El campo precio no puede ser nulo")
    @Positive(message = "El campo precio debe ser positivo")
    @Digits(integer = 10, fraction = 2, message = "El campo precio debe tener un máximo de 10 dígitos enteros y 2 dígitos decimales")
    private final BigDecimal price;

    @NotNull(message = "El campo descripción no puede ser nulo")
    @NotBlank(message = "El campo descripción  no puede estar vacío")
    @Size(max = 50, message = "El campo descripción no puede tener más de 50 caracteres")
    private final String description;

    @NotNull(message = "El campo releaseDate no puede ser nulo")
    @NotBlank(message = "El campo releaseDate no puede estar vacío")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de la fecha debe ser yyyy-MM-dd")
    private final String releaseDate;

    @NotNull(message = "El campo clasificación no puede ser nulo")
    @NotBlank(message = "El campo clasificación no puede estar vacío")
    @Pattern(regexp = "^(EC|E|E10_PLUS|T|M|AO)$", message = "La clasificación debe ser: EC, E, E10_PLUS, T, M o AO.")
    private final String clasification;

    @NotNull(message = "El campo almacenamiento no puede ser nulo")
    @Positive(message = "El campo precio debe ser positivo")
    @Digits(integer = 10, fraction = 2, message = "El campo almacenamiento debe tener un máximo de 10 dígitos enteros y 2 decimales")
    private final int storage;

    @NotNull(message = "El campo memoria no puede ser nulo")
    @Positive(message = "El campo memoria debe ser positivo")
    @Digits(integer = 10, fraction = 2, message = "El campo memoria debe tener un máximo de 10 dígitos enteros y 2 decimales")
    private final int memory;

    public GameRequest(String name, BigDecimal price, String description, String releaseDate, String clasification, int storage, int memory) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
        this.clasification = clasification;
        this.storage = storage;
        this.memory = memory;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getClasification() {
        return clasification;
    }

    public int getStorage() {
        return storage;
    }

    public int getMemory() {
        return memory;
    }

}
