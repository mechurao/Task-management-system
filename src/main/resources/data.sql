create sequence user_id_seq start with 1 increment by 1;
create sequence project_id_seq start with 1 increment by 1;
create sequence task_id_seq start with 1 increment by 1;

-- id, name, email
INSERT INTO user VALUES
                        (next value for user_id_seq, 'John Doe', 'john.doe@gmail.com'),
                        (next value for user_id_seq, 'Jane Smith', 'jane.smith@gmail.com');
-- id, user_id, name, description, created_at
INSERT INTO project VALUES
                        (next value for project_id_seq, 1, 'Johns project', '#1', CURRENT_TIMESTAMP),
                        (next value for project_id_seq, 2, 'Janes project', 'To-do list', CURRENT_TIMESTAMP);
-- id, user_id, project_id, name, description, status, created_at
INSERT INTO task VALUES
                     (next value for task_id_seq, 1, 1, 'Spravit API', 'API ma byt pre noveho klienta', 'DONE', CURRENT_TIMESTAMP),
                     (next value for task_id_seq, 1, 1, 'Test new version', 'Unit testy + integracne testy', 'NEW', CURRENT_TIMESTAMP),
                     (next value for task_id_seq, 2, 2, 'Bug fix', null, 'NEW', CURRENT_TIMESTAMP),
                     (next value for task_id_seq, 2, null, 'Important meeting', 'floor 10, room 202', 'NEW', CURRENT_TIMESTAMP);