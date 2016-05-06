package com.accenture.javadojo.michael.humphrey_orgchart.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.accenture.javadojo.michael.humphrey_orgchart.dao.PersonDao;
import com.accenture.javadojo.michael.humphrey_orgchart.dao.PersonDaoJdbc;
import com.accenture.javadojo.michael.humphrey_orgchart.dao.PersonDaoJpa;
import com.accenture.javadojo.michael.humphrey_orgchart.dao.PersonDaoSpring;
import com.accenture.javadojo.michael.humphrey_orgchart.dao.PersonDaoStub;
import com.accenture.javadojo.michael.humphrey_orgchart.model.Person;
import com.accenture.javadojo.michael.humphrey_orgchart.services.PersonService;
import com.accenture.javadojo.michael.humphrey_orgchart.services.PersonServiceImpl;

/**
 * Configuration class for spring bean generation.
 *
 * @author michael.humphrey
 *
 */
@Configuration
public class SpringConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/javadojo");
        dataSource.setUsername("javadojo");
        dataSource.setPassword("newpd");
        return dataSource;
    }

    @Bean(name = "dbc")
    public DatabaseConfig dbc() {

        DatabaseConfig dbc = new DatabaseConfig();
        dbc.setDataSource(dataSource());
        dbc.initDataBase();
        return dbc;
    }

    @Bean
    public PersonService service() {

        PersonServiceImpl personServiceImpl = new PersonServiceImpl();
        personServiceImpl.setPersonDao(personDaoJdbc());
        return personServiceImpl;
    }

    @Bean(name = "personServiceImpl")
    public PersonServiceImpl personServiceImpl() {

        PersonServiceImpl personServiceImpl = new PersonServiceImpl();
        personServiceImpl.setPersonDao(personDaoJpa());
        return personServiceImpl;
    }

    @Bean(name = "serviceStub")
    public PersonService serviceStub() {

        PersonServiceImpl personService = new PersonServiceImpl();
        personService.setPersonDao(personDaoStub());
        return personService;

    }

    @Bean
    public PersonDao<Person, Long> personDaoStub() {

        return new PersonDaoStub();
    }

    @Bean
    public PersonDao<Person, Long> personDaoSpring() {

        return new PersonDaoSpring();
    }

    @Bean(name = "personDaoJpa")
    public PersonDao<Person, Long> personDaoJpa() {

        return new PersonDaoJpa();
    }

    @Bean
    public PersonDao<Person, Long> personDaoJdbc() {

        PersonDaoJdbc personDaoJdbc = new PersonDaoJdbc();
        personDaoJdbc.setDataSource(dataSource());
        personDaoJdbc.beanInit();
        personDaoJdbc.setConn(null);

        return personDaoJdbc;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public EntityManager getEntityManager() {

        EntityManager em;
        EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("myJPA");
        em = factory.createEntityManager();
        return em;
    }
}
