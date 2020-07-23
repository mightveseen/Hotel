package com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {
        "com.senlainc.gitcourses.javatraining.petushokvaliantsin.repository",
        "com.senlainc.gitcourses.javatraining.petushokvaliantsin.service",
        "com.senlainc.gitcourses.javatraining.petushokvaliantsin.controller",
        "com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.mapper"})
@PropertySource(value = "classpath:/properties/base.properties")
@EnableTransactionManagement
public class AppConfig {

    @Value("${DATA_BASE.URL}")
    private String baseUrl;
    @Value("${DATA_BASE.USER}")
    private String baseUser;
    @Value("${DATA_BASE.PASSWORD}")
    private String basePassword;
    @Value("${DATA_BASE.PACKAGE}")
    private String[] packageToScan;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.connection.useUnicode", "true");
        hibernateProperties.setProperty("hibernate.connection.charSet", "UTF-8");
        hibernateProperties.setProperty("hibernate.connection.characterEncoding", "UTF-8");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return hibernateProperties;
    }

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(baseUrl);
        dataSource.setUsername(baseUser);
        dataSource.setPassword(basePassword);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabase(Database.MYSQL);
        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(driverManagerDataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());
        entityManagerFactoryBean.setPersistenceUnitName("persistence");
        entityManagerFactoryBean.setPackagesToScan(packageToScan);
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public CriteriaBuilder criteriaBuilder(EntityManagerFactory emf) {
        return emf.getCriteriaBuilder();
    }
}
