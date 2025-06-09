package JPA.Spring.Api_Livrarias.exceptions;


import lombok.Getter;

public class CampoInvalidoException extends RuntimeException {

    @Getter
    private String campos;

    public CampoInvalidoException(String campos, String mensage){
        super(mensage);
        this.campos = campos;
    }
}
