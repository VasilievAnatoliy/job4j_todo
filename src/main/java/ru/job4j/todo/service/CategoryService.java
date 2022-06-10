package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryStore;

import java.util.Collection;

@Service
public class CategoryService {
    private final CategoryStore categoryStore;

    public CategoryService(CategoryStore categoryStore) {
        this.categoryStore = categoryStore;
    }

    public Category findById(int id) {
        return categoryStore.findById(id);
    }

    public Collection findAll() {
        return categoryStore.findAll();
    }
}
