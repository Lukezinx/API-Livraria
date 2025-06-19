package JPA.Spring.Api_Livrarias.Controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "Campo obrigatorio")
        String login,

        @Email(message = "Email invalido")
        String email,

        @NotBlank(message = "Campo obrigatorio")
        String senha,
        List<String> roles) {
}
