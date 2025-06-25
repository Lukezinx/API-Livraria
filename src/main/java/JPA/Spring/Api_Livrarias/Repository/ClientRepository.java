package JPA.Spring.Api_Livrarias.Repository;

import JPA.Spring.Api_Livrarias.molder.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Client findByClientId(String clientId);
}
