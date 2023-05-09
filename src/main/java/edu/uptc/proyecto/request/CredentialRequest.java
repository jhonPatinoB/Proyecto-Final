package edu.uptc.proyecto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CredentialRequest {

    @NotNull(message = "El campo user no puede ser nulo")
    @NotBlank(message = "El campo user no puede estar vacío")
    @Size(max = 50, message = "El campo user no puede tener más de 50 caracteres")
    private final String user;

    @NotNull(message = "El campo contraseña no puede ser nulo")
    @NotBlank(message = "El campo contraseña no puede estar vacío")
    @Size(max = 50, message = "El campo contraseña no puede tener más de 50 caracteres")
    private final String password;

    public CredentialRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
