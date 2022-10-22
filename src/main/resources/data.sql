INSERT INTO "users"
(email, full_name, password)
VALUES('rainhard@gmail.com', 'Rainhard vidiansyah', '$2a$12$8c/DUsBnPaNUORsXhgZsFujqMiePq/ZHIaOeUpqg.p5BN4xCGZWO2'),
('maulida@gmail.com', 'Maulida', '$2a$12$.tT/zRROKOeQK1hU/fkNLeljaacAv4H6XhAOecFly7.qwOHx6tjjS'),
('hoki@gmail.com', 'Hoki Kucing', '$2a$12$zNrNm8wMOq1n5m5TilrJO./xMddJoF7y0U6wNPOoEaDpqzVI8jA3W');


INSERT INTO role
(role_name)
VALUES('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO user_roles
(user_id, role_id)
VALUES(1, 1),
(1, 2),
(2, 2),
(3, 2);