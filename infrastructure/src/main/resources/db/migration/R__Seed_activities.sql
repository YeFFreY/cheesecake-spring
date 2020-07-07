DELETE
FROM activities where uuid in ('123e4567-e89b-12d3-a456-556642440000');
INSERT INTO activities (uuid, name, description, created_by)
VALUES ('123e4567-e89b-12d3-a456-556642440000', 'A simple activity', 'The description of the simple activity.',
        'moussjo');