package JPA.Spring.Api_Livrarias.Controller;

import JPA.Spring.Api_Livrarias.Controller.Mappers.UsuarioMapper;
import JPA.Spring.Api_Livrarias.Controller.dto.UsuarioDTO;
import JPA.Spring.Api_Livrarias.Services.UsuarioServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    public final UsuarioServices service;
    public final UsuarioMapper mapper;

    

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
