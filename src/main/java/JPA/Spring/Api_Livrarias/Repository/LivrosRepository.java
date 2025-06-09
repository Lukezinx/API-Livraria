package JPA.Spring.Api_Livrarias.Repository;

import JPA.Spring.Api_Livrarias.molder.Autor;
import JPA.Spring.Api_Livrarias.molder.GeneroLivro;
import JPA.Spring.Api_Livrarias.molder.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivrosRepositoryTest
 */

public interface LivrosRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {



    // Query method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);


    // Essa é uma personalisada
    // JPQL -> referencia as entidades e as propriedades
    @Query("select  l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> ListarDiferentesLivros();


    // para maiores usa-se 3 aspas duplas
    @Query("""
            select distinct l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileiro'
            order by l.genero
            """)
    List<String> listarPorGeneroBrasieliro();

    // Quando utiliza o @query
    // Usando parametros com a Query
    // named param -> parametros nomeados

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro,@Param("paramOrdenacao") String nomePropriedade );

    // positional param -> parametros positionais

    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParam(@Param("genero") GeneroLivro generoLivro,@Param("paramOrdenacao") String nomePropriedade );

    // Serve apenas para delete ou update
    @Modifying  // vai precisar quando fizer modificaçoes no SQL, pois precisa abrir uma transaçao
    @Transactional // faz a Transação
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);


    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);
}
