CREATE SEQUENCE users_seq
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    NO CYCLE
    CACHE 1;

CREATE TABLE users
(
    id       bigint                 not null default nextval('users_seq'),
    uuid     character varying(255) not null
        constraint users_uuid_uk UNIQUE,
    username character varying(100) not null,
    password character varying(100) not null,
    email    character varying(320) not null,
    version  integer                not null default 0,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);