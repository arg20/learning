CREATE TABLE users (
  id              INT          NOT NULL AUTO_INCREMENT,
  first_name      VARCHAR(64)  NOT NULL,
  last_name       VARCHAR(64)  NOT NULL,
  email           VARCHAR(255) NOT NULL UNIQUE,
  age             INT          NOT NULL,
  created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

INSERT INTO users (first_name, last_name, email, age) VALUES ("Foo", "Boo", "foo@nomail.com", 20);

INSERT INTO users (first_name, last_name, email, age) VALUES ("Bar", "Far", "bar@nomail.com", 16);