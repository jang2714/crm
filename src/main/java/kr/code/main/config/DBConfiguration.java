package kr.code.main.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(basePackages = {"kr.code.main.*"})
@EnableTransactionManagement
@RequiredArgsConstructor
public class DBConfiguration {
    private final DataSource dataSource;

    @Primary
    @Bean
    protected LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setPackagesToScan("org.springframework.data.jpa.convert.threeten", "kr.code.main.*");
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(japProperties());

        return entityManagerFactory;
    }


    /**
     * properties 값을 map으로 변환한다.
     *
     * @return
     */
    @Bean
    public Properties japProperties() {
        Properties properties = new Properties();
        properties.putAll(jpaProperties().getProperties());
        properties.put("hibernate.physical_naming_strategy", "kr.code.main.config.PhysicalNamingStrategyImpl");
        return properties;
    }

    /**
     * hiberate 설정
     *
     * @return
     */
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

    /**
     * exception translator 정의
     *
     * @return
     */
    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }


    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(HibernateSettings hibernateSettings) {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean	
    @ConfigurationProperties(prefix = "spring.jpa.properties.hibernate")
    public HibernateSettings hibernateSettings() {
        return new HibernateSettings();
    }

}
