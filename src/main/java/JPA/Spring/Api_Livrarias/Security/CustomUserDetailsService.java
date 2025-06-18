package JPA.Spring.Api_Livrarias.Security;

import JPA.Spring.Api_Livrarias.Services.UsuarioServices;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioServices services;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario  usuario = services.obterPorLogin(login);

        if (usuario == null){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
