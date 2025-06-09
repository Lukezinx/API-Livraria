package JPA.Spring.Api_Livrarias.Services;

import JPA.Spring.Api_Livrarias.Repository.AutorRepository;
import JPA.Spring.Api_Livrarias.Repository.LivrosRepository;
import JPA.Spring.Api_Livrarias.exceptions.OperacaoNaoPermitidaException;
import JPA.Spring.Api_Livrarias.molder.Autor;
import JPA.Spring.Api_Livrarias.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // -> cria um construtor em campo final e gera um construtor automatico.
public class AutorServices {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivrosRepository livrosRepository;


    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null) {
            throw new IllegalArgumentException
                    ("Para atualizar é nescessario que o autor ja esteja salvo na base");
        }
        validator.validar(autor);
         repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor) {

        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("nao é permitido deletar um autor que possui Autor Possui Livros Cadastrados!");
        }

        repository.delete(autor);
    }


    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor,matcher);

        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        return livrosRepository.existsByAutor(autor);
    }
}
