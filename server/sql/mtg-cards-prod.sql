drop database if exists mtg_cards;
create database mtg_cards;
use mtg_cards;

-- Create the tables and their relationships
-- First tables with no dependencies

create table `user` (
    user_id int primary key auto_increment,
	username varchar(50) not null,
    email varchar(100) not null,
    password_hash_char varchar(2048) not null,
    restricted tinyint(1),
    `role` varchar(20) not null default 'USER'
);

create table card_type (
	card_type_id int primary key auto_increment,
    card_type varchar(20) not null
);

create table rarity (
	rarity_id int primary key auto_increment,
    rarity varchar(20) not null
);

-- Tables with dependencies

create table collection (
	collection_id int primary key auto_increment,
    user_id int not null,
    collection_name varchar(200),

    constraint fk_collection_user_id
		foreign key (user_id)
        references `user`(user_id)
);

create table card (
	card_id varchar(36) primary key,
    card_type_id int not null,
    rarity_id int not null,
    card_name varchar(200) not null,
    mana_cost varchar(100) not null,
    color_identity varchar(100) not null,
    card_text text,
    `set` varchar(100) not null,
    image_uri varchar(200) not null,

    constraint fk_card_card_type_id
		foreign key (card_type_id)
        references card_type(card_type_id),
	constraint fk_card_rarity_id
		foreign key (rarity_id)
        references rarity(rarity_id)
);

create table collected_card (
	collected_card_id int primary key auto_increment,
    card_id varchar(36) not null,
    collection_id int not null,
    quantity int not null,
    `condition` varchar(20),
    in_use bit not null,

    constraint fk_collected_card_card_id
		foreign key (card_id)
        references card(card_id),
	constraint fk_collected_card_collection_id
		foreign key (collection_id)
        references collection (collection_id)
);

-- Static insertions that don't need to change between runs

insert into card_type (card_type_id, card_type)
values
(1, 'Artifact'),
(2, 'Creature'),
(3, 'Enchantment'),
(4, 'Land'),
(5, 'Instant'),
(6, 'Planeswalker'),
(7, 'Sorcery'),
(8, 'Battle'),
(9, 'Unknown');


insert into rarity (rarity_id, rarity)
values
(1, 'Common'),
(2, 'Uncommon'),
(3, 'Rare'),
(4, 'Mythic'),
(5, 'Special'),
(6, 'Bonus');

set sql_safe_updates = 0;
update `user` set role = 'ADMIN' where username = 'admin';
set sql_safe_updates = 1;