package JPA.Spring.Api_Livrarias.Repository;

import JPA.Spring.Api_Livrarias.molder.Autor;
import JPA.Spring.Api_Livrarias.molder.GeneroLivro;
import JPA.Spring.Api_Livrarias.molder.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LivrosRepositoryTest {

    @Autowired
    LivrosRepository livrosRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("9021-98765");
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("UIDA");
        livro.setDataPublicacao(LocalDate.of(1963, 1, 27));
        livro.setPreco(BigDecimal.valueOf(100));

        Autor autor = autorRepository.findById(UUID.fromString("6bd7f579-dc9a-4182-827d-ab4ba1062b98")).orElse(null);
        livro.setAutor(autor);

        livrosRepository.save(livro);
    }


    @Test
    public void atualizarAutorDeUmLivro() {
        var livroParaAtualizar = livrosRepository.findById(UUID.fromString("085d9dd1-9d35-4a71-b0ab-388603c11a34")).orElse(null);

        var autorDoLivro = autorRepository.findById(UUID.fromString("e3b6ac53-3fb0-4339-80ce-10fcb177fa1a")).orElse(null);

        livroParaAtualizar.setAutor(autorDoLivro);

        livrosRepository.save(livroParaAtualizar);
    }

    @Test
    public void deletar() {
        livrosRepository.deleteById(UUID.fromString("69b53d6f-f7cb-430b-b515-a609863045c7"));

    }

    @Test
    public void pesquisaPorLivro() {
        List<Livro> pesquisarPorTitulo = livrosRepository.findByTitulo("Vida");
        pesquisarPorTitulo.forEach(System.out::println);

    }

    @Test
    public void listarLivrosComQueryJPQL() {

        var listar = livrosRepository.listarTodosOrdenadoPorTituloAndPreco();
        listar.forEach(System.out::println);

    }

    @Test
    public void listarAutoresDosLivros() {

        var listar = livrosRepository.listarAutoresDosLivros();
        listar.forEach(System.out::println);

    }

    @Test
    public void titulosNaoRepetidos() {

        var listar = livrosRepository.ListarDiferentesLivros();
        listar.forEach(System.out::println);
    }


    @Test
    public void autoresBrasileirosListar() {

        var listar = livrosRepository.listarPorGeneroBrasieliro();
        listar.forEach(System.out::println);

    }

    @Test
    public void listarOgeneroQueryParam() {

        var listar = livrosRepository.findByGenero(GeneroLivro.CIENCIA, "dataPublicacao");
        listar.forEach(System.out::println);

    }

    @Test
    public void listarOgeneroQueryParamPositional() {

        var listar = livrosRepository.findByGeneroPositionalParam(GeneroLivro.CIENCIA, "dataPublicacao");
        listar.forEach(System.out::println);

    }

    @Test
    public void deletePorGenero() {
        livrosRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }


    @Test
    public void updateDataPublicacaoTeste() {
        livrosRepository.updateDataPublicacao(LocalDate.of(2021, 1, 20));
    }
}
