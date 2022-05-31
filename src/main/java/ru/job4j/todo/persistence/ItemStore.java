package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.util.List;

@Repository
public class ItemStore {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List findAllDone() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item where done = true ").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List findAllNotDone() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item where done = false ").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void update(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    public void done(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update Item s set s.done = true where s.id = :fId")
        .setParameter("fId", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
