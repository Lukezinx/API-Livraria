package JPA.Spring.Api_Livrarias.Controller.dto;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
