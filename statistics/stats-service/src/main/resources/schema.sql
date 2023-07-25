CREATE TABLE IF NOT EXISTS ENDPOINTHITS
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    app VARCHAR(100)                            NOT NULL,
    uri VARCHAR                                 NOT NULL,
    ip  VARCHAR(20)                             NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_endpointhits PRIMARY KEY (id)
);