package edu.uptc.proyecto.controllers.api;

import edu.uptc.proyecto.entities.Administrator;
import edu.uptc.proyecto.entities.Customer;
import edu.uptc.proyecto.request.UserRequest;
import edu.uptc.proyecto.response.ResponseHandler;
import edu.uptc.proyecto.services.AdministratorService;
import edu.uptc.proyecto.services.CustomerService;
import edu.uptc.proyecto.services.TokenService;
import edu.uptc.proyecto.validation.CustomValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private Boolean status;
    private String message;
    private Object data;

    private final CustomerService customerService;
    private final AdministratorService administratorService;
    private final TokenService tokenService;

    public CustomerController(CustomerService customerService, AdministratorService administratorService, TokenService tokenService) {
        this.customerService = customerService;
        this.administratorService = administratorService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<Object> index(@RequestHeader("Authorization") String authorization) {

        status = true;
        message = "Listado de clientes";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = customerService.getAll();

        return ResponseHandler.generateResponse(status, message, data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@RequestHeader("Authorization") String authorization, @PathVariable int id) {

        status = true;
        message = "Busqueda de clientes por Id";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = customerService.findById(id);

        if (data == null) {
            message = "No ha sido posible encontrar al cliente, verifique el Id del cliente";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String authorization, @Valid @RequestBody UserRequest userRequest, BindingResult result, @PathVariable int id) {

        status = true;
        message = "Cliente actualizado correctamente";

        if (tokenService.validateCustomer(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (validateNickName(userRequest.getNickName(), id)) {
            message = "No ha sido posible actualizar al cliente, el nickName ya está registrado";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (validateMail(userRequest.getMail(), id)) {
            message = "No ha sido posible actualizar al cliente, el mail ya está registrado";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (result.hasErrors()) {
            Map<String, Object> errors = CustomValidation.getErrorsMap(result);
            message = "No ha sido posible actualizar al cliente";
            return ResponseHandler.generateResponse(false, message, errors);
        }

        data = customerService.update(userRequest, id);

        if (data == null) {
            message = "No ha sido posible actualizar al cliente, verifique el Id del cliente";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@RequestHeader("Authorization") String authorization, @PathVariable int id) {

        status = true;
        message = "Cliente eliminado correctamente";

        if (tokenService.validateCustomer(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = customerService.delete(id);

        if (data == null) {
            message = "No ha sido posible eliminar al cliente, verifique el Id del cliente";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, null);
    }

    @PostMapping("add-game/{customerId}/{gameId}")
    public ResponseEntity<Object> addGame(@RequestHeader("Authorization") String authorization, @PathVariable int customerId, @PathVariable int gameId) {

        status = true;
        message = "Juego agregado a la lista del cliente";

        if (tokenService.validateCustomer(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (customerService.findById(customerId) == null || customerService.findGameById(gameId) == null) {
            message = "No se ha podido agregar el juego a la lista del usuario, verifique el Id de usuario y juego";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = customerService.addGame(customerId, gameId);

        if (data == null) {
            message = "El juego no se puede agregar debido a que ya está en la lista";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    @PostMapping("remove-game/{customerId}/{gameId}")
    public ResponseEntity<Object> removeGame(@RequestHeader("Authorization") String authorization, @PathVariable int customerId, @PathVariable int gameId) {

        status = true;
        message = "Juego retirado de la lista del cliente";

        if (tokenService.validateCustomer(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (customerService.findById(customerId) == null || customerService.findGameById(gameId) == null) {
            message = "No se ha podido retirar el juego de la lista del usuario, verifique el Id de usuario y juego";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = customerService.removeGame(customerId, gameId);
        return ResponseHandler.generateResponse(status, message, data);
    }

    private Boolean validateNickName(String nickName, int id) {

        for (Customer customer : customerService.getAll()) {
            if (customer.getNickName().equals(nickName) && customer.getId() != id) {
                return true;
            }
        }

        return false;
    }

    private Boolean validateMail(String mail, int id) {

        for (Administrator administrator : administratorService.getAll()) {
            if (administrator.getMail().equals(mail) && administrator.getId() != id) {
                return true;
            }
        }

        for (Customer customer : customerService.getAll()) {
            if (customer.getMail().equals(mail) && customer.getId() != id) {
                return true;
            }
        }

        return false;
    }

}
