package com.example.lab3jakarta.infrastructure.repository;

import com.example.lab3jakarta.domain.entities.Hit;
import com.example.lab3jakarta.domain.repository.HitRepository;
import com.example.lab3jakarta.infrastructure.entities.DbHit;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Реализация репозитория для добавления попаданий в БД PostgreSQL
 */
public class PostgresHitRepository implements HitRepository {
    private final EntityManagerFactory emf;
    private final SessionFactory sessionFactory;

    public PostgresHitRepository(String configName) {
        emf = Persistence.createEntityManagerFactory(configName);
        sessionFactory = emf.unwrap(SessionFactory.class);
    }


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
