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
CREATE TABLE partners (
    alias VARCHAR(50) PRIMARY KEY,
    type VARCHAR(30) NOT NULL,
    direction CHAR(1) NOT NULL,
    flow_type CHAR(1) NOT NULL,
    description TEXT NOT NULL,
    application jsonb
);

CREATE TABLE messages (
    id BIGSERIAL UNIQUE NOT NULL,
    partner_alias VARCHAR(50) NOT NULL REFERENCES partners(alias) ON DELETE SET NULL,
    payload JSONB NOT NULL,
    received_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    direction CHAR(1) NOT NULL,
    metadata JSONB NOT NULL,
    status VARCHAR(20) NOT NULL
);


-- GRANT USER --
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA pqkapp TO pqkadm;
GRANT SELECT, UPDATE, USAGE ON ALL SEQUENCES IN SCHEMA pqkapp to pqkadm;

-- CREATE DEFAULT SEQUENCE --
CREATE SEQUENCE IF NOT EXISTS pqkapp.hibernate_sequence;

CREATE INDEX idx_partner_alias ON partners(alias);
CREATE INDEX idx_partner_direction ON partners(direction);
CREATE INDEX idx_partners_flow_type ON partners(flow_type);

CREATE INDEX idx_message_partner_alias ON messages(partner_alias);
CREATE INDEX idx_message_received_at ON messages(received_at DESC);