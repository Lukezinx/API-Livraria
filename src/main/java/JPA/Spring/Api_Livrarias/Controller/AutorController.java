package JPA.Spring.Api_Livrarias.Controller;

import JPA.Spring.Api_Livrarias.Controller.Mappers.AutorMapper;
import JPA.Spring.Api_Livrarias.Controller.dto.AutorDTO;
import JPA.Spring.Api_Livrarias.Services.AutorServices;
import JPA.Spring.Api_Livrarias.molder.Autor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@Slf4j
//http://localhost:8080/autores

@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorServices services;
    private final AutorMapper autorMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        log.info("Cadastrando novo autor: {}", dto.nome());

        Autor autorEntidade = autorMapper.toEntity(dto);
        services.salvar(autorEntidade);
        URI location = gerarHeaderLocation(autorEntidade.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = services.obterPorId(idAutor);

        return services
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
        /*
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = autorMapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();

         */
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        log.info("deletendo um autor de ID: {}", id);
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = services.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        services.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = services.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(autorMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }


    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Object> atualizar(@Valid @PathVariable("id") String id, @RequestBody AutorDTO dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = services.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        services.atualizar(autor);
        return ResponseEntity.noContent().build();

    }

}