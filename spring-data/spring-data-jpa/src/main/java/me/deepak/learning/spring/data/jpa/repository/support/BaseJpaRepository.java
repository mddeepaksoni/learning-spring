package me.deepak.learning.spring.data.jpa.repository.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends JpaRepository<T, ID> {

    <S extends T> S persist(S entity);

    <S extends T> List<S> persistAll(Iterable<S> entities);

    <S extends T> S merge(S entity);

    <S extends T> List<S> mergeAll(Iterable<S> entities);
}
