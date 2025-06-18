package JPA.Spring.Api_Livrarias.Controller;

import JPA.Spring.Api_Livrarias.Controller.Mappers.LivroMapper;
import JPA.Spring.Api_Livrarias.Controller.dto.CadastroLivroDTO;
import JPA.Spring.Api_Livrarias.Controller.dto.ResultadoPesquisaLivroDTO;
import JPA.Spring.Api_Livrarias.Services.LivroServices;
import JPA.Spring.Api_Livrarias.molder.GeneroLivro;
import JPA.Spring.Api_Livrarias.molder.Livro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroServices services;
    private final LivroMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
            Livro livro = mapper.toEntity(dto);
            services.salvar(livro);
            var url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {

        return services.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> deletarLivro(@PathVariable("id") String id){
        return services.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    services.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicascao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina) {

        Page<Livro> paginaResultado = services.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicascao,pagina,tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> atualizarLivros(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto) {

       return services.obterPorId(UUID.fromString(id))
                .map(livro -> {

                    Livro entidateAuxiliar = mapper.toEntity(dto);
                    livro.setDataPublicacao(entidateAuxiliar.getDataPublicacao());
                    livro.setIsbn((entidateAuxiliar.getIsbn()));
                    livro.setTitulo(entidateAuxiliar.getTitulo());
                    livro.setAutor(entidateAuxiliar.getAutor());
                    livro.setPreco(entidateAuxiliar.getPreco());

                    services.atualizar(livro);

                    return ResponseEntity.noContent().build();

                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

}
