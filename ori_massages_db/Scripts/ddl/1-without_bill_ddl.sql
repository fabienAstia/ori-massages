DROP TABLE IF EXISTS t_appointments, t_services, t_durations, t_appointment_statuses;
DROP TABLE IF EXISTS t_locations, t_service_types, t_messages, t_customers;

CREATE TABLE t_customers(
	id int GENERATED ALWAYS AS IDENTITY,
	username varchar(254) NOT NULL,
	lastname varchar(50),
	firstname varchar(20),
	phone_number varchar(15)NOT NULL,
	CONSTRAINT t_customers_pk PRIMARY KEY (id),
	CONSTRAINT t_customers_username_uk UNIQUE (username),
	CONSTRAINT t_customers_phone_number_uk UNIQUE (phone_number)
);

CREATE TABLE t_messages(
	id int GENERATED ALWAYS AS IDENTITY,
	customer_id int NOT NULL,
	message_date timestamp NOT NULL,
	message_content varchar(300) NOT NULL,
	CONSTRAINT t_messages_pk PRIMARY KEY (id),
	CONSTRAINT t_messages_customer_id_fk FOREIGN KEY (customer_id)
		REFERENCES t_customers(id)
		ON DELETE CASCADE
);

CREATE TABLE t_service_types(
	id int GENERATED ALWAYS AS IDENTITY,
	type_name varchar(50) NOT NULL,
	type_description varchar(300) NOT NULL,
	CONSTRAINT t_service_types_pk PRIMARY KEY (id),
	CONSTRAINT t_service_types_type_name_uk UNIQUE (type_name),
	CONSTRAINT t_service_types_type_description_uk UNIQUE (type_description)
);

CREATE TABLE t_locations(
	id int GENERATED ALWAYS AS IDENTITY,
	location_name varchar(50) NOT NULL,
	location_address varchar(255),
	CONSTRAINT t_locations_pk PRIMARY KEY (id),
	CONSTRAINT t_locations_location_name_uk UNIQUE (location_name)
);

CREATE TABLE t_appointment_statuses(
	id int GENERATED ALWAYS AS IDENTITY,
	status_name varchar(20) NOT NULL,
	CONSTRAINT t_appointment_statuses_pk PRIMARY KEY (id),
	CONSTRAINT t_appointment_statuses_status_name_uk UNIQUE (status_name)
);

CREATE TABLE t_durations(
	id int GENERATED ALWAYS AS IDENTITY,
	duration_value int NOT NULL,
	duration_label varchar(20) NOT NULL,
	CONSTRAINT t_durations_pk PRIMARY KEY (id),
	CONSTRAINT t_durations_duration_value_uk UNIQUE (duration_value),
	CONSTRAINT t_durations_duration_label_uk UNIQUE (duration_label)
);

CREATE TABLE t_services(
	id int GENERATED ALWAYS AS IDENTITY,
	service_name varchar(50) NOT NULL,
	service_description varchar(300) NOT NULL,
	service_price numeric(5,2) NOT NULL,
	is_active boolean DEFAULT TRUE,
	duration_id int NOT NULL,
	type_id int NOT NULL,
	CONSTRAINT t_services_pk PRIMARY KEY (id),
	CONSTRAINT t_services_duration_id_fk FOREIGN key(duration_id)
		REFERENCES t_durations(id),
	CONSTRAINT t_services_type_id_fk FOREIGN key(type_id)
		REFERENCES t_service_types(id),
	CONSTRAINT t_services_service_name_duration_id_type_id_uk UNIQUE (service_name, duration_id, type_id),
	CONSTRAINT t_services_service_description_uk UNIQUE (service_description)
);

CREATE TABLE t_appointments(
	id int GENERATED ALWAYS AS IDENTITY,
	begin_at timestamp NOT NULL,
	end_at timestamp NOT NULL,
	created_at timestamp NOT NULL DEFAULT now(),
	customer_address varchar(255),
	practitioner_comment varchar(300),
	status_id int NOT NULL,
	location_id int NOT NULL,
	customer_id int NOT NULL,
	service_id int NOT NULL,
	CONSTRAINT t_appointments_pk PRIMARY KEY (id),
	CONSTRAINT t_appointments_begin_at_uk UNIQUE (begin_at),
	CONSTRAINT t_appointments_status_id_fk FOREIGN KEY (status_id)
		REFERENCES t_appointment_statuses(id),
	CONSTRAINT t_appointments_location_id_fk FOREIGN KEY (location_id)
		REFERENCES t_locations(id),
	CONSTRAINT t_appointments_customer_id_fk FOREIGN KEY (customer_id)
		REFERENCES t_customers(id)
		ON DELETE CASCADE,
	CONSTRAINT t_appointments_service_id_fk FOREIGN KEY (service_id)
		REFERENCES t_services(id),
	CONSTRAINT t_appointments_check_duration CHECK (end_at > begin_at)
);