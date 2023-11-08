package com.esewa.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
//        basePackages = {"com.esewa.repository"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "highEndServerTransactionManager"
)
public class MysqlDatabaseConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.high-end-server")
    public DataSourceProperties highEndServerSataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource highEndServerDataSource() {
        return highEndServerSataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean highEndServerEntityManagerFactory(@Qualifier("highEndServerDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.esewa.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getHibernateProperties());
        em.setPersistenceUnitName("mysql");
        return em;
    }

    private Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        return hibernateProperties;
    }

    @Bean
    @Primary
    public PlatformTransactionManager highEndServerTransactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean highEndServerEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(highEndServerEntityManagerFactory.getObject()));
    }
}
