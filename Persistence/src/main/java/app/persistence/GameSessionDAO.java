package app.persistence;

import app.model.GameSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import app.persistence.util.HibernateUtil;

import java.util.List;

public class GameSessionDAO {

    public void save(GameSession session) {
        Transaction tx = null;
        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            tx = hibernateSession.beginTransaction();
            hibernateSession.saveOrUpdate(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<GameSession> findAll() {
        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            return hibernateSession.createQuery("from GameSession", GameSession.class).list();
        }
    }

    public List<GameSession> findUnfinishedByNickname(String nickname) {
        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            return hibernateSession.createQuery(
                            "from GameSession where nickname = :nickname and won = false and endTime is null",
                            GameSession.class)
                    .setParameter("nickname", nickname)
                    .list();
        }
    }

    public GameSession findById(Long id) {
        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            return hibernateSession.get(GameSession.class, id);
        }
    }

    // For leaderboard: get all finished games sorted by won, attempts, duration
    public List<GameSession> findFinishedGamesOrdered() {
        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            return hibernateSession.createQuery(
                            "from GameSession where endTime is not null order by won desc, attempts asc, endTime asc", GameSession.class)
                    .list();
        }
    }
}
