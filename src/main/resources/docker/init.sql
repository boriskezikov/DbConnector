-- schema owner
CREATE USER dbconnectionsstorage WITH password 'dbconnectionsstorage';

CREATE DATABASE "db_connections_storage" WITH OWNER = postgres ENCODING = 'UTF8';

\connect "db_connections_storage";

-- schema user
CREATE USER dbconnectionsstorage_user WITH password 'dbconnectionsstorage_user';
CREATE USER dbconnectionsstorage_read WITH password 'dbconnectionsstorage_read';

-- create schema
CREATE SCHEMA dbconnectionsstorage AUTHORIZATION dbconnectionsstorage;

-- add-privileges
GRANT USAGE ON SCHEMA dbconnectionsstorage TO dbconnectionsstorage_user;
GRANT USAGE ON SCHEMA dbconnectionsstorage TO dbconnectionsstorage_read;

ALTER DEFAULT PRIVILEGES FOR USER dbconnectionsstorage IN SCHEMA dbconnectionsstorage GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO dbconnectionsstorage_user;
ALTER DEFAULT PRIVILEGES FOR USER dbconnectionsstorage IN SCHEMA dbconnectionsstorage GRANT USAGE ON SEQUENCES TO dbconnectionsstorage_user;
ALTER DEFAULT PRIVILEGES FOR USER dbconnectionsstorage IN SCHEMA dbconnectionsstorage GRANT EXECUTE ON FUNCTIONS TO dbconnectionsstorage_user;
ALTER DEFAULT PRIVILEGES FOR USER dbconnectionsstorage IN SCHEMA dbconnectionsstorage GRANT SELECT ON TABLES TO dbconnectionsstorage_read;


CREATE TABLE dbconnectionsstorage.spring_session
(
    PRIMARY_ID            CHAR(36) NOT NULL,
    SESSION_ID            CHAR(36) NOT NULL,
    CREATION_TIME         BIGINT   NOT NULL,
    LAST_ACCESS_TIME      BIGINT   NOT NULL,
    MAX_INACTIVE_INTERVAL INT      NOT NULL,
    EXPIRY_TIME           BIGINT   NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT spring_session_pk PRIMARY KEY (PRIMARY_ID)
);
CREATE TABLE dbconnectionsstorage.spring_session_attributes
(
    session_primary_id CHAR(36)     not null,
    attribute_name     VARCHAR(200) NOT NULL,
    attribute_bytes    bytea        not null,
    constraint spring_session_attributes_pk primary key (session_primary_id, attribute_name),
    constraint spring_session_attributes_fk foreign key (session_primary_id) references dbconnectionsstorage.spring_session (PRIMARY_ID)
);

GRANT ALL PRIVILEGES ON TABLE dbconnectionsstorage.spring_session TO dbconnectionsstorage_user;
GRANT ALL PRIVILEGES ON TABLE dbconnectionsstorage.spring_session TO dbconnectionsstorage;
GRANT ALL PRIVILEGES ON TABLE dbconnectionsstorage.spring_session_attributes TO dbconnectionsstorage_user;
GRANT ALL PRIVILEGES ON TABLE dbconnectionsstorage.spring_session_attributes TO dbconnectionsstorage;
