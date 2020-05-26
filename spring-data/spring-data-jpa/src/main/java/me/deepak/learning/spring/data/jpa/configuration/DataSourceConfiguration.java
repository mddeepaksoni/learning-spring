package me.deepak.learning.spring.data.jpa.configuration;

import me.deepak.learning.spring.data.jpa.properties.DataSourceProperties;
import me.deepak.learning.spring.data.jpa.repository.support.BaseSimpleJpaRepository;
import me.deepak.learning.spring.data.jpa.repository.support.integrator.MetadataExtractorIntegrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.*;

@Configuration
@EnableJpaRepositories(basePackages = {"me.deepak.learning.spring.data.jpa.repository"},
        repositoryBaseClass = BaseSimpleJpaRepository.class,
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties("datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource(@Autowired DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
                .url(dataSourceProperties.getJdbcUrl())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Autowired DataSource dataSource, @Autowired DataSourceProperties dataSourceProperties) {
        List<String> packages = new ArrayList<>();
        Optional.ofNullable(dataSourceProperties.getHibernate()).ifPresent(hibernateProperties -> {
            Optional.ofNullable(hibernateProperties.getPackages()).ifPresent(hibernatePackages -> packages.addAll(Arrays.asList(hibernatePackages)));
        });

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.integrator_provider", (IntegratorProvider) () -> Collections.singletonList(MetadataExtractorIntegrator.getInstance()));
        Optional.ofNullable(dataSourceProperties.getHibernate()).ifPresent(hibernateProperties -> {
            Optional.ofNullable(hibernateProperties.getDialect()).ifPresent(dialect -> jpaProperties.put("hibernate.dialect", dialect));

            Optional.ofNullable(hibernateProperties.getBatchSize()).ifPresent(batchSize -> jpaProperties.put("hibernate.jdbc.batch_size", batchSize.toString()));
            Optional.ofNullable(hibernateProperties.getOrderInserts()).ifPresent(orderInserts -> jpaProperties.put("hibernate.order_inserts", orderInserts.toString()));
            Optional.ofNullable(hibernateProperties.getOrderUpdates()).ifPresent(orderUpdates -> jpaProperties.put("hibernate.order_updates", orderUpdates.toString()));

            Optional.ofNullable(hibernateProperties.getShowSql()).ifPresent(showSql -> jpaProperties.put("hibernate.show_sql", showSql.toString()));
            Optional.ofNullable(hibernateProperties.getFormatSql()).ifPresent(formatSql -> jpaProperties.put("hibernate.format_sql", formatSql.toString()));
            Optional.ofNullable(hibernateProperties.getGenerateStatistics()).ifPresent(generateStatistics -> jpaProperties.put("hibernate.generate_statistics", generateStatistics.toString()));
        });

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages(packages.toArray(new String[]{}))
                .properties(jpaProperties)
                .build();
    }

    @Bean
    public JpaTransactionManager transactionManager(@Autowired EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
