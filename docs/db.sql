DROP DATABASE IF EXISTS clothify_store;
CREATE DATABASE clothify_store;
USE clothify_store;

CREATE TABLE `user` (
    id INT AUTO_INCREMENT,
    user_name VARCHAR(50) UNIQUE NOT NULL,
    initials VARCHAR(10) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50),
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

DESC `user`;


INSERT INTO `user` (user_name, initials, first_name, last_name, nic, email, address, dob, password, salary, type, role,
admin_id) VALUES
(
    'GodXero',
    'H.R',
    'Sathish',
    'Shan',
    '200121001975',
    'shansathish38@gmail.com',
    'No.60, Alokamawatha, Walawegama, Udawalawa, Embilipitiya',
    '2001/07/28',
    '1111',
    400000.0,
    2,
    'Manager',
    null
);

SELECT * FROM `user`;
