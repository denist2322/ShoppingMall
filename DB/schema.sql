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
 nick_name VARCHAR(20) NOT NULL,
 cellphone CHAR(20) NOT NULL,
 delDate DATETIME COMMENT '탈퇴날짜'
);

INSERT INTO mall_user SET
 reg_date = NOW(),
 update_date = NOW(),
 user_email = "admin@test.com",
 user_password = "admin",
 nick_name = "관리자",
 cellphone = "010-0000-0000";


INSERT INTO mall_user SET
 reg_date = NOW(),
 update_date = NOW(),
 user_email = "user1@test.com",
 user_password = "user1",
 nick_name = "테스트 유저",
 cellphone = "010-1111-0000";

  SELECT * FROM mall_user;

CREATE TABLE product(
 id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
 reg_date DATETIME NOT NULL,
 update_date DATETIME NOT NULL,
 title VARCHAR(50) NOT NULL,
 `body` TEXT NOT NULL
);

INSERT INTO product SET
 reg_date = NOW(),
 update_date  = NOW(),
 title = "품목1",
 `body` = "품목1";

INSERT INTO product SET
 reg_date = NOW(),
 update_date  = NOW(),
 title = "품목2",
 `body` = "품목2";

 SELECT * FROM product;

CREATE TABLE question_answer(
id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
reg_date DATETIME NOT NULL,
update_date DATETIME NOT NULL,
`body` TEXT NOT NULL
);

INSERT INTO question_answer SET
 reg_date = NOW(),
 update_date  = NOW(),
 `body` = "환불 안해줄꺼야";

SELECT * FROM question_answer;

CREATE TABLE Question (
    id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `subject` VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    create_date DATETIME NOT NULL
);

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 1',
content = '주문 내역은 어디서 확인하나요 ?';

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 2',
content = '환불 기간은 어떻게 되나요 ?';

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 3',
content = '교환 신청은 어떻게 하나요 ?';

INSERT INTO Question SET
create_date = NOW(),
`subject` = '질문 4',
content = '교환 발송은 언제 되나요 ?';

 SELECT * FROM Question;