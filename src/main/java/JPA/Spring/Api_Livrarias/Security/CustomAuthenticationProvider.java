package JPA.Spring.Api_Livrarias.Security;

import JPA.Spring.Api_Livrarias.Services.UsuarioServices;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private final UsuarioServices usuarioServices;
    private final PasswordEncoder encoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = usuarioServices.obterPorLogin(login);

        if(usuarioEncontrado == null){
            throw  new UsernameNotFoundException("Usuario e/ou senha Incoretos!");
        }

        String senhaCriptografada =  usuarioEncontrado.getSenha();

        boolean senhasBatem = encoder.matches(senhaDigitada,senhaCriptografada);

        if(senhasBatem){
            return new CustomAuthentication(usuarioEncontrado);
        }

         throw  new UsernameNotFoundException("Usuario e/ou senha Incoretos! ");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
