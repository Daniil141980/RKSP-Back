--changeset Korshunov Daniil:create-account
create sequence if not exists account_pk_seq start 1 increment 1;

create table if not exists account (
                                    id bigint primary key,
                                    email text not null check ( length(email) > 0 ),
                                    password text not null check (length(password) > 0 ),
                                    full_name text not null check (length(full_name) > 0 ),
                                    role text not null check (length(role) > 0 )
);

alter table account alter column id set default nextval('account_pk_seq');
