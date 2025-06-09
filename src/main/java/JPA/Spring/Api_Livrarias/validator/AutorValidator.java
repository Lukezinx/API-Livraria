package JPA.Spring.Api_Livrarias.validator;


import JPA.Spring.Api_Livrarias.Repository.AutorRepository;
import JPA.Spring.Api_Livrarias.exceptions.RegistroDuplicadoException;
import JPA.Spring.Api_Livrarias.molder.Autor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)){
            throw  new RegistroDuplicadoException("Autor já Cadastrado");
        }
    }

    private Boolean existeAutorCadastrado( Autor autor) {
        Optional<Autor> autorEncontrado = repository
                .findByNomeAndDataNascimentoAndNacionalidade(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
