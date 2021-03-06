package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

@Repository
public class ItemStore {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Item add(Item item) {
       tx(session -> session.save(item));
       return item;
    }

    public Item findById(int id) {
       return tx(session -> (Item) session.createQuery(
               "select distinct s from Item s join fetch s.categories where s.id = :fId")
                       .setParameter("fId", id).uniqueResult()
               );
    }

    public List findAll() {
        return tx(session -> session.createQuery(
             "select distinct s from Item s join fetch s.categories").list()
        );
    }

    public List findAllDone() {
        return tx(session -> session.createQuery(
                "select distinct s from Item s join fetch s.categories where s.done = true").list()
        );
    }

    public List findAllNotDone() {
        return tx(session -> session.createQuery(
                "select distinct s from Item s join fetch s.categories where s.done = false").list()
        );
    }

    public void update(Item item) {
        tx(session -> {
            session.update(item);
                    return null;
                }
        );
    }

    public void delete(int id) {
        tx(session -> session.createQuery(
                "delete from Item where id = :fId")
                .setParameter("fId", id).executeUpdate()
        );
    }

    public void done(int id) {
        tx(session -> session.createQuery(
                "update Item s set s.done = true where s.id = :fId")
                .setParameter("fId", id).executeUpdate()
        );
    }
}
