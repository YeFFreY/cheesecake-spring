CREATE SEQUENCE activities_seq
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    NO CYCLE
    CACHE 1;

CREATE TABLE activities
(
    id          bigint                 not null default nextval('activities_seq'),
    uuid        character varying(255) not null
        constraint activities_uuid_uk UNIQUE,
    name        character varying(255) not null,
    description text,
    owner_id    character varying(255) not null,
    version     integer not null default 0,
    CONSTRAINT activities_pkey PRIMARY KEY (id)
)
