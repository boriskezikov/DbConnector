CREATE TABLE spring_session
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
CREATE TABLE spring_session_attributes
(
    session_primary_id CHAR(36)     not null,
    attribute_name     VARCHAR(200) NOT NULL,
    attribute_bytes    bytea        not null,
    constraint spring_session_attributes_pk primary key (session_primary_id, attribute_name),
    constraint spring_session_attributes_fk foreign key (session_primary_id) references dbconnectionsstorage.spring_session (PRIMARY_ID)
);
