package JPA.Spring.Api_Livrarias.Repository;

import JPA.Spring.Api_Livrarias.molder.Autor;
import JPA.Spring.Api_Livrarias.molder.GeneroLivro;
import JPA.Spring.Api_Livrarias.molder.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivrosRepository livrosRepository;

    @Test
    public void salvarTeste(){
        Autor autor = new Autor();
        autor.setNome("Pedro");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1954,1,31));

        var autorSalvo = repository.save(autor);

        System.out.println("Autor  foi salvo " + autorSalvo);
    }

    @Test
    public void atualizarTest() {

         var id = UUID.fromString("8cdc709c-7309-4317-b639-456959588a80");
         Optional<Autor> possivelAutor = repository.findById(id);

         if(possivelAutor.isPresent()) {
             Autor  autorEncontrado = possivelAutor.get();

             System.out.println("Dados do autor: ");
             System.out.println(autorEncontrado);

             autorEncontrado.setDataNascimento(LocalDate.of(1960,1,30));

             repository.save(autorEncontrado);
         }

    }

    @Test
    public void listarTodos() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void testCount() {
        System.out.println("contagem dos autores "+ repository.count());
    }

    @Test
    public void testDeleteId(){
        var id = UUID.fromString("24add22a-7b9b-4105-85ec-0d142fe92dd9");
        repository.deleteById(id);
    }


    @Test
    public void testDeleteObject(){
        var id = UUID.fromString("a8c35f6a-98fe-4197-9bf7-9169a1ba851f");
        var maria = repository.findById(id).get(); // sei que esta presente no banco de datos !!cuidado
        repository.delete(maria);
    }

    @Test
    public void salvarAutorcomLivrosTestes() {
        Autor autor2 = new Autor();
        autor2.setNome("Matheus");
        autor2.setNacionalidade("Europeu");
        autor2.setDataNascimento(LocalDate.of(2000,3,27));

        Livro livro = new Livro();
        livro.setIsbn("9024-7892");
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Vida");
        livro.setDataPublicacao(LocalDate.of(2001,6,27));
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setAutor(autor2);

        Livro livro2 = new Livro();
        livro2.setIsbn("87614-98765");
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("Utilizade");
        livro2.setDataPublicacao(LocalDate.of(2005,7,29));
        livro2.setPreco(BigDecimal.valueOf(200));
        livro2.setAutor(autor2);

        autor2.setLivros(new ArrayList<>());
        autor2.getLivros().add(livro);
        autor2.getLivros().add(livro2);

        repository.save(autor2);

        livrosRepository.saveAll(autor2.getLivros());
    }

    @Test
    public void listarAutor() {
        var id = UUID.fromString("a8c35f6a-98fe-4197-9bf7-9169a1ba851f");
        var autor = repository.findById(id).get(); // sei que esta presente no banco de datos !!cuidado

        List<Livro> livroListas = livrosRepository.findByAutor(autor);
        autor.setLivros(livroListas);


        autor.getLivros().forEach(System.out::println);
    }

}
