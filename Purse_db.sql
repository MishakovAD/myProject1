create table User(
	id_user int(2) AUTO_INCREMENT,
	username varchar(20) NOT NULL,
	PRIMERY KEY (id_user)
	);

create table IncomeTypeTable(
	id_income_type int(2) AUTO_INCREMENT,
	income_type varchar(20),
	PRIMERY KEY(id_income_type)
	);


create table IncomeTable(
	id_income int(200) AUTO_INCREMENT,
	user_id int,
	income_type int,
	money_add money,
	date_add date,
	PRIMERY KEY (id_purse),
	FOREIGN KEY (user_id) REFERENCES User(id_user)
	FOREIGN KEY (income_type) REFERENCES IncomeTypeTable(id_income_type)
	);



create table ConsumptionTypeTable(
	id_consumption_type int(2) AUTO_INCREMENT,
	consumption_type varchar(20),
	PRIMERY KEY(id_consumption_type)
	);


create table ConsumptionTable(
	id_consumption int(10) AUTO_INCREMENT,
	consumption_type int,
	money_add money,
	date_add date,
	user_id int,
	PRIMERY KEY(id_consumption)
	FOREIGN KEY (user_id) REFERENCES User(id_user)
	FOREIGN KEY (income_type) REFERENCES IncomeTypeTable(id_income_type)
	);


INSERT INTO IncomeTypeTable(income_type) VALUES('salary', 'tutoring', 'schoolarship', 'other');
INSERT INTO ConsumptionTypeTable(consumption_type) VALUES('products shops', 'pharmacy', 'cosmetics shops','clothes/shoes shops', 'electronics shops', 'petrol', 'details for car', 'service car', 'mobile','entertainment', 'fast_food', 'transport', 'other');



