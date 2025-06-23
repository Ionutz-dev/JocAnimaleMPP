package app.persistence;

import app.model.Config;
import org.hibernate.Session;
import org.hibernate.Transaction;
import app.persistence.util.HibernateUtil;

import java.util.List;

public class ConfigDAO {

    public void save(Config config) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(config);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<Config> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Config", Config.class).list();
        }
    }

    public Config findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Config.class, id);
        }
    }
}
