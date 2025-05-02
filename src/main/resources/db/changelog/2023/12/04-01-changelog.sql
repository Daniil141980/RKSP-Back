--changeset Lysenko Artem:create-roles-table

create sequence if not exists role_pk_seq start 1 increment 1;

create table if not exists role
(
    id   bigint primary key,
    name text not null check ( length(name) > 0 )
);

alter table role
    alter column id set default nextval('role_pk_seq');

alter table account drop column role;
alter table account add column role_id bigint references role(id);