--changeset Korshunov Daniil:drop-column-from-file-table
ALTER TABLE file
    DROP COLUMN publication_id;

--changeset Korshunov Daniil:create-publication_file-table
CREATE TABLE publication_file
(
    publication_id BIGINT REFERENCES publication (id),
    file_id        BIGINT REFERENCES file (id),
    PRIMARY KEY (publication_id, file_id)
);

--changeset Korshunov Daniil:set-not-null-to-role_id-and-create-column-photo
ALTER TABLE account ALTER COLUMN role_id set not null;
alter table account add column photo_id bigint references file(id);

--changeset Korshunov Daniil:drop-table-profile_picture
DROP TABLE profile_picture;

--changeset Korshunov Daniil:add-column-last-updated-at-to-comment-and-add-column-parent
alter table comment add column last_updated_at timestamp not null default now();
alter table comment add column parent bigint references comment(id);

--changeset Korshunov Daniil:create-news_publication-table
create table news_publication (
                    id bigint primary key references publication(id)
);

--changeset Korshunov Daniil:delete-column-subject_topic_id-from-publication-and-add-column-is_thread
alter table publication drop column subject_topic_id;
alter table publication add column supports_thread bool not null default false;

--changeset Korshunov Daniil:create-subject_topic_publication-table
CREATE TABLE subject_topic_publication
(
    publication_id BIGINT REFERENCES publication (id),
    subject_topic_id BIGINT REFERENCES subject_topic(id),
    PRIMARY KEY (publication_id, subject_topic_id)
);
