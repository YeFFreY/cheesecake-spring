DELETE
FROM resources
where uuid in ('223e4567-e89b-12d3-a456-556642440000');
INSERT INTO resources (uuid, name, description, quantity_unit, created_by)
VALUES ('123e4567-e89b-12d3-a456-556642440000', 'A simple activity',
        'The description of the simple activity.', 'Item', 'moussjo');