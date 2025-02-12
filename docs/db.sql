DROP DATABASE IF EXISTS clothify_store;
CREATE DATABASE clothify_store;
USE clothify_store;

CREATE TABLE employee (
    id INT AUTO_INCREMENT,
    user_name VARCHAR(15) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    nic VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(255),
    address VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    password VARCHAR(15) DEFAULT '1234',
    salary DECIMAL(8, 2) DEFAULT 0.0,
    type ENUM('EMPLOYEE', 'ADMIN') DEFAULT 'EMPLOYEE',
    role VARCHAR(30) NOT NULL,
    admin_id INT,
    PRIMARY KEY (id)
);

CREATE TABLE employee_phone (
    phone VARCHAR(12),
    employee_id INT NOT NULL,
    type ENUM('MOBILE', 'HOME', 'WHATSAPP') NOT NULL,
    PRIMARY KEY (phone),
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

CREATE TABLE customer (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(12),
    email VARCHAR(255),
    address VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE product (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    gender ENUM('MALE', 'FEMALE', 'COMMON') NOT NULL,
    `size` ENUM('XS', 'S', 'M', 'L', 'XL', 'XXL') NOT NULL,
    type VARCHAR(60) NOT NULL,
    brand VARCHAR(60) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(10, 2) DEFAULT 0.0,
    stock INT(10) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE supplier (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    email VARCHAR(255),
    address VARCHAR(255) NOT NUll,
    type ENUM('BUSINESS', 'INDIVIDUAL') NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE `order` (
    id INT AUTO_INCREMENT,
    `date` DATE NOT NULL,
    `time` TIME NOT NULL,
    amount DECIMAL(10, 2),
    employee_id INT NOT NULL,
    customer_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE order_item (
    order_id INT,
    product_id INT,
    discount DECIMAL(10, 2),
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES `order` (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE product_supplier (
    supplier_id INT,
    product_id INT,
    `date` DATE,
    `time` TIME,
    quantity INT NOT NULL,
    supplier_price DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('PENDING', 'PAID') DEFAULT 'PENDING',
    payment_date DATE,
    PRIMARY KEY (supplier_id, product_id, `date`, `time`),
    FOREIGN KEY (supplier_id) REFERENCES supplier (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE report (
    id INT AUTO_INCREMENT,
    `date` DATE NOT NULL,
    `time` TIME NOT NULL,
    employee_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

DESC employee;
DESC employee_phone;
DESC customer;
DESC product;
DESC supplier;
DESC `order`;
DESC order_item;
DESC product_supplier;
DESC report;


INSERT INTO employee (user_name, full_name, nic, email, address, dob, password, salary, type, role,
admin_id) VALUES
('god_xero', 'h.r.sathish shan', '200121001975', 'shansathish38@gmail.com', 'no.60,alokamawatha,walawegama,udawalawa,embilipitiya', '2001/07/28', '1111', 400000.0, 2, 'Manager', null
);

INSERT INTO employee_phone (phone, employee_id) VALUES
('0770110488', 1),
('0728045217', 1);

SELECT * FROM employee;
