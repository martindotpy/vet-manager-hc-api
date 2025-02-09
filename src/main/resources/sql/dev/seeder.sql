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