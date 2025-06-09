package JPA.Spring.Api_Livrarias.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //basico - projetos pequenos, com muitas pessoas ele quebra. tbm pode resolver problemas
    /*
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);

        return ds;
    }
     */

    // opcional, pois o string já faz isso.
    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); //maximo de 10 conexoes
        config.setMinimumIdle(1); //diz quanto vai ser liberada de inicio do pool
        config.setPoolName("library-Pool");
        config.setMaxLifetime(600000); //600k de ms (10 minutos)
        config.setConnectionTimeout(100000); //timeOut para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); //query de teste

        return new HikariDataSource(config);
    }
}
