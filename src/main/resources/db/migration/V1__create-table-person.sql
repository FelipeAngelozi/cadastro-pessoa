CREATE TABLE person(

    id INT8 GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(25) NOT NULL UNIQUE,
    birthdate TIMESTAMP NOT NULL,

    PRIMARY KEY(id)
);