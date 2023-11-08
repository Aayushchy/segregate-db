package com.esewa.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
        transactionManagerRef = "lowEndServerTransactionManager"
)
public class PostgresDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.low-end-server")
    public DataSourceProperties lowEndServerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource lowEndServerDataSource() {
        return lowEndServerDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean lowEndServerEntityManagerFactory(@Qualifier("lowEndServerDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.esewa.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getHibernateProperties());
        return em;
    }

    private Properties getHibernateProperties() {
        Properties hibernateProperties = new Properties();
//        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.put("hibernate.show_sql", true);
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager lowEndServerTransactionManager(
            @Qualifier("lowEndServerEntityManagerFactory") LocalContainerEntityManagerFactoryBean lowEndServerEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(lowEndServerEntityManagerFactory.getObject()));
    }

}
