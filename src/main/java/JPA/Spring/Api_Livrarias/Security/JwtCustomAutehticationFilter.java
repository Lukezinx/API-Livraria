package JPA.Spring.Api_Livrarias.Security;

import JPA.Spring.Api_Livrarias.Services.UsuarioServices;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtCustomAutehticationFilter extends OncePerRequestFilter {

    private final UsuarioServices services;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain) throws ServletException, IOException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (deveConverter(authentication)){
            String login = authentication.getName();
            Usuario usuario = services.obterPorLogin(login);
            if(usuario !=  null){
                authentication = new CustomAuthentication(usuario);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request, response);

    }

    private  boolean deveConverter(Authentication authentication){
        return authentication != null && authentication instanceof JwtAuthenticationToken;
    }
}

