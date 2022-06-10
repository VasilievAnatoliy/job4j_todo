CREATE TABLE IF NOT EXISTS users (
     id         SERIAL PRIMARY KEY,
     name       VARCHAR UNIQUE,
     password   TEXT
);


CREATE TABLE IF NOT EXISTS items (
     id          SERIAL PRIMARY KEY,
     name        TEXT,
     description TEXT,
     created     TIMESTAMP,
     done        BOOLEAN,
     user_id INT NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS categories (
    id          SERIAL PRIMARY KEY,
    name        TEXT
);

CREATE TABLE IF NOT EXISTS items_categories (
    item_id INT NOT NULL REFERENCES items(id),
    categories_id INT NOT NULL REFERENCES categories(id)
);

INSERT INTO categories (name) VALUES ('Отдел-продаж'), ('Склад'), ('Производство');






