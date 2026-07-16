CREATE EXTENSION IF NOT EXISTS btree_gist;

CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    timezone VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE course (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE offering (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES course(id),
    teacher_id BIGINT NOT NULL REFERENCES app_user(id),
    title VARCHAR(255) NOT NULL,
    max_capacity INT NOT NULL,
    current_capacity INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_offering_course ON offering(course_id);

CREATE TABLE session (
    id BIGSERIAL PRIMARY KEY,
    offering_id BIGINT NOT NULL REFERENCES offering(id),
    start_time TIMESTAMPTZ NOT NULL,
    end_time TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_session_offering ON session(offering_id);

CREATE TABLE booking (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT NOT NULL REFERENCES app_user(id),
    offering_id BIGINT NOT NULL REFERENCES offering(id),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    CONSTRAINT uq_booking_parent_offering UNIQUE (parent_id, offering_id)
);

CREATE TABLE parent_schedule (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT NOT NULL REFERENCES app_user(id),
    session_id BIGINT NOT NULL REFERENCES session(id),
    schedule_range TSTZRANGE NOT NULL,
    CONSTRAINT no_overlap EXCLUDE USING gist (
        parent_id WITH =,
        schedule_range WITH &&
    )
);

-- Insert dummy users for testing
INSERT INTO app_user (role, timezone) VALUES ('TEACHER', 'America/New_York'); -- ID 1
INSERT INTO app_user (role, timezone) VALUES ('PARENT', 'Asia/Kolkata'); -- ID 2

INSERT INTO course (title, description) VALUES ('Python Coding', 'Learn Python from scratch'); -- ID 1