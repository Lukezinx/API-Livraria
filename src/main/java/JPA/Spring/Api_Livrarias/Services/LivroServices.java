package JPA.Spring.Api_Livrarias.Services;

import JPA.Spring.Api_Livrarias.Repository.LivrosRepository;
import JPA.Spring.Api_Livrarias.Security.SecurityService;
import JPA.Spring.Api_Livrarias.molder.GeneroLivro;
import JPA.Spring.Api_Livrarias.molder.Livro;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import JPA.Spring.Api_Livrarias.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static JPA.Spring.Api_Livrarias.Repository.Specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroServices {

    private final LivrosRepository livrosRepository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro) {
        validator.validarLivro(livro);
        Usuario usuario = securityService.usuarioLogado();
        livro.setUsuario(usuario);
        return livrosRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livrosRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livrosRepository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicascao,
                                Integer pagina,
                                Integer tamanhoPagina) {

        // select * from livro where isbn = :isbn and titulo and
        // metodo direto
//        Specification<Livro> specs = Specification.where(LivroSpecs.isbnEqual(isbn))
//                        .and(LivroSpecs.tituloLike(titulo))
//                        .and(LivroSpecs.generoEqual(genero));



        //Where -> deprecated
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if( isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        if(anoPublicascao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicascao));
        }

        if(nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);


        return  livrosRepository.findAll(specs,pageRequest);
    }

    public void atualizar(Livro livro) {

        if(livro.getId() == null){
            throw new IllegalArgumentException
                    ("Para atualizar é nescessario que o livro ja esteja salvo na base");
        }
        validator.validarLivro(livro);
        livrosRepository.save(livro);
    }
}
