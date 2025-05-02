--changeset Korshunov Daniil:create-comment_aud-table
CREATE TABLE comment_aud (
                             id bigserial primary key,
                             comment_id bigint references comment(id) not null,
                             content text not null check ( length(content) > 0 ),
                             time_change timestamp not null
);