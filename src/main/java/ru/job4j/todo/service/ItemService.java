package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemStore;

import java.util.List;

@Service
public class ItemService {
    private final ItemStore itemStore;

    public ItemService(ItemStore store) {
        this.itemStore = store;
    }

    public void add(Item item) {
        itemStore.add(item);
    }

    public Item findById(int id) {
        return itemStore.findById(id);
    }

    public List findAll() {
        return itemStore.findAll();
    }

    public List findAllDone() {
        return itemStore.findAllDone();
    }

    public List findAllNotDone() {
        return itemStore.findAllNotDone();
    }

    public void update(Item item) {
        itemStore.update(item);
    }

    public void delete(int id) {
        itemStore.delete(id);
    }

    public void done(int id) {
        itemStore.done(id);
    }
}
