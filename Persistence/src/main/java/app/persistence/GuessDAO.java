package app.persistence;

import app.model.Guess;
import org.hibernate.Session;
import org.hibernate.Transaction;
import app.persistence.util.HibernateUtil;

import java.util.List;

public class GuessDAO {

    public void save(Guess guess) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(guess);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<Guess> findByGameSessionId(Long gameSessionId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Guess where gameSession.id = :gsid order by attemptNumber asc", Guess.class)
                    .setParameter("gsid", gameSessionId)
                    .list();
        }
    }
}
