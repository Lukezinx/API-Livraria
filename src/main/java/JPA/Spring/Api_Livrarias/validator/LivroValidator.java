package JPA.Spring.Api_Livrarias.validator;

import JPA.Spring.Api_Livrarias.Repository.LivrosRepository;
import JPA.Spring.Api_Livrarias.exceptions.CampoInvalidoException;
import JPA.Spring.Api_Livrarias.exceptions.RegistroDuplicadoException;
import JPA.Spring.Api_Livrarias.molder.Livro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;
    private final LivrosRepository repository;


    public void validarLivro(Livro livro) {
        if(exiteLivroIsbn(livro)){
            throw new RegistroDuplicadoException("isbn ja cadastrado");
        }


        if(isPrecoObrigatorioNulo(livro)){
            throw  new CampoInvalidoException("preco", "Para Livros com o ano de Publicaçao a partir de 2020 o preço é obrigatorio");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {

        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean exiteLivroIsbn(Livro livro){
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));

    }
}
