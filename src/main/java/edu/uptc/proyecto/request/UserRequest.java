package edu.uptc.proyecto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequest {

    @NotNull(message = "El campo nombre no puede ser nulo")
    @NotBlank(message = "El campo nombre  no puede estar vacío")
    @Size(max = 50, message = "El campo nombre no puede tener más de 50 caracteres")
    private final String name;

    @NotNull(message = "El campo apellido no puede ser nulo")
    @NotBlank(message = "El campo apellido no puede estar vacío")
    @Size(max = 50, message = "El campo apellido no puede tener más de 50 caracteres")
    private final String lastName;

    @NotNull(message = "El campo mail no puede ser nulo")
    @NotBlank(message = "El campo mail no puede estar vacío")
    @Size(max = 50, message = "El campo mail no puede tener más de 50 caracteres")
    private final String mail;

    @Size(max = 50, message = "El campo nickName no puede tener más de 50 caracteres")
    private final String nickName;

    @NotNull(message = "El campo contraseña no puede ser nulo")
    @NotBlank(message = "El campo contraseña no puede estar vacío")
    @Size(max = 50, message = "El campo contraseña no puede tener más de 50 caracteres")
    private final String password;

    public UserRequest(String name, String lastName, String mail, String nickName, String password) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.nickName = nickName;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

}
