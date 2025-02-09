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
    -- Password: admin
    (
        1,
        'Admin',
        'Admin',
        'admin@admin.com',
        NULL,
        '$2a$10$IxmCMA8q0Ly3FKqd5WyXHueWu0DGE7mUyi07kJO7nPr.3svHjplpa',
        'ADMIN',
        0
    );