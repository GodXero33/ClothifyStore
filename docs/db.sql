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
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE employee_phone (
    phone VARCHAR(12),
    employee_id INT NOT NULL,
    type ENUM('MOBILE', 'HOME', 'WHATSAPP') NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (phone),
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

CREATE TABLE customer (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(12),
    email VARCHAR(255),
    address VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
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
    quantity INT(10) NOT NULL,
    description VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
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
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE `order` (
    id INT AUTO_INCREMENT,
    `date` DATE NOT NULL,
    `time` TIME NOT NULL,
    amount DECIMAL(10, 2),
    employee_id INT NOT NULL,
    customer_id INT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id),
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE order_item (
    order_id INT,
    product_id INT,
    discount DECIMAL(10, 2),
    is_deleted BOOLEAN DEFAULT FALSE,
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
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (supplier_id, product_id, `date`, `time`),
    FOREIGN KEY (supplier_id) REFERENCES supplier (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

DESC employee;
DESC employee_phone;
DESC customer;
DESC product;
DESC supplier;
DESC `order`;
DESC order_item;
DESC product_supplier;

INSERT INTO employee (user_name, full_name, nic, email, address, dob, password, salary, type, role, admin_id) VALUES
('god_xero', 'Sathish Shan', '200121001975', 'shansathish38@gmail.com', 'No.60, Alokamawatha, Walawegama, Udawalawa, Embilipitiya', '2001-07-28', '1111', 400000.00, 'ADMIN', 'Manager', NULL),
('kavinda123', 'Kavinda Perera', '199305205678', 'kavinda.perera@email.com', 'Colombo, Sri Lanka', '1993-05-20', '1234',
 50000.00, 'EMPLOYEE', 'Software Engineer', 1),
('nimali22', 'Nimali Karunaratne', '199812045678', 'nimali.karuna@email.com', 'Kandy, Sri Lanka', '1998-12-04', '1234', 60000.00, 'EMPLOYEE', 'HR Executive', 1),
('dinesh90', 'Dinesh Rajapaksha', '199002205678', 'dinesh.rajapaksha@email.com', 'Galle, Sri Lanka', '1990-02-20', '1234', 55000.00, 'EMPLOYEE', 'Data Analyst', 1),
('pasindu01', 'Pasindu Fernando', '199711105678', 'pasindu.fernando@email.com', 'Anuradhapura, Sri Lanka', '1997-11-10', '1234', 45000.00, 'EMPLOYEE', 'Project Manager', 1),
('mash_021', 'Masha Aravinda', '199507165678', 'masha.aravinda@email.com', 'Colombo, Sri Lanka', '1995-07-16', '1234', 65000.00, 'EMPLOYEE', 'Designer', 2),
('jayanthi1980', 'Jayanthi Kumari', '198004145678', 'jayanthi.kumari@email.com', 'Matara, Sri Lanka', '1980-04-14', '1234', 47000.00, 'EMPLOYEE', 'Secretary', 2),
('ranil2020', 'Ranil Perera', '199003205678', 'ranil.perera@email.com', 'Negombo, Sri Lanka', '1990-03-20', '1234', 72000.00, 'EMPLOYEE', 'Marketing Head', 2),
('ahmad019', 'Ahmad Junaid', '199603255678', 'ahmad.junaid@email.com', 'Gampaha, Sri Lanka', '1996-03-25', '1234', 68000.00, 'EMPLOYEE', 'Assistant Manager', 3),
('sangeetha19', 'Sangeetha Ruwan', '199912255678', 'sangeetha.ruwan@email.com', 'Trincomalee, Sri Lanka', '1999-12-25', '1234', 50000.00, 'EMPLOYEE', 'Accounts Officer', 3),
('tushara88', 'Tushara Wickramasinghe', '198812255678', 'tushara.wickra@email.com', 'Kurunegala, Sri Lanka', '1988-12-25', '1234', 53000.00, 'EMPLOYEE', 'IT Support', 3),
('nihal1407', 'Nihal Koralage', '199101045678', 'nihal.koralage@email.com', 'Ratnapura, Sri Lanka', '1991-01-04', '1234', 41000.00, 'EMPLOYEE', 'Assistant Engineer', 4),
('shanthy10', 'Shanthy Karunaratne', '199401205678', 'shanthy.karuna@email.com', 'Colombo, Sri Lanka', '1994-01-20', '1234', 56000.00, 'EMPLOYEE', 'Assistant HR', 4),
('buddhika09', 'Buddhika Weerasinghe', '198506255678', 'buddhika.weerasinghe@email.com', 'Jaffna, Sri Lanka', '1985-06-25', '1234', 49000.00, 'EMPLOYEE', 'Customer Support', 4),
('hiran2025', 'Hiran Fernando', '199102205678', 'hiran.fernando@email.com', 'Kandy, Sri Lanka', '1991-02-20', '1234', 53000.00, 'EMPLOYEE', 'Software Developer', 5),
('priya01', 'Priya Jayaratne', '199408245678', 'priya.jayaratne@email.com', 'Colombo, Sri Lanka', '1994-08-24', '1234', 54000.00, 'EMPLOYEE', 'Junior Accountant', 5),
('deshan7', 'Deshan Silva', '200005165678', 'deshan.silva@email.com', 'Colombo, Sri Lanka', '2000-05-16', '1234', 42000.00, 'EMPLOYEE', 'Logistics Manager', 5),
('theja23', 'Theja Liyanage', '199603085678', 'theja.liyanage@email.com', 'Kandy, Sri Lanka', '1996-03-08', '1234', 48000.00, 'EMPLOYEE', 'Warehouse Manager', 6),
('kavisha80', 'Kavisha Gunaratne', '198308095678', 'kavisha.gunaratne@email.com', 'Galle, Sri Lanka', '1983-08-09', '1234', 50000.00, 'EMPLOYEE', 'Business Analyst', 6),
('roshan0910', 'Roshan Jayasinghe', '199302245678', 'roshan.jayasinghe@email.com', 'Kandy, Sri Lanka', '1993-02-24', '1234', 55000.00, 'EMPLOYEE', 'UI/UX Designer', 6),
('neelanka2000', 'Neelanka Samarasinghe', '200010245678', 'neelanka.samarasinghe@email.com', 'Colombo, Sri Lanka', '2000-10-24', '1234', 43000.00, 'EMPLOYEE', 'Sales Executive', 7),
('priyankaa99', 'Priyanka Rajapaksha', '199701205678', 'priyanka.rajapaksha@email.com', 'Negombo, Sri Lanka', '1997-01-20', '1234', 58000.00, 'EMPLOYEE', 'Graphic Designer', 7),
('kusal07', 'Kusal Fernando', '199804105678', 'kusal.fernando@email.com', 'Gampaha, Sri Lanka', '1998-04-10', '1234', 46000.00, 'EMPLOYEE', 'Operations Manager', 7),
('priyanka2010', 'Priyanka Wickramasinghe', '199102095678', 'priyanka.wickramasinghe@email.com', 'Kurunegala, Sri Lanka', '1991-02-09', '1234', 48000.00, 'EMPLOYEE', 'Marketing Assistant', 8),
('rahul08', 'Rahul Kumar', '199503245678', 'rahul.kumar@email.com', 'Jaffna, Sri Lanka', '1995-03-24', '1234', 50000.00, 'EMPLOYEE', 'IT Specialist', 8),
('chaminda123', 'Chaminda Perera', '199709165678', 'chaminda.perera@email.com', 'Colombo, Sri Lanka', '1997-09-16', '1234', 54000.00, 'EMPLOYEE', 'Content Manager', 8),
('pranav21', 'Pranav Ruwan', '199208045678', 'pranav.ruwan@email.com', 'Matara, Sri Lanka', '1992-08-04', '1234', 51000.00, 'EMPLOYEE', 'Marketing Executive', 9),
('tharushi90', 'Tharushi Senanayake', '199503215678', 'tharushi.senanayake@email.com', 'Anuradhapura, Sri Lanka', '1995-03-21', '1234', 56000.00, 'EMPLOYEE', 'Assistant Manager', 9),
('dinusha2011', 'Dinusha Kumari', '199004205678', 'dinusha.kumari@email.com', 'Negombo, Sri Lanka', '1990-04-20', '1234', 47000.00, 'EMPLOYEE', 'HR Assistant', 9),
('naresh17', 'Naresh Kumar', '199306115678', 'naresh.kumar@email.com', 'Kurunegala, Sri Lanka', '1993-06-11', '1234', 49000.00, 'EMPLOYEE', 'Supervisor', 10);

INSERT INTO employee_phone (phone, employee_id, type) VALUES
('0772345678', 1, 'MOBILE'),
('0712345679', 1, 'WHATSAPP'),
('0782345680', 2, 'MOBILE'),
('0772345681', 2, 'MOBILE'),
('0722345682', 3, 'MOBILE'),
('0702345683', 3, 'WHATSAPP'),
('0782345684', 4, 'MOBILE'),
('0712345685', 4, 'MOBILE'),
('0772345686', 5, 'MOBILE'),
('0722345687', 5, 'WHATSAPP'),
('0782345688', 6, 'MOBILE'),
('0702345689', 6, 'MOBILE'),
('0772345690', 7, 'MOBILE'),
('0722345691', 7, 'WHATSAPP'),
('0782345692', 8, 'MOBILE'),
('0712345693', 8, 'MOBILE'),
('0772345694', 9, 'MOBILE'),
('0722345695', 9, 'WHATSAPP'),
('0782345696', 10, 'MOBILE'),
('0712345697', 10, 'MOBILE'),
('0772345698', 11, 'MOBILE'),
('0722345699', 11, 'WHATSAPP'),
('0782345700', 12, 'MOBILE'),
('0712345701', 12, 'MOBILE'),
('0772345702', 13, 'MOBILE'),
('0722345703', 13, 'WHATSAPP'),
('0782345704', 14, 'MOBILE'),
('0712345705', 14, 'MOBILE'),
('0772345706', 15, 'MOBILE'),
('0722345707', 15, 'WHATSAPP'),
('0782345708', 16, 'MOBILE'),
('0712345709', 16, 'MOBILE'),
('0772345710', 17, 'MOBILE'),
('0722345711', 17, 'WHATSAPP'),
('0782345712', 18, 'MOBILE'),
('0712345713', 18, 'MOBILE'),
('0772345714', 19, 'MOBILE'),
('0722345715', 19, 'WHATSAPP'),
('0782345716', 20, 'MOBILE'),
('0712345717', 20, 'MOBILE'),
('0772345718', 21, 'MOBILE'),
('0722345719', 21, 'WHATSAPP'),
('0782345720', 22, 'MOBILE'),
('0712345721', 22, 'MOBILE'),
('0772345722', 23, 'MOBILE'),
('0722345723', 23, 'WHATSAPP'),
('0782345724', 24, 'MOBILE'),
('0712345725', 24, 'MOBILE'),
('0772345726', 25, 'MOBILE'),
('0722345727', 25, 'WHATSAPP'),
('0782345728', 26, 'MOBILE'),
('0712345729', 26, 'MOBILE'),
('0772345730', 27, 'MOBILE'),
('0722345731', 27, 'WHATSAPP'),
('0782345732', 28, 'MOBILE'),
('0712345733', 28, 'MOBILE'),
('0772345734', 29, 'MOBILE'),
('0722345735', 29, 'WHATSAPP'),
('0782345736', 30, 'MOBILE'),
('0712345737', 30, 'MOBILE');

INSERT INTO product (name, gender, size, type, brand, price, discount, quantity, description) VALUES
('Classic White Shirt', 'MALE', 'M', 'Shirt', 'Arrow', 3500.00, 0.0, 50, 'Formal white shirt for men'),
('Slim Fit Jeans', 'MALE', 'L', 'Jeans', 'Levi\'s', 4500.00, 0.0, 30, 'Comfortable blue denim jeans'),
('Printed T-Shirt', 'MALE', 'S', 'T-Shirt', 'Nike', 2500.00, 0.0, 40, 'Casual printed t-shirt'),
('Hooded Sweatshirt', 'COMMON', 'XL', 'Sweatshirt', 'Adidas', 5000.00, 0.0, 20, 'Warm and stylish hoodie'),
('Cotton Kurta', 'MALE', 'L', 'Kurta', 'FabIndia', 3200.00, 0.0, 25, 'Traditional cotton kurta'),
('Formal Trousers', 'MALE', 'M', 'Trousers', 'Raymond', 4000.00, 0.0, 35, 'Office wear trousers'),
('Leather Jacket', 'MALE', 'L', 'Jacket', 'Zara', 7500.00, 0.0, 10, 'Trendy leather jacket'),
('Casual Shorts', 'MALE', 'M', 'Shorts', 'Puma', 2800.00, 0.0, 45, 'Summer cotton shorts'),
('Chinos', 'MALE', 'L', 'Chinos', 'Jack & Jones', 4200.00, 0.0, 30, 'Casual chinos for everyday wear'),
('Running Shoes', 'COMMON', 'XL', 'Shoes', 'Reebok', 6500.00, 0.0, 25, 'Lightweight running shoes'),
('Denim Jacket', 'FEMALE', 'S', 'Jacket', 'Forever 21', 7000.00, 0.0, 12, 'Trendy denim jacket'),
('Bodycon Dress', 'FEMALE', 'M', 'Dress', 'H&M', 5000.00, 0.0, 20, 'Elegant bodycon dress'),
('Ankle-Length Skirt', 'FEMALE', 'L', 'Skirt', 'Zara', 4500.00, 0.0, 18, 'Flowy ankle-length skirt'),
('Crop Top', 'FEMALE', 'S', 'Top', 'Forever 21', 2000.00, 0.0, 40, 'Trendy summer crop top'),
('Jogger Pants', 'FEMALE', 'M', 'Joggers', 'Nike', 3800.00, 0.0, 30, 'Comfortable jogging pants'),
('Party Gown', 'FEMALE', 'XL', 'Gown', 'Gucci', 15000.00, 0.0, 5, 'Luxury evening gown'),
('Formal Blazer', 'FEMALE', 'M', 'Blazer', 'Hugo Boss', 8000.00, 0.0, 10, 'Professional formal blazer'),
('Woolen Scarf', 'COMMON', 'M', 'Scarf', 'Burberry', 3500.00, 0.0, 25, 'Winter essential scarf'),
('Fleece Hoodie', 'COMMON', 'L', 'Hoodie', 'Adidas', 5500.00, 0.0, 20, 'Comfortable fleece hoodie'),
('Leather Belt', 'COMMON', 'M', 'Belt', 'Tommy Hilfiger', 1800.00, 0.0, 50, 'Stylish leather belt'),
('Sports Leggings', 'FEMALE', 'M', 'Leggings', 'Nike', 3200.00, 0.0, 35, 'High-stretch leggings'),
('Graphic T-Shirt', 'COMMON', 'L', 'T-Shirt', 'Uniqlo', 2400.00, 0.0, 40, 'Casual graphic tee'),
('Casual Loafers', 'MALE', 'L', 'Shoes', 'Clarks', 5000.00, 0.0, 20, 'Stylish casual loafers'),
('Sleeveless Blouse', 'FEMALE', 'S', 'Blouse', 'Mango', 2800.00, 0.0, 30, 'Chic sleeveless blouse'),
('Summer Hat', 'COMMON', 'XL', 'Hat', 'Gucci', 7000.00, 0.0, 15, 'Fashionable summer hat'),
('Cargo Pants', 'MALE', 'XL', 'Pants', 'Wrangler', 4200.00, 0.0, 20, 'Durable cargo pants'),
('Wool Sweater', 'COMMON', 'L', 'Sweater', 'Ralph Lauren', 5800.00, 0.0, 22, 'Cozy wool sweater'),
('Silk Saree', 'FEMALE', 'XL', 'Saree', 'Kanchipuram', 12000.00, 0.0, 8, 'Elegant silk saree'),
('Printed Hoodie', 'COMMON', 'M', 'Hoodie', 'Supreme', 6200.00, 0.0, 18, 'Limited edition hoodie'),
('Denim Overalls', 'FEMALE', 'L', 'Overalls', 'Levi\'s', 6800.00, 0.0, 12, 'Trendy denim overalls');

INSERT INTO customer (name, phone, email, address) VALUES
('John Doe', '1234567890', 'john@example.com', '123 Main St'),
('Jane Smith', '9876543210', 'jane@example.com', '456 Elm St'),
('Alice Brown', '5551234567', 'alice@example.com', '789 Oak St'),
('Michael Johnson', '1112223333', 'michael@example.com', '101 Pine St'),
('Emily Davis', '2223334444', 'emily@example.com', '202 Maple St'),
('David Wilson', '3334445555', 'david@example.com', '303 Birch St'),
('Sophia Martinez', '4445556666', 'sophia@example.com', '404 Cedar St'),
('James Anderson', '5556667777', 'james@example.com', '505 Spruce St'),
('Olivia Thomas', '6667778888', 'olivia@example.com', '606 Fir St'),
('Benjamin Harris', '7778889999', 'benjamin@example.com', '707 Oak St'),
('Charlotte White', '8889990000', 'charlotte@example.com', '808 Walnut St'),
('Daniel Clark', '9990001111', 'daniel@example.com', '909 Chestnut St'),
('Ava Lewis', '0001112222', 'ava@example.com', '1010 Redwood St'),
('Henry Walker', '1112223333', 'henry@example.com', '1111 Sequoia St'),
('Mia Hall', '2223334444', 'mia@example.com', '1212 Ash St'),
('Samuel Allen', '3334445555', 'samuel@example.com', '1313 Magnolia St'),
('Isabella Young', '4445556666', 'isabella@example.com', '1414 Cypress St'),
('Lucas King', '5556667777', 'lucas@example.com', '1515 Palm St'),
('Amelia Wright', '6667778888', 'amelia@example.com', '1616 Willow St');

INSERT INTO supplier (name, phone, email, address, type, description) VALUES
('ABC Textiles', '1112223333', 'abc@textiles.com', '10 Fabric St', 'BUSINESS', 'Wholesale fabric supplier'),
('XYZ Garments', '4445556666', 'xyz@garments.com', '20 Fashion Ave', 'BUSINESS', 'Premium garment supplier'),
('Tom Taylor', '7778889999', 'tom@taylor.com', '5 Handmade Ln', 'INDIVIDUAL', 'Independent tailor'),
('Global Fabrics', '8889990000', 'global@fabrics.com', '30 Linen St', 'BUSINESS', 'Export quality fabrics'),
('Elite Threads', '9990001111', 'elite@threads.com', '40 Cotton Rd', 'BUSINESS', 'High-end garments'),
('Vintage Apparel', '0001112222', 'vintage@apparel.com', '50 Retro Ln', 'BUSINESS', 'Classic fashion supplier'),
('Smart Stitches', '1112223333', 'smart@stitches.com', '60 Designer St', 'INDIVIDUAL', 'Bespoke tailoring service'),
('Trend Weavers', '2223334444', 'trend@weavers.com', '70 Knit Rd', 'BUSINESS', 'Modern clothing trends'),
('Royal Fabrics', '3334445555', 'royal@fabrics.com', '80 Silk Blvd', 'BUSINESS', 'Luxury fabric supplier'),
('Nova Textiles', '4445556666', 'nova@textiles.com', '90 Denim Ave', 'BUSINESS', 'Denim specialist'),
('Fine Tailors', '5556667777', 'fine@tailors.com', '100 Tailor St', 'INDIVIDUAL', 'Personalized tailoring'),
('Urban Wear', '6667778888', 'urban@wear.com', '110 Streetwear Ln', 'BUSINESS', 'Street fashion brand'),
('Classic Cuts', '7778889999', 'classic@cuts.com', '120 Suit Ave', 'BUSINESS', 'Premium suits and coats'),
('Modern Stitch', '8889990000', 'modern@stitch.com', '130 Couture Rd', 'BUSINESS', 'Haute couture supplier'),
('Blue Weave', '9990001111', 'blue@weave.com', '140 Fashion St', 'BUSINESS', 'Casual wear fabrics'),
('Silken Dreams', '0001112222', 'silken@dreams.com', '150 Silk St', 'BUSINESS', 'Exclusive silk textiles'),
('Pioneer Garments', '1112223333', 'pioneer@garments.com', '160 Uniform Rd', 'BUSINESS', 'Workwear and uniforms');

INSERT INTO `order` (`date`, `time`, amount, employee_id, customer_id) VALUES
('2025-02-01', '10:30:00', 150.00, 1, 1),
('2025-02-02', '11:00:00', 200.00, 1, 2),
('2025-02-03', '14:15:00', 75.50, 1, 3);

INSERT INTO order_item (order_id, product_id, discount) VALUES
(1, 1, 10.00),
(1, 2, 5.00),
(2, 3, 0.00),
(3, 1, 15.00);

INSERT INTO product_supplier (supplier_id, product_id, `date`, `time`, quantity, supplier_price, payment_status, payment_date) VALUES
(1, 1, '2025-01-15', '09:00:00', 100, 10.50, 'PAID', '2025-01-16'),
(2, 2, '2025-01-16', '10:30:00', 200, 15.75, 'PENDING', NULL),
(3, 3, '2025-01-17', '12:45:00', 50, 8.25, 'PAID', '2025-01-18');

UPDATE employee SET is_deleted = TRUE WHERE id = 10;
UPDATE employee_phone SET is_deleted = TRUE WHERE employee_id = 10;
UPDATE employee_phone SET is_deleted = TRUE WHERE employee_id = 10;

SELECT * FROM employee;
SELECT * FROM employee_phone;
SELECT * FROM product;
SELECT * FROM supplier;
SELECT * FROM product_supplier;
SELECT * FROM `order`;
SELECT * FROM order_item;


SELECT COUNT(*) FROM employee;
SELECT COUNT(*) FROM employee_phone;
SELECT COUNT(*) FROM product;
SELECT COUNT(*) FROM supplier;
SELECT COUNT(*) FROM product_supplier;
SELECT COUNT(*) FROM `order`;
SELECT COUNT(*) FROM order_item;

SELECT
    e.id,
    e.user_name,
    e.full_name,
    e.nic,
    e.email,
    e.address,
    e.dob,
    e.password,
    e.salary,
    e.type,
    e.role,
    e.admin_id,
    e.is_deleted,
    MAX(CASE WHEN ep.row_num = 1 THEN ep.phone END) AS phone1,
    MAX(CASE WHEN ep.row_num = 2 THEN ep.phone END) AS phone2,
    MAX(CASE WHEN ep.row_num = 3 THEN ep.phone END) AS phone3
FROM employee e
LEFT JOIN (
    SELECT
        phone,
        employee_id,
        ROW_NUMBER() OVER (PARTITION BY employee_id ORDER BY phone) AS row_num
    FROM employee_phone
    WHERE is_deleted = FALSE
) ep ON e.id = ep.employee_id
WHERE e.is_deleted = FALSE
GROUP BY e.id;

SELECT
    e.full_name,
    e.role,
    e.salary,
    COUNT(o.id) AS orders_handled,
    COALESCE(SUM(o.amount), 0) AS total_sales
FROM employee e
LEFT JOIN `order` o
    ON e.id = o.employee_id
    AND o.is_deleted = FALSE
    AND o.date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)  -- Last 30 days filter
WHERE e.is_deleted = FALSE
AND e.id = 1
GROUP BY e.id;

SELECT
    e.id,
    e.full_name,
    e.role,
    e.nic,
    e.salary,
    MAX(CASE WHEN p_row = 1 THEN ep.phone END) AS phone1,
    MAX(CASE WHEN p_row = 2 THEN ep.phone END) AS phone2,
    MAX(CASE WHEN p_row = 3 THEN ep.phone END) AS phone3
FROM employee e
LEFT JOIN (
    SELECT
        employee_id,
        phone,
        ROW_NUMBER() OVER (PARTITION BY employee_id ORDER BY phone) AS p_row
    FROM employee_phone
    WHERE is_deleted = FALSE
) ep ON e.id = ep.employee_id
WHERE e.is_deleted = FALSE
GROUP BY e.id, e.full_name, e.role, e.nic, e.salary;

SELECT
    e.id,
    e.full_name,
    e.role,
    e.nic,
    e.salary,
    MAX(CASE WHEN p_row = 1 THEN ep.phone END) AS phone1,
    MAX(CASE WHEN p_row = 2 THEN ep.phone END) AS phone2,
    MAX(CASE WHEN p_row = 3 THEN ep.phone END) AS phone3
FROM employee e
LEFT JOIN (
    SELECT
        employee_id,
        phone,
        ROW_NUMBER() OVER (PARTITION BY employee_id ORDER BY phone) AS p_row
    FROM employee_phone
) ep ON e.id = ep.employee_id
WHERE e.is_deleted = TRUE
GROUP BY e.id, e.full_name, e.role, e.nic, e.salary;

SELECT
    e.id,
    e.full_name,
    e.role,
    e.nic,
    e.salary,
    MAX(CASE WHEN p_row = 1 THEN ep.phone END) AS phone1,
    MAX(CASE WHEN p_row = 2 THEN ep.phone END) AS phone2,
    MAX(CASE WHEN p_row = 3 THEN ep.phone END) AS phone3
FROM employee e
LEFT JOIN (
    SELECT
        employee_id,
        phone,
        ROW_NUMBER() OVER (PARTITION BY employee_id ORDER BY phone) AS p_row
    FROM employee_phone
    WHERE is_deleted = FALSE
) ep ON e.id = ep.employee_id
WHERE e.is_deleted = FALSE AND e.type = 'ADMIN'
GROUP BY e.id, e.full_name, e.role, e.nic, e.salary;
