# DB 생성
DROP DATABASE IF EXISTS shopingMall;
CREATE DATABASE shopingMall;
USE shopingMall;

CREATE TABLE mallUser(
 id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
 reg_date DATETIME NOT NULL,
 user_email VARCHAR(50) NOT NULL,
 user_password VARCHAR(100) NOT NULL,
 nick_name VARCHAR(20) NOT NULL,
 cellphoneNo CHAR(20) NOT NULL,
 authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨(3=일반,7=관리자)',
 delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전,1=탈퇴)',
 delDate DATETIME COMMENT '탈퇴날짜'
);

INSERT INTO mallUser SET
 reg_date = NOW(),
 user_email = "admin@test.com",
 user_password = "admin",
 nick_name = "관리자",
 cellphoneNo = "010-0000-0000",
 authLevel = 7;


INSERT INTO mallUser SET
 reg_date = NOW(),
 user_email = "user1@test.com",
 user_password = "user1",
 nick_name = "테스트 유저",
 cellphoneNo = "010-1111-0000";

  SELECT * FROM mallUser;

CREATE TABLE article(
 id INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
 reg_date DATETIME NOT NULL,
 update_date DATETIME NOT NULL,
 title VARCHAR(50) NOT NULL,
 `body` TEXT NOT NULL
);

INSERT INTO article SET
 reg_date = NOW(),
 update_date  = NOW(),
 title = "품목1",
 `body` = "품목1";

INSERT INTO article SET
 reg_date = NOW(),
 update_date  = NOW(),
 title = "품목2",
 `body` = "품목2";

 SELECT * FROM article;
