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
