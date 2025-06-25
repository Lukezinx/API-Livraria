package JPA.Spring.Api_Livrarias.Controller.Commom;

import JPA.Spring.Api_Livrarias.Controller.dto.ErroCampo;
import JPA.Spring.Api_Livrarias.Controller.dto.ErroResposta;
import JPA.Spring.Api_Livrarias.exceptions.CampoInvalidoException;
import JPA.Spring.Api_Livrarias.exceptions.OperacaoNaoPermitidaException;
import JPA.Spring.Api_Livrarias.exceptions.RegistroDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handlerMethothArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Erro de validação");
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaDeErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro de validacao", listaDeErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.conflito(e.getMessage());

    }


    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e ) {
        return  ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return  new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro validação", List.of(new ErroCampo(e.getCampos(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAccessDeniedException(AccessDeniedException e){
        return new ErroResposta(HttpStatus.FORBIDDEN.value(), "acesso negado", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e) {
        log.error("Erro inesperado", e);
        return new ErroResposta
                (HttpStatus
                        .INTERNAL_SERVER_ERROR
                        .value(),
                        "Ocorreu um erro inesperado, Entre em contato com o Suporte ",
                        List.of());
    }


}
