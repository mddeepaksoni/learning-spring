package me.deepak.learning.spring.data.jpa.repository.support;

import me.deepak.learning.spring.data.jpa.repository.support.integrator.MetadataExtractorIntegrator;
import me.deepak.learning.spring.data.jpa.repository.support.namedparam.BeanPropertySqlParameterSource;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.*;

public class BaseSimpleJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private final EntityInformation<T, ?> entityInformation;
    private final EntityManager entityManager;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BaseSimpleJpaRepository(Class<T> domainClass, EntityManager entityManager) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager), entityManager);
    }

    public BaseSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;

        this.jdbcTemplate = new NamedParameterJdbcTemplate((DataSource) entityManager.getEntityManagerFactory().getProperties().get("hibernate.connection.datasource"));
    }

    private Optional<Integer> getBatchSize() {
        return Optional.ofNullable(entityManager.getEntityManagerFactory().getProperties().get("hibernate.jdbc.batch_size"))
                .map(batchSize -> batchSize.getClass().equals(String.class) ? Integer.valueOf(String.class.cast(batchSize)) : Integer.class.cast(batchSize));
    }

    private Optional<MetadataExtractorIntegrator> getMetadataExtractorIntegrator() {
        return Optional.ofNullable(entityManager.getEntityManagerFactory().getProperties().get("hibernate.integrator_provider"))
                .flatMap(integratorProvider -> ((IntegratorProvider) integratorProvider).getIntegrators().stream()
                        .filter(integrator -> integrator instanceof MetadataExtractorIntegrator)
                        .map(integrator -> (MetadataExtractorIntegrator) integrator)
                        .findAny());
    }

    @Override
    @Transactional
    public <S extends T> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public <S extends T> List<S> persistAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();

        Iterator<S> iterator = entities.iterator();
        while (iterator.hasNext()) {
            result.add(persist(iterator.next()));
        }

        return result;
    }

    @Override
    @Transactional
    public <S extends T> S merge(S entity) {
        entityManager.merge(entity);
        return entity;
    }

    private <S extends T> S mergeBatch(S entity, List<SqlParameterSource> params, Integer batchSize) {
        Objects.requireNonNull(params);

        Optional.ofNullable(entity).ifPresent(item -> {
            params.add(new BeanPropertySqlParameterSource(entity));
        });

        if (Objects.equals(params.size(), batchSize)) {
            String sql = "merge into tbl_employees(first_name, last_name, email, joining_date) key (email) values (:firstName, :lastName, :email, :joiningDate)";
            jdbcTemplate.batchUpdate(sql, params.toArray(new SqlParameterSource[]{}));

            params.clear();
        }

        return entity;
    }

    @Override
    @Transactional
    public <S extends T> List<S> mergeAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();

        Integer batchSize = getBatchSize().orElse(1);
        Iterator<S> iterator = entities.iterator();
        List<SqlParameterSource> params = new ArrayList<>(batchSize);
        while (iterator.hasNext()) {
            result.add(mergeBatch(iterator.next(), params, batchSize));
        }
        mergeBatch(null, params, params.size());

        return result;
    }
}
