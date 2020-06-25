CREATE SEQUENCE resources_seq
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    NO CYCLE
    CACHE 1;

CREATE TABLE resources
(
    id            bigint                 not null default nextval('resources_seq'),
    uuid          character varying(255) not null
        constraint resources_uuid_uk UNIQUE,
    name          character varying(255) not null,
    description   text,
    owner_id      character varying(255) not null,
    quantity_unit character varying(255) not null,
    version       integer                not null default 0,
    CONSTRAINT resources_pkey PRIMARY KEY (id)
);

CREATE TABLE activity_resources
(
    activity_id bigint  not null,
    resource_id bigint  not null,
    quantity    integer not null default 1,
    CONSTRAINT activities_resources_pkey PRIMARY KEY (activity_id, resource_id),
    CONSTRAINT activities_resources_activity_fkey FOREIGN KEY (activity_id) references activities (id),
    CONSTRAINT activities_resources_resource_fkey FOREIGN KEY (resource_id) references resources (id)
)
