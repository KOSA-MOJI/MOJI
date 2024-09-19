-- 1. users 테이블 생성
CREATE TABLE users (
                       email VARCHAR2(50) NOT NULL,
                       user_name VARCHAR2(50) NOT NULL,
                       birthday DATE NOT NULL,
                       gender CHAR(1) NOT NULL,
                       password VARCHAR2(150) NOT NULL,
                       created_at DATE DEFAULT sysdate NOT NULL,
                       role_id NUMBER(10) NOT NULL,
                       PRIMARY KEY (email)
);

-- 2. couples 테이블 생성
CREATE TABLE couples (
                         couple_id NUMBER NOT NULL,
                         user1_email VARCHAR2(50) NOT NULL,
                         user2_email VARCHAR2(50) NOT NULL,
                         couple_name VARCHAR2(50) NOT NULL,
                         couple_profile_image VARCHAR2(255) NOT NULL,
                         PRIMARY KEY (couple_id)
);

-- 3. requests 테이블 생성
CREATE TABLE requests (
                          request_id NUMBER NOT NULL,
                          request_email VARCHAR2(50) NOT NULL,
                          reciever_email VARCHAR2(50) NOT NULL,
                          created_at DATE DEFAULT sysdate NOT NULL,
                          PRIMARY KEY (request_id)
);

-- 4. diaries 테이블 생성
CREATE TABLE diaries (
                         diary_id NUMBER NOT NULL,
                         couple_id NUMBER NOT NULL,
                         cover_image VARCHAR2(150) NOT NULL,
                         bookmark_id NUMBER NOT NULL,
                         PRIMARY KEY (diary_id)
);

-- 5. pages 테이블 생성
CREATE TABLE pages (
                       page_id NUMBER NOT NULL,
                       diary_id NUMBER NOT NULL,
                       created_at DATE DEFAULT sysdate NOT NULL,
                       weather VARCHAR2(50) NOT NULL,
                       content CLOB NOT NULL,
                       public_status CHAR(1) NOT NULL,
                       template_id NUMBER NOT NULL,
                       PRIMARY KEY (page_id)
);

-- 6. breakups 테이블 생성
CREATE TABLE breakups (
                          couple_id NUMBER NOT NULL,
                          request_email VARCHAR2(50) NOT NULL,
                          created_at DATE DEFAULT sysdate NOT NULL,
                          expire_date DATE DEFAULT sysdate NOT NULL,
                          PRIMARY KEY (couple_id)
);

-- 7. image_urls 테이블 생성
CREATE TABLE image_urls (
                            image_url_id NUMBER NOT NULL,
                            location_id NUMBER NOT NULL,
                            map_image VARCHAR2(255) NOT NULL,
                            created_at DATE DEFAULT sysdate NOT NULL,
                            PRIMARY KEY (image_url_id)
);

-- 8. locations 테이블 생성
CREATE TABLE locations (
                           location_id NUMBER NOT NULL,
                           page_id NUMBER NOT NULL,
                           address VARCHAR2(150) NOT NULL,
                           latitude NUMBER(9,6) NOT NULL,
                           longitude NUMBER(9,6) NOT NULL,
                           created_at DATE DEFAULT sysdate NOT NULL,
                           PRIMARY KEY (location_id)
);

-- 9. scraps 테이블 생성
CREATE TABLE scraps (
                        scraps_id NUMBER NOT NULL,
                        email VARCHAR2(50) NOT NULL,
                        page_id NUMBER NOT NULL,
                        created_at DATE DEFAULT sysdate NOT NULL,
                        PRIMARY KEY (scraps_id)
);

-- 10. templates 테이블 생성
CREATE TABLE templates (
                           template_id NUMBER NOT NULL,
                           template_image VARCHAR2(150) NOT NULL,
                           created_at DATE DEFAULT sysdate NOT NULL,
                           PRIMARY KEY (template_id)
);

-- 11. bookmarks 테이블 생성
CREATE TABLE bookmarks (
                           bookmark_id NUMBER NOT NULL,
                           bookmark_image VARCHAR2(255) NOT NULL,
                           created_at DATE DEFAULT sysdate NOT NULL,
                           PRIMARY KEY (bookmark_id)
);

-- 12. roles 테이블 생성
CREATE TABLE roles (
                       role_id NUMBER(10) NOT NULL,
                       role_name VARCHAR2(255) NOT NULL,
                       PRIMARY KEY (role_id)
);

-- 시퀀스 생성
CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE couples_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE requests_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE diaries_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE pages_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE breakups_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE image_urls_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE locations_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE scraps_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE templates_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE bookmarks_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE roles_seq START WITH 1 INCREMENT BY 1;

-- 트리거 생성
-- couples 테이블 트리거
CREATE OR REPLACE TRIGGER couples_bi
    BEFORE INSERT ON couples
    FOR EACH ROW
BEGIN
    :NEW.couple_id := couples_seq.NEXTVAL;
END;
/

-- requests 테이블 트리거
CREATE OR REPLACE TRIGGER requests_bi
    BEFORE INSERT ON requests
    FOR EACH ROW
BEGIN
    :NEW.request_id := requests_seq.NEXTVAL;
END;
/

-- diaries 테이블 트리거
CREATE OR REPLACE TRIGGER diaries_bi
    BEFORE INSERT ON diaries
    FOR EACH ROW
BEGIN
    :NEW.diary_id := diaries_seq.NEXTVAL;
END;
/

-- pages 테이블 트리거
CREATE OR REPLACE TRIGGER pages_bi
    BEFORE INSERT ON pages
    FOR EACH ROW
BEGIN
    :NEW.page_id := pages_seq.NEXTVAL;
END;
/

-- image_urls 테이블 트리거
CREATE OR REPLACE TRIGGER image_urls_bi
    BEFORE INSERT ON image_urls
    FOR EACH ROW
BEGIN
    :NEW.image_url_id := image_urls_seq.NEXTVAL;
END;
/

-- locations 테이블 트리거
CREATE OR REPLACE TRIGGER locations_bi
    BEFORE INSERT ON locations
    FOR EACH ROW
BEGIN
    :NEW.location_id := locations_seq.NEXTVAL;
END;
/

-- scraps 테이블 트리거
CREATE OR REPLACE TRIGGER scraps_bi
    BEFORE INSERT ON scraps
    FOR EACH ROW
BEGIN
    :NEW.scraps_id := scraps_seq.NEXTVAL;
END;
/

-- templates 테이블 트리거
CREATE OR REPLACE TRIGGER templates_bi
    BEFORE INSERT ON templates
    FOR EACH ROW
BEGIN
    :NEW.template_id := templates_seq.NEXTVAL;
END;
/

-- bookmarks 테이블 트리거
CREATE OR REPLACE TRIGGER bookmarks_bi
    BEFORE INSERT ON bookmarks
    FOR EACH ROW
BEGIN
    :NEW.bookmark_id := bookmarks_seq.NEXTVAL;
END;
/

-- roles 테이블 트리거
CREATE OR REPLACE TRIGGER roles_bi
    BEFORE INSERT ON roles
    FOR EACH ROW
BEGIN
    :NEW.role_id := roles_seq.NEXTVAL;
END;
/

-- 외래 키 설정
ALTER TABLE users ADD CONSTRAINT fk_users_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id);
ALTER TABLE couples ADD CONSTRAINT fk_couples_user1_email FOREIGN KEY (user1_email) REFERENCES users(email);
ALTER TABLE couples ADD CONSTRAINT fk_couples_user2_email FOREIGN KEY (user2_email) REFERENCES users(email);
ALTER TABLE diaries ADD CONSTRAINT fk_diaries_couple_id FOREIGN KEY (couple_id) REFERENCES couples(couple_id);
ALTER TABLE pages ADD CONSTRAINT fk_pages_diary_id FOREIGN KEY (diary_id) REFERENCES diaries(diary_id);
ALTER TABLE pages ADD CONSTRAINT fk_pages_template_id FOREIGN KEY (template_id) REFERENCES templates(template_id);
ALTER TABLE diaries ADD CONSTRAINT fk_diaries_bookmark_id FOREIGN KEY (bookmark_id) REFERENCES bookmarks(bookmark_id);

