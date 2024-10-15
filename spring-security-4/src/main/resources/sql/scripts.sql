create table users(username varchar(50) not null primary key, password varchar(500) not null, enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

-- create users
INSERT IGNORE INTO users VALUES('user', '{noop}UserUser@1234', '1');
INSERT IGNORE INTO authorities VALUES('user', 'read');

INSERT IGNORE INTO users VALUES('admin', '{bcrypt}$2a$12$56/aRxKgGwepwU4vYnFS7uDZBtL4OmmmjF8WGfYx1.9UDI1cWcyCK', '1');
INSERT IGNORE INTO authorities VALUES('admin', 'admin');

SELECT `users`.`username`,
    `users`.`password`,
    `users`.`enabled`
FROM `eazy_bank`.`users`;

SELECT `authorities`.`username`,
    `authorities`.`authority`
FROM `eazy_bank`.`authorities`;