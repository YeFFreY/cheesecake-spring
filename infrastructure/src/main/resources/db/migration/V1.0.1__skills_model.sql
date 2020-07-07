CREATE SEQUENCE skills_seq
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    NO CYCLE
    CACHE 1;

CREATE TABLE skills
(
    id          bigint                 not null default nextval('skills_seq'),
    uuid        character varying(255) not null constraint skills_uuid_uk UNIQUE,
    name        character varying(255) not null,
    description text,
    created_by  character varying(255) not null,
    version     integer not null default 0,
    CONSTRAINT skills_pkey PRIMARY KEY (id)
)
