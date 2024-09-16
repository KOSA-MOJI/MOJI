-- 테이블 삭제
--DROP TABLE diaries CASCADE CONSTRAINTS;
--DROP TABLE pages CASCADE CONSTRAINTS;
--DROP TABLE templates CASCADE CONSTRAINTS;
--DROP TABLE locations CASCADE CONSTRAINTS;
--DROP TABLE image_urls CASCADE CONSTRAINTS;


-- 시퀀스 생성
CREATE SEQUENCE templates_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE image_urls_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE locations_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE diaries_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE pages_seq START WITH 1 INCREMENT BY 1;

-- 테이블 생성
CREATE TABLE templates (
                           template_id NUMBER PRIMARY KEY,  -- 기본 키
                           template_image VARCHAR2(150) NULL,  -- NULL 허용
                           created_at DATE DEFAULT sysdate NULL  -- NULL 허용
);

CREATE TABLE image_urls (
                            image_url_id NUMBER PRIMARY KEY,  -- 기본 키
                            location_id NUMBER NOT NULL,  -- NULL 허용하지 않음
                            map_image VARCHAR2(255) NULL,  -- NULL 허용
                            created_at DATE DEFAULT sysdate NULL  -- NULL 허용
);

CREATE TABLE locations (
                           location_id NUMBER PRIMARY KEY,  -- 기본 키
                           page_id NUMBER NOT NULL,  -- NULL 허용하지 않음
                           address VARCHAR2(150) NULL,  -- NULL 허용
                           latitude NUMBER(9,6) NULL,  -- NULL 허용
                           longitude NUMBER(9,6) NULL,  -- NULL 허용
                           created_at DATE DEFAULT sysdate NULL  -- NULL 허용
);

CREATE TABLE diaries (
                         diary_id NUMBER PRIMARY KEY,  -- 기본 키
                         couple_id NUMBER NULL,  -- NULL 허용
                         cover_image VARCHAR2(150) NULL,  -- NULL 허용
                         bookmark_id NUMBER NULL  -- NULL 허용
);

CREATE TABLE pages (
                       page_id NUMBER PRIMARY KEY,  -- 기본 키
                       diary_id NUMBER NOT NULL,  -- NULL 허용하지 않음
                       created_at DATE DEFAULT sysdate NULL,  -- NULL 허용
                       weather VARCHAR2(50) NULL,  -- NULL 허용
                       content CLOB NULL,  -- NULL 허용
                       public_status CHAR(1) NULL,  -- NULL 허용
                       template_id NUMBER NOT NULL,  -- NULL 허용하지 않음
                       FOREIGN KEY (diary_id) REFERENCES diaries(diary_id),
                       FOREIGN KEY (template_id) REFERENCES templates(template_id)
);

-- 트리거 생성
CREATE OR REPLACE TRIGGER templates_bi
BEFORE INSERT ON templates
FOR EACH ROW
BEGIN
    :NEW.template_id := templates_seq.NEXTVAL;  -- 기본 키 자동 증가
END;
/

CREATE OR REPLACE TRIGGER image_urls_bi
BEFORE INSERT ON image_urls
FOR EACH ROW
BEGIN
    :NEW.image_urls_id := image_urls_seq.NEXTVAL;  -- 기본 키 자동 증가
END;
/

CREATE OR REPLACE TRIGGER locations_bi
BEFORE INSERT ON locations
FOR EACH ROW
BEGIN
    :NEW.location_id := locations_seq.NEXTVAL;  -- 기본 키 자동 증가
END;
/

CREATE OR REPLACE TRIGGER diaries_bi
BEFORE INSERT ON diaries
FOR EACH ROW
BEGIN
    :NEW.diary_id := diaries_seq.NEXTVAL;  -- 기본 키 자동 증가
END;
/

CREATE OR REPLACE TRIGGER pages_bi
BEFORE INSERT ON pages
FOR EACH ROW
BEGIN
    :NEW.page_id := pages_seq.NEXTVAL;  -- 기본 키 자동 증가
END;
/
