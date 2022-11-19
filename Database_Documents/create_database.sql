/* CREATE DATABASE 
 * - This is an implementation of a conditional create
 * - Postgres does not support CREATE DATABASE IF NOT EXISTS
 */
 
DO
$do$
BEGIN
   IF EXISTS (SELECT FROM pg_database WHERE datname = 'nursing_app') THEN
      RAISE NOTICE 'Database already exists';  -- optional
   ELSE
      PERFORM dblink_exec('dbname=' || current_database()  -- current db
                        , 'CREATE DATABASE nursing_app WITH OWNER = postgresl ENCODING = "UTF8"');
   END IF;
END
$do$;


/* Drop existing tables
 * - We want to generate a new set of tables with this script
 * - This script is either to generate or update tables
 * - We can cleanly drop everything then
 */ 
DROP TABLE IF EXISTS events_speakers_table CASCADE;
DROP TABLE IF EXISTS user_events_table CASCADE;
DROP TABLE IF EXISTS speakers CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS conferences CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS app_info;
DROP TABLE IF EXISTS audit_history;

/* Create users table */
CREATE TABLE users (
	user_id serial PRIMARY KEY,
	email VARCHAR( 255) UNIQUE NOT NULL,
	password VARCHAR( 50) NOT NULL,
	user_type INT DEFAULT 1
	created_date TIMESTAMP NOT NULL
	updated_date TIMESTAMP
);

/* Create conferences table */
CREATE TABLE conferences (
	conference_id serial PRIMARY KEY,
	name VARCHAR( 50) NOT NULL,
	description VARCHAR( 255) NOT NULL,
	start_date TIMESTAMP NOT NULL,
	end_date TIMESTAMP NOT NULL
	view_status boolean DEFAULT 0,
	created_date TIMESTAMP NOT NULL,
	updated_date TIMESTAMP,
	FOREIGN KEY (updated_user)
		REFERENCES users (user_id)
);

/* Create events table */
CREATE TABLE events (
	event_id serial PRIMARY KEY,
	FOREIGN KEY (conference_id)
		REFERENCES conferences (conference_id),
	name VARCHAR( 50) NOT NULL,
	description VARCHAR( 255) NOT NULL,
	start_date TIMESTAMP NOT NULL,
	end_date TIMESTAMP NOT NULL,
	view_status boolean DEFAULT 0,
	created_date TIMESTAMP NOT NULL,
	updated_date TIMESTAMP,
	FOREIGN KEY (updated_user)
		REFERENCES users (user_id)
);

/* Create speakers table */
CREATE TABLE speakers (
	speaker_id serial PRIMARY KEY,
	name VARCHAR( 50) NOT NULL,
	bio VARCHAR( 255) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	FOREIGN KEY (updated_user)
		REFERENCES users (user_id)
);

/* Create many to many for speakers/events */
CREATE TABLE events_speakers_table (
	event_id INT REFERENCES events (event_id) ON UPDATE CASCADE ON DELETE CASCADE,
	speaker_id INT REFERENCES speakers (speaker_id) ON UPDATE CASCADE ON DELETE CASCADE,
	speaker_type INT DEFAULT 1,
	CONSTRAINT events_speakers_pkey PRIMARY KEY (event_id, speaker_id)
);

/* Create RSVP table */
CREATE TABLE users_events_table (
	user_id INT REFERENCES users (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	event_id INT REFERENCES events (event_id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT users_events_pkey PRIMARY KEY (user_id, event_id)
);

/* Create table to track app statistics */
CREATE TABLE app_info (
	name VARCHAR( 50) PRIMARY KEY,
	description VARCHAR( 255) NOT NULL,
	updated_date TIMESTAMP,
	FOREIGN KEY (updated_user)
		REFERENCES users (user_id)
);

/* Not sure if we need this yet, or ever
 * - This would just be to manually log events
 * - The purpose is to see who did what on updates
 * - It would help admins track total staff activity
 */
CREATE TABLE audit_history (
	instance_id serial PRIMARY KEY,
	description VARCHAR( 50) NOT NULL,
	created_date TIMESTAMP NOT NULL,
	FOREIGN KEY (instance_user)
		REFERENCES users (user_id)
);