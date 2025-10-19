DELETE FROM t_services; DELETE FROM t_service_types;
DELETE FROM t_durations; DELETE FROM t_statuses;

INSERT INTO t_durations(duration_value, duration_label) VALUES
(45, '45 min'),
(60, '1h'),
(90, '1h30');

INSERT INTO t_service_types (type_name, type_description) VALUES 
('Massages', 'Massages relaxants et énergétiques'),
('Soins visage', 'Soins adaptés à chaque type de peau');

INSERT INTO t_services (service_name, service_description, service_price, is_active, duration_id, type_id) VALUES 
('Massage relaxant ciblé',
'Massage localisé (dos, nuque, épaules) pour relâcher les tensions et apaiser le stress accumulé. Idéal pour une pause bien-être.',
50, TRUE,
(SELECT id FROM t_durations td WHERE td.duration_label = '45 min'),
(SELECT id FROM t_service_types tst WHERE tst.type_name = 'Massages')),

('Massage bien-être complet',
'Massage global du corps, associant pressions et effleurages doux pour détendre, rééquilibrer l’énergie et favoriser la relaxation profonde.',
70, TRUE,
(SELECT id FROM t_durations td WHERE td.duration_label = '1h'),
(SELECT id FROM t_service_types tst WHERE tst.type_name = 'Massages')), 

('Massage harmonisant profond', 
'Massage long et enveloppant, combinant techniques relaxantes et appuyées pour libérer les tensions et reconnecter corps et esprit.',
90, TRUE, 
(SELECT id FROM t_durations td WHERE td.duration_label = '1h30'),
(SELECT id FROM t_service_types tst WHERE tst.type_name = 'Massages')), 

('Soin éclat express', 
'Nettoyage en douceur, exfoliation et masque adapté pour réveiller l’éclat du teint. Idéal pour redonner fraîcheur et vitalité à la peau en peu de temps.',
45, TRUE, 
(SELECT id FROM t_durations td WHERE td.duration_label = '45 min'),
(SELECT id FROM t_service_types tst WHERE tst.type_name = 'Soins visage')), 

('Soin hydratant essentiel', 
'Soin complet incluant nettoyage, gommage, modelage du visage et masque nourrissant pour une peau douce, souple et éclatante.',
60, TRUE, 
(SELECT id FROM t_durations td WHERE td.duration_label = '1h'),
(SELECT id FROM t_service_types tst WHERE tst.type_name = 'Soins visage')), 

('Soin anti-âge régénérant', 
'Rituel complet associant techniques manuelles et produits revitalisants pour lisser les traits, redonner fermeté et luminosité au visage.',
80, TRUE, 
(SELECT id FROM t_durations td WHERE td.duration_label = '1h30'),
(SELECT id FROM t_service_types tst WHERE tst.type_name = 'Soins visage')); 

INSERT INTO t_statuses (status_name) VALUES
('enregistré'),
('complété'),
('annulé');