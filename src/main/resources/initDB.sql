create  table Patient(
    id BIGINT not null primary key,
    firstname VARCHAR(45),
    lastname VARCHAR(45),
    secondname VARCHAR(45) ,
    Phone VARCHAR(45),
);
create table Doctor(
    id BIGINT not null primary key,
    firstname VARCHAR(45),
    lastname VARCHAR(45),
    secondname VARCHAR(45),
    specialization VARCHAR(45)
);
create table Recipe(
    id BIGINT not null primary key,
    description VARCHAR(255),
    patient BIGINT,
    doctor BIGINT,
    createdata date not null,
    validate VARCHAR(45),
    priority VARCHAR(45),
        foreign key (patient) references Patient(id),
        foreign key (doctor) references  Doctor(id)
);