# DB 생성
DROP DATABASE IF EXISTS shopingMall;
CREATE DATABASE shopingMall;
USE shopingMall;

CREATE TABLE mall_user(
 id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
 reg_date DATETIME NOT NULL,
 update_date DATETIME NOT NULL,
 user_email VARCHAR(50) NOT NULL,
 user_password VARCHAR(100) NOT NULL,
 `name` VARCHAR(20) NOT NULL,
 birthday INT(100) UNSIGNED NOT NULL,
 home_address VARCHAR(50) NOT NULL,
 cellphone CHAR(20) NOT NULL
);

INSERT INTO mall_user SET
 reg_date = NOW(),
 update_date = NOW(),
 user_email = "admin@test.com",
 user_password = "admin",
 `name` = "관리자",
 birthday = 100000,
 home_address = "대전 서구 갈마역로 6** (둔산동, 아이브)**200호**35230",
 cellphone = "010-0000-0000";


INSERT INTO mall_user SET
 reg_date = NOW(),
 update_date = NOW(),
 user_email = "user1@test.com",
 user_password = "user1",
 `name` = "테스트 유저",
 birthday = 110101,
 home_address = "대전 서구 갈마역로 6** (둔산동, 아이브)**200호**35230",
 cellphone = "010-1111-0000";

 SELECT * FROM mall_user;

CREATE TABLE product(
 id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
 reg_date DATETIME NOT NULL,
 title VARCHAR(50) NOT NULL,
 `body` TEXT,
 main_image TEXT,
 price INT UNSIGNED NOT NULL,
 discount INT UNSIGNED NOT NULL,
 category VARCHAR(20) NOT NULL
);

INSERT INTO product SET
 reg_date = NOW(),
 title = "더스티 오픈숄더 크롭 티셔츠",
 `body` = "평균 2~3일내로 발송됩니다.",
 main_image = "더스티 오프숄더 크롭 티셔츠 메인 이미지.jpg",
 price = 25000,
 discount = 10,
 category = "woman";

 SELECT * FROM product;

 CREATE TABLE product_image(
   id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   images TEXT,
   product_id INT(100) UNSIGNED NOT NULL
 );

INSERT INTO product_image SET
images = "더스티 오프숄더 크롭 티셔츠 상세 이미지1.jpg",
product_id = 1;

INSERT INTO product_image SET
images = "더스티 오프숄더 크롭 티셔츠 상세 이미지2.jpg",
product_id = 1;

INSERT INTO product_image SET
images = "더스티 오프숄더 크롭 티셔츠 상세 이미지3.jpg",
product_id = 1;

INSERT INTO product_image SET
images = "더스티 오프숄더 크롭 티셔츠 상세 이미지4.jpg",
product_id = 1;

INSERT INTO product_image SET
images = "더스티 오프숄더 크롭 티셔츠 상세 이미지5.jpg",
product_id = 1;

SELECT * FROM product;
SELECT * FROM product_image;

CREATE TABLE question_answer(
id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
reg_date DATETIME NOT NULL,
update_date DATETIME NOT NULL,
`body` TEXT NOT NULL,
question_id INT UNSIGNED NOT NULL
);

INSERT INTO question_answer SET
 reg_date = NOW(),
 update_date  = NOW(),
 `body` = "환불 안해줄꺼야",
  question_id=1;

  INSERT INTO question_answer SET
 reg_date = NOW(),
 update_date  = NOW(),
 `body` = "환불 안해줄꺼야",
  question_id=2;

  INSERT INTO question_answer SET
 reg_date = NOW(),
 update_date  = NOW(),
 `body` = "환불 안해줄꺼야",
  question_id=3;

SELECT * FROM question_answer;

CREATE TABLE Question (
    id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `subject` VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    create_date DATETIME NOT NULL,
    modify_date DATETIME NOT NULL,
    mall_user_id INT UNSIGNED NOT NULL
);

INSERT INTO Question SET
create_date = NOW(),
`subject` = '안녕 1',
content = '주문 내역은 어디서 확인하나요 ?',
modify_date = NOW(),
mall_user_id = 2;

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 2',
content = '환불 기간은 어떻게 되나요 ?',
modify_date = NOW(),
mall_user_id = 2;

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 3',
content = '교환 신청은 어떻게 하나요 ?',
modify_date = NOW(),
mall_user_id = 2;

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 4',
content = '교환 발송은 언제 되나요 ?',
modify_date = NOW(),
mall_user_id = 2;

SELECT * FROM Question;

CREATE TABLE Shopping_cart(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cart_color VARCHAR(20) NOT NULL,
    cart_size VARCHAR(20) NOT NULL,
    cart_count INT UNSIGNED NOT NULL,
    cart_total_price INT UNSIGNED NOT NULL,
    product_id INT UNSIGNED NOT NULL,
    mall_user_id INT UNSIGNED NOT NULL
);

INSERT INTO Shopping_cart SET
cart_color = "White",
cart_size = "Free",
cart_count = 2,
cart_total_price = 50000,
product_id = 1,
mall_user_id = 1;

SELECT * FROM shopping_cart;

CREATE TABLE Product_color(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_color VARCHAR(20),
    product_id INT UNSIGNED NOT NULL
);

CREATE TABLE Product_size(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_size VARCHAR(20),
    product_id INT UNSIGNED NOT NULL
);

INSERT INTO Product_color SET
   product_color = "Ivory",
   product_id = 1;
INSERT INTO Product_color SET
   product_color = "Pink",
   product_id = 1;
INSERT INTO Product_color SET
   product_color = "Skyblue",
   product_id = 1;
INSERT INTO Product_color SET
   product_color = "Black",
   product_id = 1;
INSERT INTO Product_size SET
   product_size = "S",
   product_id = 1;
INSERT INTO Product_size SET
   product_size = "M",
   product_id = 1;
INSERT INTO Product_size SET
   product_size = "L",
   product_id = 1;

CREATE TABLE `order_sheet` (
   id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
   sheet_number VARCHAR(20) NOT NULL,
   sheet_orderer_email VARCHAR(20) NOT NULL,
   sheet_orderer_name VARCHAR(20) NOT NULL,
   sheet_orderer_phone VARCHAR(20) NOT NULL,
   sheet_receiver_name VARCHAR(20) NOT NULL,
   sheet_receiver_phone VARCHAR(20) NOT NULL,
   sheet_receiver_address VARCHAR(100) NOT NULL,
   sheet_option TEXT NOT NULL,
   shipping_cost INT UNSIGNED NOT NULL,
   product_cost INT UNSIGNED NOT NULL,
   total_price INT NOT NULL,
   now_state INT(4) UNSIGNED NOT NULL COMMENT '0: 입금전, 1:배송준비중, 2:배송중, 3:배송완료'
);


SELECT * FROM Product_color;
SELECT * FROM Product_size;

