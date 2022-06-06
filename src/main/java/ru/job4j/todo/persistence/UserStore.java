package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;
import java.util.function.Function;

@Repository
public class UserStore {
    private final SessionFactory sf;

    public UserStore(SessionFactory sf) {
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

    public Optional<User> add(User user) {
        Optional rsl = Optional.empty();
        try {
         rsl = Optional.of(
                 tx(session -> session.save(user)));
        } catch (Exception e) {
            e.printStackTrace();
        }
         return rsl;
    }

    public Optional<User> findUserByNameAndPwd(String name, String password) {
     return tx(session -> session.createQuery(
                      "from User s where s.name = :fName and s.password = :fPassword")
                .setParameter("fName", name)
                .setParameter("fPassword", password)
             .uniqueResultOptional()
        );

    }
}
