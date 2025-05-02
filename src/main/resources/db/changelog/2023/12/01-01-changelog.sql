--changeset Korshunov Daniil:add-not-null-profile-picture
alter table profile_picture alter column photo_name_in_directory set not null;
alter table profile_picture alter column initial_photo_name set not null;

--changeset Korshunov Daniil:add-one-to-one-profile_picture-and-account
alter table profile_picture drop column account_id;
alter table profile_picture drop column set_date;
alter table profile_picture rename column id to account_id;
alter table profile_picture add foreign key (account_id) references account(id);

--changeset Korshunov Daniil:add-column-is_anonymous-to-comment
alter table comment add column is_anonymous bool not null default false;

--changeset Korshunov Daniil:add-column-status-to-account
alter table account add column description text;
alter table account add constraint email_unique unique(email);