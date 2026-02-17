-- sec.app_user definition

-- Drop table

-- DROP TABLE sec.app_user;

CREATE SEQUENCE IF NOT EXISTS sec.user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS sec.refresh_token_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE sec.app_user (
	enabled bool NOT NULL,
	id int8 NOT NULL,
	external_id uuid NOT NULL,
	email varchar(255) NOT NULL,
	full_name varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	"role" varchar(255) NOT NULL,
	CONSTRAINT app_user_email_key UNIQUE (email),
	CONSTRAINT app_user_external_id_key UNIQUE (external_id),
	CONSTRAINT app_user_pkey PRIMARY KEY (id),
	CONSTRAINT app_user_role_check CHECK (((role)::text = ANY ((ARRAY['ROLE_ADMIN'::character varying, 'ROLE_TRAINER'::character varying, 'ROLE_CLIENT'::character varying])::text[])))
);
CREATE INDEX idx2jumdao8oj7gaeii5ae3hn6mp ON sec.app_user USING btree (enabled);

-- sec.refresh_token definition

-- Drop table

-- DROP TABLE sec.refresh_token;

CREATE TABLE sec.refresh_token (
	is_revoked bool NOT NULL,
	attempts int8 NOT NULL,
	expiration_date timestamp(6) NOT NULL,
	generation_date timestamp(6) NOT NULL,
	id int8 NOT NULL,
	user_id int8 NOT NULL,
	jti uuid NOT NULL,
	"token" text NOT NULL,
	CONSTRAINT refresh_token_jti_key UNIQUE (jti),
	CONSTRAINT refresh_token_pkey PRIMARY KEY (id)
);

-- sec.refresh_token foreign keys

ALTER TABLE sec.refresh_token ADD CONSTRAINT fk5wkt2p042y3lwltk29cvpxuh FOREIGN KEY (user_id) REFERENCES sec.app_user(id);

