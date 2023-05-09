package edu.uptc.proyecto.controllers.auth;

import edu.uptc.proyecto.entities.Administrator;
import edu.uptc.proyecto.entities.Customer;

import edu.uptc.proyecto.request.CredentialRequest;
import edu.uptc.proyecto.request.UserRequest;
import edu.uptc.proyecto.response.ResponseHandler;
import edu.uptc.proyecto.services.AdministratorService;
import edu.uptc.proyecto.services.AuthService;
import edu.uptc.proyecto.services.CustomerService;
import edu.uptc.proyecto.services.TokenService;
import edu.uptc.proyecto.validation.CustomValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class AuthController {

    private Boolean status;
    private String message;
    private Object data;

    private final AuthService authService;
    private final TokenService tokenService;
    private final AdministratorService administratorService;
    private final CustomerService customerService;

    public AuthController(AuthService authService, TokenService tokenService, AdministratorService administratorService, CustomerService customerService) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.administratorService = administratorService;
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRequest userRequest, BindingResult result) {

        status = true;
        message = "Cliente registrado correctamente";

        if (result.hasErrors()) {
            Map<String, Object> errors = CustomValidation.getErrorsMap(result);
            message = "No ha sido posible registrar al cliente";
            return ResponseHandler.generateResponse(false, message, errors);
        }

        if (validateMail(userRequest.getMail())) {
            message = "No ha sido posible registrar al cliente, el mail ya está registrado";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (validateNickName(userRequest.getNickName())) {
            message = "No ha sido posible registrar al cliente, el nickName ya está registrado";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = customerService.save(userRequest);

        return ResponseHandler.generateResponse(status, message, data);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody CredentialRequest credentialRequest, BindingResult result) {

        status = true;
        message = "Usuario logeado correctamente";
        Object userSession = authService.login(credentialRequest);

        if (result.hasErrors()) {
            Map<String, Object> errors = CustomValidation.getErrorsMap(result);
            message = "No ha sido posible iniciar sesión";
            return ResponseHandler.generateResponse(false, message, errors);
        }

        if (userSession == null) {
            message = "Las credenciales no coinciden con los registros";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (userSession instanceof Administrator administrator) {
            data = tokenService.save(administrator.getRol());
            return ResponseHandler.generateResponse(status, message, data);
        }

        Customer customer = (Customer) userSession;

        data = tokenService.save(customer.getRol());
        return ResponseHandler.generateResponse(status, message, data);
    }

    @PostMapping("/logout/{token}")
    public ResponseEntity<Object> logout(@PathVariable String token) {

        status = true;
        message = "Sesión finalizada, se ha eliminado el token";
        data = tokenService.delete(token);

        if (data == null) {
            message = "No ha sido posible encontar el token de sesión";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    private Boolean validateMail(String mail) {

        for (Customer customer : customerService.getAll()) {
            if (customer.getMail().equals(mail)) {
                return true;
            }
        }

        for (Administrator administrator : administratorService.getAll()) {
            if (administrator.getMail().equals(mail)) {
                return true;
            }
        }

        return false;
    }

    private Boolean validateNickName(String nickName) {

        for (Customer customer : customerService.getAll()) {
            if (customer.getNickName().equals(nickName)) {
                return true;
            }
        }

        return false;
    }

}
