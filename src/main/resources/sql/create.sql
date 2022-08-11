DROP TABLE IF EXISTS weapon;
DROP TABLE IF EXISTS class;
DROP TABLE IF EXISTS sub;
DROP TABLE IF EXISTS special;

CREATE TABLE class (
  cls_id INT PRIMARY KEY,
  cls_jpn_name VARCHAR,
  cls_eng_name VARCHAR
);

CREATE TABLE sub (
  sub_id INT PRIMARY KEY,
  sub_jpn_name VARCHAR,
  sub_eng_name VARCHAR
);

CREATE TABLE special (
  spc_id INT PRIMARY KEY,
  spc_jpn_name VARCHAR,
  spc_eng_name VARCHAR
);

CREATE TABLE weapon (
  wpn_id INT PRIMARY KEY,
  wpn_jpn_name VARCHAR,
  wpn_eng_name VARCHAR,
  wpn_category_id INT REFERENCES class(cls_id),
  wpn_sub_id INT REFERENCES sub(sub_id),
  wpn_special_id INT REFERENCES special(spc_id)
);