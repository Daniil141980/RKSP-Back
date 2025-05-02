--changeset Korshunov Daniil:drop-column-course_number-from-subject_topic
alter table subject_topic alter column course_number drop not null;
alter table subject_topic drop column if exists course_number;

--changeset Korshunov Daniil:add-column-course_number-to-subject
alter table subject add column if not exists course_number int references course(course_number) not null default 1;

--changeset Korshunov Daniil:add-check-to-file-column
alter table file add constraint file_name_in_directory_check check ( length(file_name_in_directory) > 0 );
alter table file add constraint initial_file_name_check check ( length(initial_file_name) > 0 );

--changeset Korshunov Daniil:add-constraint-unique-to-subject
alter table subject add constraint subject_unq unique (name, course_number);

--changeset Korshunov Daniil:delete-from-file
delete from file;
alter sequence file_pk_seq restart with 1;

--changeset Korshunov Daniil:add-not-null-publication-description
alter table publication alter column description set not null;