-- CREATE EXTENSION --
CREATE EXTENSION pgcrypto;

-- CREATE SCHEMA --
CREATE SCHEMA pqkapp;

-- SET DEFAULT SCHEMA --
SET search_path TO pqkapp;

-- SET TIME ZONE
SET TIME ZONE 'Europe/Paris';

-- CREATE TABLES --

-- CREATE customer TABLE --
CREATE TABLE partner (
    alias VARCHAR(50) PRIMARY KEY,
    type VARCHAR(30) NOT NULL,
    direction CHAR(1) NOT NULL,
    flow_type CHAR(1) NOT NULL,
    description TEXT NOT NULL,
    application jsonb
);

-- GRANT USER --
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA pqkapp TO pqkadm;
GRANT SELECT, UPDATE, USAGE ON ALL SEQUENCES IN SCHEMA pqkapp to pqkadm;

-- CREATE DEFAULT SEQUENCE --
CREATE SEQUENCE IF NOT EXISTS pqkapp.hibernate_sequence;