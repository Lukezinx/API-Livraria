package JPA.Spring.Api_Livrarias.Security;


import JPA.Spring.Api_Livrarias.Services.UsuarioServices;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioServices usuarioServices;

    public Usuario usuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login  = userDetails.getUsername();
        return usuarioServices.obterPorLogin(login);
    }
}
