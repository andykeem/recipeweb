create table category (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table ingredient (id bigint not null auto_increment, amount decimal(19,2), description varchar(255), recipe_id bigint, uom_id bigint, primary key (id)) engine=InnoDB
create table note (id bigint not null auto_increment, text longtext, recipe_id bigint, primary key (id)) engine=InnoDB
create table recipe (id bigint not null auto_increment, cook_time integer, description varchar(255), difficulty varchar(24), direction longtext, image longblob, prep_time integer, servings integer, source varchar(255), url varchar(255), primary key (id)) engine=InnoDB
create table recipe_category (recipe_id bigint not null, category_id bigint not null, primary key (recipe_id, category_id)) engine=InnoDB
create table unit_of_measure (id bigint not null auto_increment, value varchar(255), primary key (id)) engine=InnoDB
alter table ingredient add constraint fk_ingredient_recipe foreign key (recipe_id) references recipe (id)
alter table ingredient add constraint fk_ingredient_uom foreign key (uom_id) references unit_of_measure (id)
alter table note add constraint fk_note_recipe foreign key (recipe_id) references recipe (id)
alter table recipe_category add constraint fk_category_recipe foreign key (category_id) references category (id)
alter table recipe_category add constraint fk_recipe_category foreign key (recipe_id) references recipe (id)
