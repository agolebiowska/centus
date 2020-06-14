CREATE DATABASE IF NOT EXISTS db;
use db;

create table if not exists user(
	id int(11) not null auto_increment,
	username varchar(255) not null unique,
	password varchar(255) not null,
	created_at datetime not null default current_timestamp,
	primary key(id)
);

create table if not exists expense(
	id int(11) not null auto_increment,
	user_id int(11) not null,
	description varchar(255),
    value int(11) not null,
	created_at datetime not null default current_timestamp,
	primary key(id),
	foreign key (user_id) references user(id)
);

create table if not exists budget(
	id int(11) not null auto_increment,
	user_id int(11) not null,
    amount int(11) not null,
	created_at datetime not null default current_timestamp,
	primary key(id),
	foreign key (user_id) references user(id)
);