package JPA.Spring.Api_Livrarias.Controller;


import JPA.Spring.Api_Livrarias.Services.ClientServices;
import JPA.Spring.Api_Livrarias.molder.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
@Slf4j

public class ClientController {

    private final ClientServices services;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody  Client client){
        log.info("Registrando novo client {} com o escopo: {}", client.getClientId(), client.getScope());
        services.salvar(client);
    }

}
