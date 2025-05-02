--changeset Lysenko Artem:add-foreign-key-in-comment
alter table comment add foreign key (publication_id) references publication(id);


--changeset Lysenko Artem:refactor-account
alter table account rename column full_name to nickname;
alter table account add column first_name text check ( length(first_name) > 0 );
alter table account add column last_name text check ( length(last_name) > 0 );
alter table account add column middle_name text check ( length(middle_name) > 0 );
alter table account add column birth_date timestamp;
alter table account add column is_banned bool not null default false;
alter table account add constraint nickname_unique unique(nickname);

--changeset Lysenko Artem:add-profile-picture-table
create table if not exists profile_picture
(
    id bigint primary key,
    photo_name_in_directory text,
    initial_photo_name text,
    account_id bigint references publication(id),
    set_date timestamp not null
);

