package com.xlaser4j.demo.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @package: com.xlaser4j.demo.config
 * @author: Elijah.D
 * @time: 2019/12/18 17:22
 * @description:
 * @modified: Elijah.D
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.xlaser4j.demo.repository.read", entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanOfRead", transactionManagerRef = "platformTransactionManagerOfRead")
public class JpaReadConfig {
    @Autowired
    JpaProperties prop;

    @Autowired
    @Qualifier("readDataSource")
    DataSource readDataSource;

    /**
     * transaction
     *
     * @param builder
     * @return
     */
    @Bean
    PlatformTransactionManager platformTransactionManagerOfRead(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBeanOfRead(builder).getObject()));
    }

    /**
     * entityManagerFactory
     *
     * @param builder
     * @return
     */
    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanOfRead(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(readDataSource)
                .properties(prop.getProperties())
                .persistenceUnit("read")
                .packages("com.xlaser4j.demo.entity")
                .build();
    }
}
