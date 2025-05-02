--changeset Korshunov Daniil:refactor-publication-table
delete from publication;
alter table publication add column if not exists account_id bigint references account(id) not null;
alter sequence publication_pk_seq restart with 1;

--changeset Korshunov Daniil:refactor-comment-table
delete from comment;
alter table comment add column if not exists account_id bigint references account(id) not null;
alter sequence comment_pk_seq restart with 1;