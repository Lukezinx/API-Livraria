package JPA.Spring.Api_Livrarias.Controller.Mappers;


import JPA.Spring.Api_Livrarias.Controller.dto.AutorDTO;
import JPA.Spring.Api_Livrarias.molder.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
