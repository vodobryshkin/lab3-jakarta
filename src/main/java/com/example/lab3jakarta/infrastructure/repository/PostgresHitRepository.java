package com.example.lab3jakarta.infrastructure.repository;

import com.example.lab3jakarta.domain.entities.Hit;
import com.example.lab3jakarta.domain.repository.HitRepository;
import com.example.lab3jakarta.infrastructure.entities.DbHit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

import java.util.List;

/**
 * Реализация репозитория для добавления попаданий в БД PostgreSQL
 */
@Named("hitRepository")
@ApplicationScoped
public class PostgresHitRepository implements HitRepository {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .applySetting(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5432/appdb")
            .applySetting(AvailableSettings.JAKARTA_JDBC_USER, "app")
            .applySetting(AvailableSettings.JAKARTA_JDBC_PASSWORD, "Vova20102006.")
            .applySetting(AvailableSettings.JAKARTA_JDBC_DRIVER, "org.postgresql.Driver")
            .applySetting(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
            .applySetting(AvailableSettings.HBM2DDL_AUTO, "update")
            .applySetting(AvailableSettings.SHOW_SQL, "true")
            .applySetting(AvailableSettings.FORMAT_SQL, "true")
            .applySetting(AvailableSettings.POOL_SIZE, "5")
            .build();

    private final Metadata metadata = new MetadataSources(registry)
            .addAnnotatedClass(DbHit.class)
            .buildMetadata();

    private final SessionFactory sessionFactory = metadata.buildSessionFactory();

    /**
     * Метод для добавления попадания в репозиторий
     *
     * @param hit логическая модель попадания
     */
    @Override
    public void addHit(Hit hit) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(new DbHit(hit));
                tx.commit();
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    /**
     * Метод для получения результатов всех попаданий
     *
     * @return результаты всех попаданий
     */
    @Override
    public List<Hit> getAllHits() {
        try (Session session = sessionFactory.openSession()) {
            session.setDefaultReadOnly(true);
            Transaction tx = session.beginTransaction();
            try {
                List<Hit> hits = session.createQuery(
                                "select new Hit(h.id, h.x, h.y, h.radius, h.status) from DbHit h", Hit.class)
                        .setReadOnly(true)
                        .getResultList();

                tx.commit();
                return hits;
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
