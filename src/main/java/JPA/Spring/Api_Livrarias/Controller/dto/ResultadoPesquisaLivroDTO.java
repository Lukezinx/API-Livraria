package JPA.Spring.Api_Livrarias.Controller.dto;

import JPA.Spring.Api_Livrarias.molder.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO
        (UUID id,
         String isbn,
         String titulo,
         LocalDate dataPublicacao,
         GeneroLivro generoLivro,
         BigDecimal preco,
         AutorDTO autor
        ) {
}
