create table if not exists roles
(
    id integer generated by default as identity
    constraint roles_pkey
    primary key
);

alter table roles owner to postgres;

create table if not exists users
(
    id bigint generated by default as identity
    constraint users_pkey
    primary key,
    active boolean not null,
    created_at timestamp not null,
    email varchar(255) not null
    constraint uk_6dotkott2kjsp8vw4d0m25fb7
    unique,
    login varchar(255) not null
    constraint uk_ow0gan20590jrb00upg3va2fn
    unique,
    password varchar(255) not null,
    score bigint
    );

alter table users owner to postgres;

create table if not exists courses
(
    id bigint generated by default as identity
    constraint courses_pkey
    primary key,
    name varchar(255) not null
    constraint uk_5o6x4fpafbywj4v2g0owhh11r
    unique,
    owner_id bigint
    constraint fk3jkpttw97v8c2yuqx4prsm4m9
    references users
    );

alter table courses owner to postgres;

create table if not exists course_user
(
    course_id bigint not null
    constraint fk8lwf41pgqkmlkfvklvf22pmcb
    references courses,
    user_id bigint not null
    constraint fkf2f9pdami9tgornv4vld7pfea
    references users
);

alter table course_user owner to postgres;

create table if not exists tasks
(
    id bigint generated by default as identity
    constraint tasks_pkey
    primary key,
    answer varchar(255) not null,
    question varchar(255) not null,
    course_id bigint
    constraint fkopldg47bgaarlampi2f6wees3
    references courses
    );

alter table tasks owner to postgres;

create table if not exists courses_tasks
(
    course_id bigint not null
    constraint fk9p2m2enynuop4w2n65w8pqnok
    references courses,
    tasks_id bigint not null
    constraint uk_ka6u4gg7woxgwi0omvlmbmdv
    unique
    constraint fki0rlh6uj7v1r3oosqwulj7nkj
    references tasks
);

alter table courses_tasks owner to postgres;

create table if not exists user_roles
(
    user_id bigint not null
    constraint fkhfh9dx7w3ubf1co1vdev94g3f
    references users,
    role_id integer not null
    constraint fkh8ciramu9cc9q3qcqiv4ue8a6
    references roles,
    constraint user_roles_pkey
    primary key (user_id, role_id)
    );

alter table user_roles owner to postgres;

create table if not exists spring_session
(
    primary_id char(36) not null
    constraint spring_session_pk
    primary key,
    session_id char(36) not null,
    creation_time bigint not null,
    last_access_time bigint not null,
    max_inactive_interval integer not null,
    expiry_time bigint not null,
    principal_name varchar(100)
    );

alter table spring_session owner to postgres;

create unique index if not exists spring_session_ix1
	on spring_session (session_id);

create index if not exists spring_session_ix2
	on spring_session (expiry_time);

create index if not exists spring_session_ix3
	on spring_session (principal_name);

create table if not exists spring_session_attributes
(
    session_primary_id char(36) not null
    constraint spring_session_attributes_fk
    references spring_session
    on delete cascade,
    attribute_name varchar(200) not null,
    attribute_bytes bytea not null,
    constraint spring_session_attributes_pk
    primary key (session_primary_id, attribute_name)
    );

alter table spring_session_attributes owner to postgres;

insert into roles values (0);
insert into roles values (1);

insert into user_roles values (1, 0);