CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    added_by VARCHAR(255) NOT NULL
);



CREATE TABLE product_price (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    discount_percent DECIMAL(5, 2) DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);


CREATE TABLE product_price_change_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    old_price DECIMAL(10, 2),
    new_price DECIMAL(10, 2),
    old_discount_percent DECIMAL(5, 2),
    new_discount_percent DECIMAL(5, 2),
    operation_type ENUM('insert', 'update', 'delete') NOT NULL,
    operation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    performed_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);



SELECT
    p.name AS product_name,
    p.category AS product_category,
    pp.price AS product_price,
    pp.updated_by AS price_updated_by,
    pp.updated_at AS price_updated_at
FROM
    product p
JOIN
    product_price pp ON p.id = pp.product_id;
