CREATE TABLE IF NOT EXISTS invoice(
	id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	amount Double PRECISION ,
	paid_amount DOUBLE PRECISION  DEFAULT 0,
	due_date Date,
	status Varchar(20)	
);
