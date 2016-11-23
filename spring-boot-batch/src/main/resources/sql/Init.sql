 USE DEMO; 
 DROP TABLE  IF EXISTS employee; 
 CREATE TABLE employee (
  Employee_Id INT NOT NULL,
  name VARCHAR(45) NULL,
  age INT NULL,
  salary DOUBLE NULL,
  PRIMARY KEY (Employee_Id)); 
  
  DROP TABLE  IF EXISTS employee_address; 
  CREATE TABLE employee_address (
  EMPLOYEE_ADDRESS_ID INT NOT NULL,
  EMPLOYEE_ID INT NULL,
  ADDRESS1 VARCHAR(45) NULL,
  ADDRESS2 VARCHAR(45) NULL,
  City VARCHAR(45) NULL,
  State VARCHAR(45) NULL,
  Zip VARCHAR(45) NULL,
  PRIMARY KEY (EMPLOYEE_ADDRESS_ID)); 
  
   DROP TABLE  IF EXISTS employee_benefits; 
  CREATE TABLE employee_benefits (
  EMPLOYEE_BENEFITS_ID INT NOT NULL,
  EMPLOYEE_ID INT NULL,
  BENEFIT_Name VARCHAR(45) NULL,
  BENEFIT_Value VARCHAR(45) NULL,
  PRIMARY KEY (EMPLOYEE_BENEFITS_ID)); 
  
INSERT INTO employee (Employee_Id, name, age, salary) VALUES ('1', 'test', '19', '11000');
INSERT INTO employee (Employee_Id, name, age, salary) VALUES ('2', 'test1', '29', '21000');
INSERT INTO employee (Employee_Id, name, age, salary) VALUES ('3', 'test', '39', '24000');
INSERT INTO employee (Employee_Id, name, age, salary) VALUES ('4', 'test', '24', '15000');

INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('1', '1', 'aabc', 'Malden', 'MA', '02148');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('2', '1', 'abc', 'Boston', 'MA', '02248');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('3', '2', 'bbc', 'Malden', 'MA', '02148');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('4', '3', 'gjh', 'Malden', 'MA', '02148');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('5', '4', 'gilmore', 'Somerville', 'MA', '02145');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('6', '3', 'congress st', 'Boston', 'MA', '02248');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('7', '4', 'kings st', 'Woburn', 'MA', '03148');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('8', '2', 'aabc', 'Malden', 'MA', '02148');
INSERT INTO employee_address (EMPLOYEE_ADDRESS_ID, EMPLOYEE_ID, ADDRESS1, City, State, Zip) VALUES ('9', '4', 'aabc', 'Malden', 'MA', '02148');

INSERT INTO employee_benefits (EMPLOYEE_BENEFITS_ID, EMPLOYEE_ID, BENEFIT_Name, BENEFIT_Value) VALUES ('1', '1', 'abc', 'bbc');
INSERT INTO employee_benefits (EMPLOYEE_BENEFITS_ID, EMPLOYEE_ID, BENEFIT_Name, BENEFIT_Value) VALUES ('2', '2', 'afdfbc', 'bfffbc');
INSERT INTO employee_benefits (EMPLOYEE_BENEFITS_ID, EMPLOYEE_ID, BENEFIT_Name, BENEFIT_Value) VALUES ('3', '4', 'afffbc', 'bbc');
INSERT INTO employee_benefits (EMPLOYEE_BENEFITS_ID, EMPLOYEE_ID, BENEFIT_Name, BENEFIT_Value) VALUES ('4', '4', 'aggbc', 'ggbbc');
INSERT INTO employee_benefits (EMPLOYEE_BENEFITS_ID, EMPLOYEE_ID, BENEFIT_Name, BENEFIT_Value) VALUES ('5', '1', 'ggabc', 'ggbbc');