package JPA.Spring.Api_Livrarias.Services;


import JPA.Spring.Api_Livrarias.Repository.UsuarioRepository;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServices {

    public final UsuarioRepository repository;
    public final PasswordEncoder encoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return repository.findByLogin(login);
    }

    public Usuario obeterPorEmail(String email){
        return  repository.findByEmail(email);

    }

}
