package edu.uptc.proyecto.services;

import edu.uptc.proyecto.entities.Token;
import edu.uptc.proyecto.repositories.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token save(String rol) {

        Token token = new Token();
        UUID uuid = UUID.randomUUID();

        token.setAccessToken(uuid.toString());
        token.setRol(rol);

        return tokenRepository.save(token);
    }

    public Token delete(String access_token) {

        Token token = findToken(access_token);

        if (token == null) {
            return null;
        }

        tokenRepository.delete(token);
        return token;
    }

    public Token validateAdministrator(String access_token) {

        Token token = findToken(access_token);

        if (token == null || !token.getRol().equals("administrator")) {
            return null;
        }

        return token;
    }

    public Token validateCustomer(String access_token) {

        Token token = findToken(access_token);

        if (token == null || !token.getRol().equals("customer")) {
            return null;
        }

        return token;
    }

    private Token findToken(String access_token) {

        for (Token token : tokenRepository.findAll()) {
            if (token.getAccessToken().equals(access_token)) {
                return token;
            }
        }

        return null;
    }
}
