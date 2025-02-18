-- USERS -----------------------------------------------------------------------
-- Insert users
INSERT INTO
    `user` (
        id,
        first_name,
        last_name,
        email,
        profile_image_url,
        `password`,
        roles,
        deleted
    )
VALUES
    -- * Admin:
    -- JWT: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImlkIjoxLCJmaXJzdF9uYW1lIjoiQWRtaW4iLCJsYXN0X25hbWUiOiJBZG1pbiIsImVtYWlsIjoiYWRtaW5AYWRtaW4uY29tIiwicm9sZXMiOlsiQURNSU4iXSwicHJvZmlsZV9pbWFnZV91cmwiOm51bGx9LCJzdWIiOiIxIn0.mj6KdYCJbXmZbXts1HDG252mzT-VUX8WienLj0hh2WU
    -- Password: admin
    -- * User:
    -- JWT: eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImlkIjoyLCJmaXJzdF9uYW1lIjoiVXNlciIsImxhc3RfbmFtZSI6IlVzZXIiLCJlbWFpbCI6InVzZXJAdXNlci5jb20iLCJyb2xlcyI6WyJVU0VSIl0sInByb2ZpbGVfaW1hZ2VfdXJsIjpudWxsfSwic3ViIjoiMiJ9.j43TE5dJHU1_DLME_7yj6Ux-wXrf_M7jYliaMBybsbE
    -- Password: user
    (
        2,
        'User',
        'User',
        'user@user.com',
        NULL,
        '$2a$10$rGBA6G7JkDQscH3KQD1no.wUUjm1jupdpiGYH1Ie2ejE2QO9crCX.',
        'USER',
        0
    );

INSERT INTO
    `client` (
        deleted,
        identification,
        first_name,
        last_name,
        address,
        identification_type
    )
VALUES
    (
        0,
        '12345678',
        'Juan',
        'Pérez',
        'Av. Santa Anita',
        'DNI'
    ),
    (
        0,
        '98765432',
        'María',
        'Gómez',
        'Calle Tutancamon',
        'FOREIGNER_CARNET'
    );

INSERT INTO
    `client_emails` (client_id, emails)
VALUES
    (1, 'firstclient@firstclient.com'),
    (2, 'secondclient@secondclient.com');

INSERT INTO
    `client_emails` (client_id, emails)
VALUES
    (1, 'firstclient@firstclient.com'),
    (2, 'secondclient@secondclient.com');

INSERT INTO
    `client_phones` (client_id, phones)
VALUES
    (1, '999999999'),
    (2, '922222222');

INSERT INTO
    `species` (id, `name`)
VALUES
    (1, 'Perro'),
    (2, 'Gato');

INSERT INTO
    `race` (id, species_id, `name`)
VALUES
    (1, 1, 'Poodle'),
    (2, 1, 'Chihuahua'),
    (3, 2, 'Siamés'),
    (4, 2, 'Persa');

INSERT INTO
    `patient` (
        age,
        birth_date,
        deceased,
        deleted,
        race_id,
        id,
        owner_id,
        `name`,
        characteristics,
        gender
    )
VALUES
    (
        4,
        '2025-02-16',
        0,
        0,
        2,
        1,
        1,
        'Miausculus',
        'Es fuerte e inteligente',
        'MALE'
    ),
    (
        4,
        '2025-02-16',
        0,
        0,
        1,
        2,
        1,
        'Firulays',
        'Simplemente Firulays',
        'FEMALE'
    );