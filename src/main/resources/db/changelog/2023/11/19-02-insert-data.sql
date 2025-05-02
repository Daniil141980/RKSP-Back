--liquibase formatted sql

--changeset Artem Lysenko:insert-data-into-subject
insert into subject (name) values ('МАТЕМАТИКА');
insert into subject (name) values ('ИСТОРИЯ');
insert into subject (name) values ('ВЕБ-ПРОГРАММИРОВАНИЕ');
insert into subject (name) values ('ПРОГРАММИРОВАНИЕ');
insert into subject (name) values ('НИЗКОУРОВНЕВОЕ ПРОГРАММИРОВАНИЕ');
insert into subject (name) values ('АРХИТЕКТУРА КОМПЬЮТЕРА');

--changeset Artem Lysenko:insert-data-into-course
insert into course (description) values ('1 курс бакалавриата');
insert into course (description) values ('2 курс бакалавриата');
insert into course (description) values ('3 курс бакалавриата');
insert into course (description) values ('4 курс бакалавриата');
insert into course (description) values ('1 курс магистратуры');
insert into course (description) values ('2 курс магистратуры');

--changeset Artem Lysenko:insert-data-into-topic-type
insert into topic_type (type) values (0);
insert into topic_type (type) values (1);
insert into topic_type (type) values (2);

--changeset Artem Lysenko:insert-data-into-subject-topic
insert into subject_topic (type_id, subject_id, course_number) values (1, 1, 1);
insert into subject_topic (type_id, subject_id, course_number) values (2, 2, 1);
insert into subject_topic (type_id, subject_id, course_number) values (3, 1, 3);
insert into subject_topic (type_id, subject_id, course_number) values (1, 4, 1);
insert into subject_topic (type_id, subject_id, course_number) values (1, 3, 2);
insert into subject_topic (type_id, subject_id, course_number) values (2, 5, 2);

--changeset Daniil Korshunov:insert-data-into-publication
insert into publication (title, description, subject_topic_id, created_at) values ('Контрольная работа 1',
                                                                                   'Файлы к кр 1',
                                                                                   1,
                                                                                   '2023-10-30');
insert into publication (title, description, subject_topic_id, created_at) values ('Записи по матлогу',
                                                                                   'Все конспекты семинаров',
                                                                                   2,
                                                                                   '2022-11-14');
insert into publication (title, description, subject_topic_id, created_at) values ('Полезные курсы по Кумиру',
                                                                                   'Самые полезные курсы для Кумир-разработчиков',
                                                                                   3,
                                                                                   '2020-12-31');

--changeset Daniil Korshunov:insert-data-into-comment
insert into comment (content, publication_id, created_at) values ('Охххх, сложная контрольная...', 1, '2023-10-30');
insert into comment (content, publication_id, created_at) values ('Ураааа, не зря не ходил', 2, '2022-11-14');
insert into comment (content, publication_id, created_at) values ('Чтоооо, Кумир!?!?!?', 3, '2020-12-31');
insert into comment (content, publication_id, created_at) values ('Да-да, он самый)', 3, '2020-12-31');

--changeset Daniil Korshunov:insert-data-into-file
insert into file (file_name_in_directory, initial_file_name, publication_id) values ('1Kr_1.pdf', 'Kr_1.pdf', 1);
insert into file (file_name_in_directory, initial_file_name, publication_id) values ('2Konspect.png', 'Konspect.png', 2);
insert into file (file_name_in_directory, initial_file_name, publication_id) values ('3Konspect.jpg', 'Konspect.jpg', 2);
insert into file (file_name_in_directory, initial_file_name, publication_id) values ('4Kumir.pdf', 'Kumir.pdf', 3);