package edu.uptc.proyecto.controllers.api;

import edu.uptc.proyecto.request.GameRequest;
import edu.uptc.proyecto.response.ResponseHandler;
import edu.uptc.proyecto.services.GameService;
import edu.uptc.proyecto.services.TokenService;
import edu.uptc.proyecto.validation.CustomValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    private Boolean status;
    private String message;
    private Object data;

    private final GameService gameService;
    private final TokenService tokenService;

    public GameController(GameService gameService, TokenService tokenService) {
        this.gameService = gameService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<Object> index() {

        status = true;
        message = "Listado de juegos";
        data = gameService.getAll();

        return ResponseHandler.generateResponse(status, message, data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable int id) {

        status = true;
        message = "Busqueda de juego por Id";
        data = gameService.findById(id);

        if (data == null) {
            message = "No ha sido posible encontrar el juego, verifique el Id del juego";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> store(@RequestHeader("Authorization") String authorization, @Valid @RequestBody GameRequest gameRequest, BindingResult result, @PathVariable int id) {

        status = true;
        message = "Juego registrado correctamente";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (result.hasErrors()) {
            Map<String, Object> errors = CustomValidation.getErrorsMap(result);
            message = "No ha sido posible registrar el juego";
            return ResponseHandler.generateResponse(false, message, errors);
        }

        data = gameService.save(gameRequest, id);

        if (data == null) {
            message = "No ha sido posible registrar el juego, verifique el Id del administrador";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestHeader("Authorization") String authorization, @Valid @RequestBody GameRequest gameRequest, BindingResult result, @PathVariable int id) {

        status = true;
        message = "Juego actualizado correctamente";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        if (result.hasErrors()) {
            Map<String, Object> errors = CustomValidation.getErrorsMap(result);
            message = "No ha sido posible actualizar el juego";
            return ResponseHandler.generateResponse(false, message, errors);
        }

        data = gameService.update(gameRequest, id);

        if (data == null) {
            message = "No ha sido posible actualizar el juego, verifique el Id del juego";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@RequestHeader("Authorization") String authorization, @PathVariable int id) {

        status = true;
        message = "Juego eliminado correctamente";

        if (tokenService.validateAdministrator(authorization) == null) {
            message = "No tiene los permisos para acceder a este recurso";
            return ResponseHandler.generateResponse(false, message, null);
        }

        data = gameService.delete(id);

        if (data == null) {
            message = "No ha sido posible eliminar el juego, verifique el Id del juego";
            return ResponseHandler.generateResponse(false, message, null);
        }

        return ResponseHandler.generateResponse(status, message, null);
    }

}
