DROP DATABASE IF EXISTS AM_JSP_25_04;
CREATE DATABASE AM_JSP_25_04;
USE AM_JSP_25_04;

# 게시글 테이블 생성
CREATE TABLE article (
                         id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         regDate DATETIME NOT NULL,
                         updateDate DATETIME NOT NULL,
                         title CHAR(100) NOT NULL,
                         `body` TEXT NOT NULL
);
# 회원 테이블 생성
CREATE TABLE `member` (
                          id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          regDate DATETIME NOT NULL,
                          loginId CHAR(30) NOT NULL,
                          loginPw CHAR(200) NOT NULL,
                          `name` CHAR(100) NOT NULL
);

# 게시글 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

# 회원 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '김철수';

INSERT INTO `member`
SET regDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '홍길동';

# 게시글 테이블에 memberId 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

# memberId 값 추가
UPDATE article
SET memberId = 1
WHERE id IN (1,2);

UPDATE article
SET memberId = 2
WHERE id = 3;

SELECT *
FROM article
ORDER BY id DESC;

SELECT *
FROM `member`;

######################################################################
# 게시글 데이터 대량 생성 memberId 추가
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = CEILING(RAND() * 4),
title = CONCAT('제목',SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
`body` = CONCAT('내용',SUBSTRING(RAND() * 1000 FROM 1 FOR 2));

# 회원 데이터 대량 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = CONCAT('loginId ',SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
loginPw = CONCAT('loginPw ',SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
`name` = CONCAT('name ',SUBSTRING(RAND() * 1000 FROM 1 FOR 2));
