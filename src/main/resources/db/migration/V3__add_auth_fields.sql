-- Add email and password columns to app_user table
ALTER TABLE app_user
ADD COLUMN email VARCHAR(255) UNIQUE,
ADD COLUMN password VARCHAR(255);

-- Provide dummy credentials for existing seeded users
-- Password is 'password' hashed with BCrypt
UPDATE app_user SET email = 'teacher@edu.com', password = '$2a$10$X8Q18f.3Z./k3P.t.bFhC.r0/8C5V4Z134zV8G4642C0P78Z3G3cO' WHERE id = 1;
UPDATE app_user SET email = 'parent@edu.com', password = '$2a$10$X8Q18f.3Z./k3P.t.bFhC.r0/8C5V4Z134zV8G4642C0P78Z3G3cO' WHERE id = 2;

-- Now make them NOT NULL (optional, but good practice since new users require it)
ALTER TABLE app_user
ALTER COLUMN email SET NOT NULL,
ALTER COLUMN password SET NOT NULL;
