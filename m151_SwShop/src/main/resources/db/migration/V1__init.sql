CREATE
EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE public.price
(
    id       bigint  NOT NULL,
    amount   real    NOT NULL,
    discount integer NOT NULL,
    CONSTRAINT pk_price PRIMARY KEY (id)
);

CREATE SEQUENCE price_sequence OWNED BY public.price.id;

CREATE TABLE public.benutzer
(
    id            bigint       NOT NULL,
    user_password varchar(255) NOT NULL,
    username      varchar(255) NOT NULL,
    user_group    varchar(255),
    CONSTRAINT benutzer_pkey PRIMARY KEY (id),
    CONSTRAINT uk_user_name UNIQUE (username)
);

CREATE SEQUENCE benutzer_sequence OWNED BY public.benutzer.id;

CREATE TABLE public.fruit
(
    id         bigint       NOT NULL,
    fruit_name varchar(255) NOT NULL,
    fruit_type varchar(255),
    price_id   bigint,
    CONSTRAINT pk_fruit PRIMARY KEY (id),
    CONSTRAINT uk_fruit_name UNIQUE (fruit_name),
    CONSTRAINT fk_price FOREIGN KEY (price_id)
        REFERENCES price (id)
);

CREATE SEQUENCE fruit_sequence OWNED BY public.fruit.id;

INSERT INTO public.benutzer (id, username, user_password, user_group)
VALUES (1, 'testli', crypt('test', gen_salt('bf', 8)), 'ADMIN');
