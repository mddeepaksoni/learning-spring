package me.deepak.learning.spring.data.jpa.repository.support.integrator;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class MetadataExtractorIntegrator implements Integrator {

    private static final MetadataExtractorIntegrator INTEGRATOR = new MetadataExtractorIntegrator();

    private Database database;

    private MetadataExtractorIntegrator() {
    }

    public static MetadataExtractorIntegrator getInstance() {
        return INTEGRATOR;
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public void integrate(
            Metadata metadata,
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
        database = metadata.getDatabase();
    }

    @Override
    public void disintegrate(
            SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
    }
}
