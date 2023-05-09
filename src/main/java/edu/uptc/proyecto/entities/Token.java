package edu.uptc.proyecto.entities;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String rol;

    public Token() {

    }

    public Token(String accessToken, String rol) {
        this.accessToken = accessToken;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }

}
