create table IF NOT EXISTS Patient (
    ID BIGINT identity primary key,
    FIRSTNAME VARCHAR(45),
    LASTNAME VARCHAR(45),
    SECONDNAME VARCHAR(45) ,
    PHONE VARCHAR(45),
);
create table IF NOT EXISTS  Doctor(
    ID BIGINT identity primary key,
    FIRSTNAME VARCHAR(45),
    LASTNAME VARCHAR(45),
    SECONDNAME VARCHAR(45),
    SPECIALIZATION VARCHAR(45)
);
create table IF NOT EXISTS  Recipe(
    ID BIGINT identity primary key,
    DESCRIPTION VARCHAR(255),
    PATIENT BIGINT,
    DOCTOR BIGINT,
    CREATEDATA date not null,
    VALIDATE VARCHAR(45),
    PRIORITY VARCHAR(45),
        foreign key (PATIENT) references Patient(id),
        foreign key (DOCTOR) references  Doctor(id)
)