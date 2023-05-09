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
@RequestMapping(value = "/administrators")
public class AdministratorController {

    private Boolean status;
    private String message;
    private Object data;

    private final AdministratorService administratorService;
    private final CustomerService customerService;
    private final TokenService tokenService;

    public AdministratorController(AdministratorService administratorService, CustomerService customerService, TokenService tokenService) {
        this.administratorService = administratorService;
        this.customerService = customerService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<Object> index(@RequestHeader("Authorization") String authorization) {

        status = true;
        message = "Listado de administradores";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = administratorService.getAll();

        return ResponseHandler.generateResponse(status, message, data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@RequestHeader("Authorization") String authorization, @PathVariable int id) {

        status = true;
        message = "Busqueda de administrador por Id";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = administratorService.findById(id);

        if (data == null) {
            message = "No ha sido posible encontrar al administrador, verifique el Id del administrador";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String authorization, @Valid @RequestBody UserRequest userRequest, BindingResult result, @PathVariable int id) {

        status = true;
        message = "Administrador actualizado correctamente";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (validateMail(userRequest.getMail(), id)) {
            message = "No ha sido posible actualizar al administrador, el mail ya está registrado";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (result.hasErrors()) {
            Map<String, Object> errors = CustomValidation.getErrorsMap(result);
            message = "No ha sido posible actualizar al administrador";
            return ResponseHandler.generateResponse(false, message, errors);
        }

        data = administratorService.update(userRequest, id);

        if (data == null) {
            message = "No ha sido posible actualizar al administrador, verifique el Id del administrador";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
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
