package JPA.Spring.Api_Livrarias.Security;


import JPA.Spring.Api_Livrarias.Services.UsuarioServices;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import jakarta.persistence.Index;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String SENHA_PADRAO = "1245";

    private final UsuarioServices usuarioServices;



    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken)  authentication;
        OAuth2User oauth2User = auth2AuthenticationToken.getPrincipal();
        String email = oauth2User.getAttribute("email");

        Usuario usuario = usuarioServices.obeterPorEmail(email);

        if(usuario == null){
             usuario = cadastroUsuario(email);
        }


        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario cadastroUsuario (String email){
        Usuario usuario;
        usuario = new Usuario();
        usuario.setEmail(email);

        usuario.setLogin(obterLoginPorEmail(email));

        usuario.setSenha(SENHA_PADRAO);
        usuario.setRoles(List.of("OPERADOR"));

        usuarioServices.salvar(usuario);
        return usuario;

    }

    private String obterLoginPorEmail(String email) {

        return email.substring(0, email.indexOf("@"));
    }
}
