-- 1. 모든 테이블의 데이터를 삭제
DELETE
FROM pages;
DELETE
FROM diaries;
DELETE
FROM breakups;
DELETE
FROM requests;
DELETE
FROM couples;
DELETE
FROM user_auth;
DELETE
FROM users;
DELETE
FROM scraps;
DELETE
FROM image_urls;
DELETE
FROM locations;
DELETE
FROM templates;
DELETE
FROM bookmarks;

-- 2. 테이블 삭제 (외래 키 제약 조건 때문에 순서가 중요)
DROP TABLE pages CASCADE CONSTRAINTS;
DROP TABLE diaries CASCADE CONSTRAINTS;
DROP TABLE breakups CASCADE CONSTRAINTS;
DROP TABLE requests CASCADE CONSTRAINTS;
DROP TABLE couples CASCADE CONSTRAINTS;
DROP TABLE user_auth CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;
DROP TABLE scraps CASCADE CONSTRAINTS;
DROP TABLE image_urls CASCADE CONSTRAINTS;
DROP TABLE locations CASCADE CONSTRAINTS;
DROP TABLE templates CASCADE CONSTRAINTS;
DROP TABLE bookmarks CASCADE CONSTRAINTS;

-- 3. 시퀀스 삭제
DROP SEQUENCE users_seq;
DROP SEQUENCE user_auth_seq;
DROP SEQUENCE couples_seq;
DROP SEQUENCE requests_seq;
DROP SEQUENCE diaries_seq;
DROP SEQUENCE pages_seq;
DROP SEQUENCE breakups_seq;
DROP SEQUENCE image_urls_seq;
DROP SEQUENCE locations_seq;
DROP SEQUENCE scraps_seq;
DROP SEQUENCE templates_seq;
DROP SEQUENCE bookmarks_seq;

-- 4. 트리거 삭제
DROP TRIGGER user_auth_bi;
DROP TRIGGER couples_bi;
DROP TRIGGER requests_bi;
DROP TRIGGER diaries_bi;
DROP TRIGGER pages_bi;
DROP TRIGGER image_urls_bi;
DROP TRIGGER locations_bi;
DROP TRIGGER scraps_bi;
DROP TRIGGER templates_bi;
DROP TRIGGER bookmarks_bi;

