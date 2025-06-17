package JPA.Spring.Api_Livrarias.Repository;

import JPA.Spring.Api_Livrarias.molder.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByLogin(String login);
}
