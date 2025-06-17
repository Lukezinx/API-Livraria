package JPA.Spring.Api_Livrarias.Controller.Mappers;


import JPA.Spring.Api_Livrarias.Controller.dto.UsuarioDTO;
import JPA.Spring.Api_Livrarias.molder.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {


    Usuario toEntity(UsuarioDTO dto);
}