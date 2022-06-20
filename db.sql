CREATE DATABASE bcaadam WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';

CREATE TABLE login (
    sessionid character varying NOT NULL,
    user_id character varying NOT NULL,
    created_date timestamp without time zone DEFAULT now() NOT NULL,
    is_valid boolean DEFAULT false NOT NULL,
    updated_date timestamp without time zone
);


CREATE TABLE users (
    id uuid NOT NULL,
    username character varying NOT NULL,
    name character varying NOT NULL,
    hash_password character varying NOT NULL
);

CREATE TABLE todo (
    id uuid NOT NULL,
    title character varying NOT NULL,
    created_by character varying NOT NULL,
    created_date timestamp without time zone DEFAULT now() NOT NULL,
    modified_by character varying,
    modified_date timestamp without time zone,
    is_done boolean DEFAULT false NOT NULL
);

ALTER TABLE public.todo ADD CONSTRAINT todo_pk PRIMARY KEY (id);
