package JPA.Spring.Api_Livrarias.Services;


import JPA.Spring.Api_Livrarias.Repository.AutorRepository;
import JPA.Spring.Api_Livrarias.Repository.LivrosRepository;
import JPA.Spring.Api_Livrarias.molder.Autor;
import JPA.Spring.Api_Livrarias.molder.GeneroLivro;
import JPA.Spring.Api_Livrarias.molder.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoServices {
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivrosRepository livrosRepository;

    @Transactional
    public void executar(){

        //salvar o autor
        Autor autor2 = new Autor();
        autor2.setNome("Jose");
        autor2.setNacionalidade("brasileiro");
        autor2.setDataNascimento(LocalDate.of(2000,3,27));

        autorRepository.save(autor2);



        //salva o livro
        Livro livro = new Livro();
        livro.setIsbn("9024-7892");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Vida");
        livro.setDataPublicacao(LocalDate.of(2001,6,27));
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setAutor(autor2);

        livro.setAutor(autor2);

        livrosRepository.save(livro);

        if (autor2.getNome().equals("Jose")){
            throw  new RuntimeException("RollBack!");
        }
    }


    // Salva sem precisar chamar o metodo Save.
    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livrosRepository.findById(UUID.fromString("a89232a3-970b-4a7b-a316-6b5f1e8c9e39")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024,1,21));

    }
}
