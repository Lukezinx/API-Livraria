package JPA.Spring.Api_Livrarias.Controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

//DTO - DATA TRANSFER OBJECT
public record AutorDTO(
        UUID id,
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrao") @NotBlank(message = "Campo obrigatorio") String nome,
        @Past(message = "Nao pode ser uma data futura") @NotNull(message = "Campo obrigatorio") LocalDate dataNascimento,
        @Size(min = 2, max = 50, message = "Campo fora do tamanho padrao") @NotBlank(message = "Campo obrigatorio") String nacionalidade
) {

}
