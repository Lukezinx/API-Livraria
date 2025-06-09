package JPA.Spring.Api_Livrarias.Controller.Mappers;

import JPA.Spring.Api_Livrarias.Controller.dto.CadastroLivroDTO;
import JPA.Spring.Api_Livrarias.Controller.dto.ResultadoPesquisaLivroDTO;
import JPA.Spring.Api_Livrarias.Repository.AutorRepository;
import JPA.Spring.Api_Livrarias.molder.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
     AutorRepository autorRepository;

     @Mapping(target = "autor",expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
   public abstract Livro toEntity(CadastroLivroDTO dto);


    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
