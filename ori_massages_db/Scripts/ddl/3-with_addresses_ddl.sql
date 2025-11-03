DROP TABLE IF EXISTS t_messages, t_appointments, t_addresses, t_streets, t_cities,  t_slots, t_prestations;            
DROP TABLE IF EXISTS t_users, t_roles, t_locations, t_working_hours, t_dates, t_durations, t_treatment_types, t_statuses;

CREATE TABLE t_roles(
	id int GENERATED ALWAYS AS IDENTITY,
	role_label varchar(20),
	constraint t_roles_pk primary key (id),
	CONSTRAINT t_roles_label_uk UNIQUE (role_label)
);

CREATE TABLE t_users(
	id int GENERATED ALWAYS AS IDENTITY,
	phone_number varchar(15) NOT NULL,
	email varchar(150) NOT NULL,
	fullname varchar(50),
	role_id int NOT NULL,
	CONSTRAINT t_users_pk PRIMARY KEY (id),
	CONSTRAINT t_users_role_id_fk FOREIGN KEY (role_id) REFERENCES t_roles (id),
	CONSTRAINT t_users_email_phone_number_uk UNIQUE (email, phone_number)
);

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
	begin_at time not null,
	end_at time not NULL CHECK(end_at > begin_at),
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
	constraint t_slots_begin_at_date_id_uk  
		unique(begin_at, date_id)                       		
);
CREATE INDEX idx_slots_date_id ON t_slots(date_id);


CREATE TABLE t_locations(
	id int GENERATED ALWAYS AS IDENTITY, 
	name varchar(50) NOT NULL,
	image_path varchar(20) not null, 
	CONSTRAINT t_locations_pk PRIMARY KEY (id), 
	CONSTRAINT t_locations_location_name_uk UNIQUE (name)
);

CREATE TABLE t_cities(
	id int GENERATED ALWAYS AS IDENTITY,
	zip_code char(5) NOT NULL ,
	city_name varchar(50) NOT NULL ,
	CONSTRAINT t_cities_pk PRIMARY KEY (id),
	CONSTRAINT t_cities_zip_code_city_name_uk UNIQUE (zip_code, city_name)
);

CREATE TABLE t_streets(
	id int GENERATED ALWAYS AS IDENTITY,
	city_id int NOT NULL,
	street_name varchar(255) NOT NULL,
	CONSTRAINT t_streets_pk PRIMARY KEY (id),
	CONSTRAINT t_streets_city_id_fk FOREIGN KEY (city_id)
		REFERENCES t_cities(id),
	CONSTRAINT t_streets_city_id_street_name_uk UNIQUE (city_id, street_name)
);

CREATE TABLE t_addresses(
	id int GENERATED ALWAYS AS IDENTITY,
	street_number varchar(10),
	complement varchar(100),
	street_id int NOT NULL,
	user_id int,
	location_id int NOT NULL,
	CONSTRAINT t_addresses_pk PRIMARY KEY (id),
	CONSTRAINT t_addresses_street_id_fk FOREIGN KEY (street_id)
		REFERENCES t_streets (id),
	CONSTRAINT t_addresses_user_id_fk FOREIGN KEY (user_id)
		REFERENCES t_users(id),
	CONSTRAINT t_addresses_location_id_fk FOREIGN KEY (location_id)
		REFERENCES t_locations (id),
	CONSTRAINT t_addresses_number_street_complement_uk UNIQUE (street_number, complement, street_id)
);

CREATE TABLE t_statuses(
	id int GENERATED ALWAYS AS IDENTITY,
	status_label varchar (20),
	CONSTRAINT t_statuses_id_pk PRIMARY KEY (id),
	CONSTRAINT t_statuses_label_uk UNIQUE (status_label)
);

CREATE TABLE t_appointments(
	id int GENERATED ALWAYS AS IDENTITY,
	created_at timestamp NOT NULL DEFAULT now(),
	slot_id int not null,
	user_id int not null,
	address_id int not null,
	status_id int NOT NULL, 
	CONSTRAINT t_appointments_pk PRIMARY KEY (id),
	CONSTRAINT t_appointments_slot_id_fk FOREIGN KEY (slot_id)
		REFERENCES t_slots(id),
	CONSTRAINT t_appointments_user_id_fk FOREIGN KEY (user_id)
		REFERENCES t_users(id),
	CONSTRAINT t_appointments_address_id_fk FOREIGN KEY (address_id)
		REFERENCES t_addresses(id),
	CONSTRAINT t_appointments_status_id_fk FOREIGN KEY (status_id)
		REFERENCES t_statuses(id),
	CONSTRAINT t_appointments_slot_uk unique (slot_id)
);
CREATE INDEX idx_appointments_user_id ON t_appointments(user_id);
CREATE INDEX idx_appointments_slot_id ON t_appointments(slot_id);
CREATE INDEX idx_appointments_address_id ON t_appointments(address_id);
CREATE INDEX idx_appointments_user_created_at ON t_appointments(user_id, created_at);

CREATE TABLE t_messages(
	id int GENERATED ALWAYS AS IDENTITY, 
	user_id int NOT NULL,
	msg_date timestamp NOT NULL,
	content varchar(300) NOT NULL,
	appointment_id int null,
	CONSTRAINT t_messages_pk PRIMARY KEY (id),
	CONSTRAINT t_messages_user_id_fk FOREIGN KEY (user_id)
		REFERENCES t_users(id)
		ON DELETE CASCADE,
	constraint t_messages_appointment_id_fk foreign key (appointment_id) references t_appointments(id),
	CONSTRAINT t_messages_user_id_date_uk UNIQUE (user_id, msg_date)
);
CREATE INDEX idx_messages_user_id ON t_messages(user_id);
