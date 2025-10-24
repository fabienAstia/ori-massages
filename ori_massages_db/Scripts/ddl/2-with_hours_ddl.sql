DROP TABLE IF EXISTS t_appointments,  t_slots, t_prestations, t_messages;            
DROP TABLE IF EXISTS t_users, t_locations, t_working_hours, t_dates, t_durations, t_treatment_types;

CREATE TABLE t_users(
	id int GENERATED ALWAYS AS IDENTITY,
	email varchar(150) NOT NULL,
	phone_number varchar(15) NOT NULL,
	lastname varchar(50),
	firstname varchar(20),
	CONSTRAINT t_users_pk PRIMARY KEY (id),
	CONSTRAINT t_users_email_phone_number_uk UNIQUE (email, phone_number)
);

CREATE TABLE t_messages(
	id int GENERATED ALWAYS AS IDENTITY, 
	user_id int NOT NULL,
	date timestamp NOT NULL,
	content varchar(300) NOT NULL,
	CONSTRAINT t_messages_pk PRIMARY KEY (id),
	CONSTRAINT t_messages_user_id_fk FOREIGN KEY (user_id)
		REFERENCES t_users(id)
		ON DELETE CASCADE,
	CONSTRAINT t_messages_user_id_date_uk UNIQUE (user_id, date)
);
CREATE INDEX idx_messages_user_id ON t_messages(user_id);

CREATE TABLE t_treatment_types(
	id int GENERATED ALWAYS AS IDENTITY,
	name varchar(50) NOT NULL,
	description varchar(300) NOT NULL,
	CONSTRAINT t_treatment_types_pk PRIMARY KEY (id),
	CONSTRAINT t_treatment_types_name_uk UNIQUE (name),
	CONSTRAINT t_treatment_types_description_uk UNIQUE (description)
);

CREATE TABLE t_durations(
	id int GENERATED ALWAYS AS IDENTITY,
	duration_value int NOT NULL,
	label varchar(20) NOT NULL,
	break_time int,
	CONSTRAINT t_durations_pk PRIMARY KEY (id),
	CONSTRAINT t_durations_duration_value_uk UNIQUE (duration_value)
);

CREATE TABLE t_prestations(
	id int GENERATED ALWAYS AS IDENTITY,
	name varchar(50) NOT NULL,
	description varchar(300) NOT NULL,
	price numeric(6,2) NOT NULL,
	is_active boolean DEFAULT TRUE,
	image_path varchar(20) not null ,
	duration_id int NOT NULL,
	type_id int NOT NULL,
	CONSTRAINT t_prestations_pk PRIMARY KEY (id),
	CONSTRAINT t_prestations_duration_id_fk FOREIGN key(duration_id)
		REFERENCES t_durations(id),
	CONSTRAINT t_prestations_type_id_fk FOREIGN key(type_id)
		REFERENCES t_treatment_types(id),
	CONSTRAINT t_prestations_duration_id_type_id_uk UNIQUE (duration_id, type_id),
	CONSTRAINT t_prestations_description_uk UNIQUE (description)
);
CREATE INDEX idx_prestations_type_id ON t_prestations(type_id);

CREATE TABLE t_working_hours(
	id int GENERATED ALWAYS AS IDENTITY,
	start_time time NOT NULL,
	end_time time not null check (end_time > start_time),
	name varchar(30),
	CONSTRAINT t_working_hours_pk PRIMARY KEY (id),
	CONSTRAINT t_working_hours_start_time_end_time_uk UNIQUE (start_time, end_time)
);

create table t_dates(
	id int generated always as identity,
	date_value date not null ,
	is_available boolean not null,
	comment varchar(30),
	constraint t_dates_pk primary key (id),
	constraint t_dates_date_uk unique (date_value)
);

create table t_slots(
	id int GENERATED ALWAYS AS identity,
	begin_hour time not null,
	status varchar (20) not null,
	date_id int not null,
	working_hours_id int not null,
	prestation_id int not null,
	constraint t_slots_pk primary key (id),  
	constraint t_slots_date_id_fk foreign key (date_id)  
		references t_dates (id), 
	constraint t_slots_working_hours_id_fk foreign key (working_hours_id)  
		references t_working_hours (id), 
	constraint t_slots_prestation_id_fk foreign key (prestation_id)  
		references t_prestations (id), 
	constraint t_slots_begin_hour_date_working_hours_prestation_uk  
		unique(begin_hour, date_id, working_hours_id, prestation_id)                       		
);
CREATE INDEX idx_slots_date_id ON t_slots(date_id);
CREATE INDEX idx_slots_status ON t_slots(status);


CREATE TABLE t_locations(
	id int GENERATED ALWAYS AS IDENTITY, 
	name varchar(50) NOT NULL,
	address varchar(255),
	image_path varchar(20) not null, 
	CONSTRAINT t_locations_pk PRIMARY KEY (id), 
	CONSTRAINT t_locations_location_name_uk UNIQUE (name, address)
);

CREATE TABLE t_appointments(
	id int GENERATED ALWAYS AS IDENTITY,
	created_at timestamp NOT NULL DEFAULT now(),
	comment varchar(300), 
	slot_id int not null,
	user_id int not null,
	location_id int not null,
	CONSTRAINT t_appointments_pk PRIMARY KEY (id),
	CONSTRAINT t_appointments_slot_id_fk FOREIGN KEY (slot_id)
		REFERENCES t_slots(id),
	CONSTRAINT t_appointments_user_id_fk FOREIGN KEY (user_id)
		REFERENCES t_users(id),
	CONSTRAINT t_appointments_location_id_fk FOREIGN KEY (location_id)
		REFERENCES t_locations(id),
	CONSTRAINT t_appointments_slot_user_location_created_uk 
		unique (slot_id, user_id, location_id, created_at)
);
CREATE INDEX idx_appointments_user_id ON t_appointments(user_id);
CREATE INDEX idx_appointments_slot_id ON t_appointments(slot_id);
CREATE INDEX idx_appointments_location_id ON t_appointments(location_id);