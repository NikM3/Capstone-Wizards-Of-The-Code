drop database if exists mtg_cards_test;
create database mtg_cards_test;
use mtg_cards_test;

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

-- Set up testing
delimiter //
create procedure set_known_good_state()
begin
	delete from collected_card;
    alter table collected_card auto_increment = 1;
    delete from card;
    alter table card auto_increment = 1;
    delete from collection;
    alter table collection auto_increment = 1;
    delete from `user`;
    alter table `user` auto_increment = 1;
    
    insert into card(card_id, card_type_id, rarity_id, card_name, mana_cost, color_identity, card_text, `set`, image_uri)
    values
    ('1', 5, 1, 'Chandra''s Outrage', '4', 'r', 'text text', 'Modern Masters 2017', 'test uri'),
    ('2', 4, 2, 'Dimir Aqueduct', '0', 'ub', 'text text', 'Zendikar Rising Commander', 'test uri'),
    ('3', 6, 3, 'Hour of Reckoning', '7', 'w', 'text text', 'Tarkir: Dragonstorm Commander', 'test uri'),
    ('4', 2, 4, 'The Ur-Dragon', '9', 'wubrg', 'text text', 'Commander Masters', 'test uri'),
    ('5', 1, 3, 'Black Lotus', '0', 'c', 'text text', 'Limited Edition Alpha', 'test uri'); -- For testing with adding to collected_card

    insert into `user` (`user_id`,`username`,`email`,`password_hash_char`,`restricted`,`role`) values
    (1,'george','george@mail.com','$2a$10$jyIwwSytGOU43X7PPe8BOevjtbOT3V2naTPUbiKsD0kK6Z2x74l/e',0,'USER'),
    (2,'user','user@mail.com','$2a$10$CVNkWJ5z/OBpqQ0NncBIueF7qDKFP3e5E573lEMpIIyO08eaLDz4y',0,'USER'),
    (3,'admin','admin@mail.com','$2a$10$MmuaTPFC39Xmod.Xg2CbfeprpWU6Msd.2sw3IrfCYVqtfc94frioe',0,'ADMIN');

    insert into collection(collection_id, user_id, collection_name)
    values
    (1, 2, 'test collection');
    
    insert into collected_card(collected_card_id, card_id, collection_id, quantity, `condition`, in_use)
    values
    (1, '1', 1, 5, 'Lightly Played', 0),
    (2, '2', 1, 10, 'Moderately Played', 1),
    (3, '3', 1, 2, 'Lightly Played', 0),
    (4, '4', 1, 1, 'Near Mint', 1);
    
end //
delimiter ;