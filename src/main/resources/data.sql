INSERT INTO DOCTOR (ID, FIRSTNAME, LASTNAME, SECONDNAME, SPECIALIZATION)
VALUES (1, 'Григорий', 'Распутин', 'Сергеевич', 'Имунолог'),
 (2, 'Иванов', 'Иван', 'Иванович', 'Терапевт'),
 (3,'Олег','Мясников','Сергеевич','Хирург');

INSERT INTO PATIENT (ID, FIRSTNAME, LASTNAME, SECONDNAME, PHONE) VALUES
(1, 'Анна', 'Михалков', 'Витальевна', '89374418922'),
(2, 'Олег', 'Тинтров', 'Сергеевич', '88225352595'),
(3,'Василий','Власов','Григорьевич','87249354123');

INSERT INTO RECIPE(ID,DESCRIPTION,PATIENT,DOCTOR,CREATEDATA,SHELFLIFE,PRIORITY) VALUES
(1, 'Глюкоза 10 мг 2 раз в день', 2, 2, '2020-08-15', '2020-10-20', 'CITO'),
(2, 'Чистка носа хлором 3 раза в день', 3, 2, '2020-08-15', '2020-08-31', 'STATIM'),
(3, 'Рыбий Жир 1 литр в день', 1, 1, '2020-08-15', '2020-09-17', 'NORMAL'),
(4, 'Удаление третьего глаза', 2, 3, '2020-08-14', '2020-11-20', 'NORMAL'),
(5, 'Настойка сирени', 1, 1, '2020-08-15', '2020-10-20', 'STATIM')