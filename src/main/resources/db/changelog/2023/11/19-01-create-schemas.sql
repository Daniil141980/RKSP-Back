--liquibase formatted sql

--changeset Artem Lysenko:create-subject
create sequence if not exists subject_pk_seq start 1 increment 1;

create table if not exists subject (
                                       id bigint primary key,
                                       name text not null check ( length(name) > 0 )
    );

alter table subject alter column id set default nextval('subject_pk_seq');

--changeset Artem Lysenko:create-course
create sequence if not exists course_pk_seq start 1 increment 1;

create table if not exists course (
                                      course_number int primary key,
                                      description text not null check ( length(description) > 0 )
    );

alter table course alter column course_number set default nextval('course_pk_seq');

--changeset Artem Lysenko:create-topic-type
create sequence if not exists topic_type_pk_seq start 1 increment 1;

create table if not exists topic_type (
                                          id bigint primary key,
                                          type int check ( type >= 0 )
    );

alter table topic_type alter column id set default nextval('topic_type_pk_seq');

--changeset Artem Lysenko:create-subject-topic
create sequence if not exists subject_topic_pk_seq start 1 increment 1;

create table if not exists subject_topic (
                                             id bigint primary key,
                                             type_id bigint references topic_type(id) not null,
    subject_id bigint references subject(id) not null,
    course_number int references course(course_number) not null
    );

alter table subject_topic alter column id set default nextval('subject_topic_pk_seq');

--changeset Artem Lysenko:create-publication
create sequence if not exists publication_pk_seq start 1 increment 1;

create table if not exists publication (
                                           id bigint primary key,
                                           title text not null,
                                           description text,
                                           subject_topic_id bigint references subject_topic(id) not null,
    created_at timestamp not null
    );

alter table publication alter column id set default nextval('publication_pk_seq');

--changeset Artem Lysenko:create-file
create sequence if not exists file_pk_seq start 1 increment 1;

create table if not exists file (
                                    id bigint primary key,
                                    file_name_in_directory text not null,
                                    initial_file_name text not null,
                                    publication_id bigint references publication(id) not null
    );

alter table file alter column id set default nextval('file_pk_seq');

--changeset Artem Lysenko:create-comment
create sequence if not exists comment_pk_seq start 1 increment 1;

create table if not exists comment (
                                       id bigint primary key,
                                       content text not null check ( length(content) > 0 ),
    publication_id bigint not null,
    created_at timestamp not null
    );

alter table comment alter column id set default nextval('comment_pk_seq');
