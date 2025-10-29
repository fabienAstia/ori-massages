DELETE FROM t_prestations; DELETE FROM t_treatment_types;
DELETE FROM t_durations; delete from t_working_hours;
delete from t_locations;

INSERT INTO t_durations(duration_value, label, break_time) VALUES
(45, '45 min', 5),
(60, '1h', 10),
(90, '1h30', 20);

INSERT INTO t_treatment_types (name, description) VALUES 
('Massages', 'Massages relaxants et énergétiques'),
('Soins visage', 'Soins adaptés à chaque type de peau');

INSERT INTO t_prestations (name, description, price, is_active, image_path, duration_id, type_id) VALUES 
('Massage relaxant ciblé',
'Massage localisé (dos, nuque, épaules) pour relâcher les tensions et apaiser le stress accumulé. Idéal pour une pause bien-être.',
50, 
TRUE,
'massage_45.webp',
(SELECT id FROM t_durations td WHERE td.label = '45 min'),
(SELECT id FROM t_treatment_types tst WHERE tst.name = 'Massages')),

('Massage bien-être complet',
'Massage global du corps, associant pressions et effleurages doux pour détendre, rééquilibrer l’énergie et favoriser la relaxation profonde.',
70, 
TRUE,
'massage_60.jpg',
(SELECT id FROM t_durations td WHERE td.label = '1h'),
(SELECT id FROM t_treatment_types tst WHERE tst.name = 'Massages')), 

('Massage harmonisant profond', 
'Massage long et enveloppant, combinant techniques relaxantes et appuyées pour libérer les tensions et reconnecter corps et esprit.',
90, 
TRUE, 
'massage_90.jpg',
(SELECT id FROM t_durations td WHERE td.label = '1h30'),
(SELECT id FROM t_treatment_types tst WHERE tst.name = 'Massages')), 

('Soin éclat express', 
'Nettoyage en douceur, exfoliation et masque adapté pour réveiller l’éclat du teint. Idéal pour redonner fraîcheur et vitalité à la peau en peu de temps.',
45, 
TRUE, 
'soin_visage_45.jpg',
(SELECT id FROM t_durations td WHERE td.label = '45 min'),
(SELECT id FROM t_treatment_types tst WHERE tst.name = 'Soins visage')), 

('Soin hydratant essentiel', 
'Soin complet incluant nettoyage, gommage, modelage du visage et masque nourrissant pour une peau douce, souple et éclatante.',
60, 
TRUE, 
'soin_visage_60.jpeg',
(SELECT id FROM t_durations td WHERE td.label = '1h'),
(SELECT id FROM t_treatment_types tst WHERE tst.name = 'Soins visage')), 

('Soin anti-âge régénérant', 
'Rituel complet associant techniques manuelles et produits revitalisants pour lisser les traits, redonner fermeté et luminosité au visage.',
80, 
TRUE, 
'soin_visage_90.webp',
(SELECT id FROM t_durations td WHERE td.label = '1h30'),
(SELECT id FROM t_treatment_types tst WHERE tst.name = 'Soins visage')); 

insert into t_working_hours(start_time, end_time, name) values 
('09:30', '12:30', 'matin'),
('14:00', '17:30', 'après-midi');

insert into t_locations(name, address, image_path) values
('A domicile', '', 'lieu1.jpg'),
('Espace soin', '210 rue de Belleville, 75020 Paris', 'lieu2.jpg');

--insert into t_dates(date_value, is_available, comment) values
--();
--INSERT INTO t_statuses (status_name) VALUES
--('enregistré'),
--('complété'),
--('annulé');