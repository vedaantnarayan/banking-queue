
insert into customer( name ,phone_number) values ('Vedant', '1234567'),('Shyam', '6868'),('Ram', '07667546'),('Mohan', '09843');

insert into token_type (name) values ('WITHDRAW'), ('DEPOSIT'), ('CHECK_DEPOSIT'), ('ACCOUNT_CLOSE'),('ACCOUNT_OPEN');

insert into branch (branch_name) values ('MidTown'),('RURAL'),('branch');

insert into  token (customer_id , token_type_id, branch_id) values(1, 1,1), (2, 1,1),(3, 1,1);

insert into role (name) values ('OPERATOR'),('MANAGER'),('ATTENDER');

insert into employee(name) values ('OPERATOR'),('OPERATOR2'),('MANAGER2'),('OPERATOR3'),('MANAGER4'),('OPERATOR4'),('ATTENDER1');

insert into employee_role( employee_id ,role_id) values (1, 1),(2, 1),(3, 2),(4, 1),(5, 2),(6, 1),(7, 3);

insert into counter(counter_id, employee_id, counter_type, branch_id,active) values (1,1, 'BOTH', 1, 1), (2,2,'PREMIUM', 1, 1),(3,3,'REGULAR', 1, 1),(4,4,'PREMIUM', 1, 1),(5,1,'REGULAR', 1, 1);

INSERT INTO service(name) VALUES ('ACC_VERIFICATION'),('BALANCE_ENQUIRY'),('CASH_WITHDRAW'),('CASH_DEPOSIT'),('CHECK_DEPOSIT'),('ACC_CLOSE'),('ACC_OPEN'),('MANAGER_APPROVAL'),('DOC_VERIFICATION');

insert into CounterService(counter_id, service_id) VALUES (1, 1),(1, 2),(1, 3),(2, 1),(2, 4),(3, 1),(3, 5),(5, 1),(5, 2),(5, 6),(5, 7),(5,8),(5,9);


