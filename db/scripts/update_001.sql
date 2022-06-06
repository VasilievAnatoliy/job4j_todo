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






