package JPA.Spring.Api_Livrarias.Repository;


import JPA.Spring.Api_Livrarias.Services.TransacaoServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class Transaction {

    @Autowired
    TransacaoServices transacaoServices;

    @Test
//    @Transactional
    public void transacaoSimples() {
         transacaoServices.executar();
    }


    @Test

    public void estadoManeger() {
        transacaoServices.atualizacaoSemAtualizar();
    }
}
