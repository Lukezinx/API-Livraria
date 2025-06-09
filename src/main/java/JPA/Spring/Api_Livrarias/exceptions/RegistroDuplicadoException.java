package JPA.Spring.Api_Livrarias.exceptions;

public class RegistroDuplicadoException extends RuntimeException {

    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
