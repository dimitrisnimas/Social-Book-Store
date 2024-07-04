CREATE DATABASE IF NOT EXISTS `bookstore`;
USE `bookstore`;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `userprofile`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `bookcategory`;
DROP TABLE IF EXISTS `bookauthor`;
DROP TABLE IF EXISTS `user_authors`;
DROP TABLE IF EXISTS `user_categories`;
DROP TABLE IF EXISTS `book_author_book`;
DROP TABLE IF EXISTS `book_category_book`;
DROP TABLE IF EXISTS `user_book_offers` ;
DROP TABLE IF EXISTS `user_requested_books`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `users` (
  userid int not NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) DEFAULT 'guest',
  PRIMARY KEY (userid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `userprofile` (
    userprofile_id int NOT NULL AUTO_INCREMENT,
    fullname varchar(255) DEFAULT NULL,
    username varchar(255) DEFAULT NULL, 
    age int NOT NULL,
    address varchar(255),
    phonenumber varchar(10), 
    PRIMARY KEY (userprofile_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `bookcategory` (
    categoryid int NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    PRIMARY KEY (categoryid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `bookauthor` (
    authorid int NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    PRIMARY KEY (authorid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `book` (
    bookid int NOT NULL AUTO_INCREMENT,
    title varchar(255) DEFAULT NULL,
    userprofile_id int NOT NULL,
    description text,
    PRIMARY KEY (bookid),
    FOREIGN KEY (userprofile_id) REFERENCES `userprofile`(userprofile_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `user_categories` (
  userprofile_id int NOT NULL,
  categoryid int NOT NULL,
  PRIMARY KEY (userprofile_id, categoryid),
  FOREIGN KEY (userprofile_id) REFERENCES `userprofile`(userprofile_id),
  FOREIGN KEY (categoryid) REFERENCES `bookcategory`(categoryid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_authors` (
  userprofile_id int NOT NULL,
  authorid int NOT NULL,
  PRIMARY KEY (userprofile_id, authorid),
  FOREIGN KEY (userprofile_id) REFERENCES `userprofile`(userprofile_id),
  FOREIGN KEY (authorid) REFERENCES `bookauthor`(authorid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_book_offers` (
  `userprofile_id` INT NOT NULL,
  `bookid` INT NOT NULL,
  PRIMARY KEY (`userprofile_id`, `bookid`),
  FOREIGN KEY (`userprofile_id`) REFERENCES `userprofile` (`userprofile_id`) ON DELETE CASCADE,
  FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_requested_books` (
  `userprofile_id` INT NOT NULL,
  `bookid` INT NOT NULL,
  PRIMARY KEY (`userprofile_id`, `bookid`),
  FOREIGN KEY (`userprofile_id`) REFERENCES `userprofile` (`userprofile_id`),
  FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `book_author_book` (
  `bookid` INT NOT NULL,
  `authorid` INT NOT NULL,
  PRIMARY KEY (`bookid`, `authorid`),
  FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`) ON DELETE CASCADE,
  FOREIGN KEY (`authorid`) REFERENCES `bookauthor` (`authorid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `book_category_book` (
	`bookid` INT NOT NULL,
    `categoryid` INT NOT NULL,
    PRIMARY KEY (`bookid`, `categoryid`),
    FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`) ON DELETE CASCADE,
    FOREIGN KEY (`categoryid`) REFERENCES `bookcategory` (`categoryid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `users` (username, password, role) VALUES
('user1', '$2a$10$YECF6Bj11.sz7FDIs4104uRYLjSSeSQt0ln3VyfvccxwJLLshkiLC', 'USER'),
('user2', '$2a$10$YECF6Bj11.sz7FDIs4104uRYLjSSeSQt0ln3VyfvccxwJLLshkiLC', 'USER'),
('user3', '$2a$10$YECF6Bj11.sz7FDIs4104uRYLjSSeSQt0ln3VyfvccxwJLLshkiLC', 'GUEST');

INSERT INTO `bookcategory` (name) VALUES ('Art'), ('Comic'), ('Fantasy'), ('Fiction'), 
('Biographies'), ('History'), ('Science'), ('Literature'), ('Adventure'), ('Crime'), ('Other');

INSERT INTO `bookauthor` (name) VALUES ('Author1'), ('Author2'), ('Author3'), ('Author4'), 
('Author5'), ('Author6'), ('Author7'), ('Other');

INSERT INTO `userprofile` (fullname, username, age, address, phonenumber) VALUES
('John Doe', 'user1', 30, '123 Main St', 1234567890),
('Jane Smith', 'user2', 25, '456 Elm St', 2345678901),
('Alice Johnson', 'user3', 28, '789 Oak St', 3456789012);

INSERT INTO `user_categories` (userprofile_id, categoryid) VALUES
(1,1),
(1,2);

INSERT INTO `user_authors` (userprofile_id, authorid) VALUES
(1,1),
(1,2);

INSERT INTO `book` (title, userprofile_id, description) VALUES
('The Great Gatsby', 1, 'The story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan.'),
('1984', 1, 'A dystopian social science fiction novel and cautionary tale of a totalitarian society.');

INSERT INTO `user_book_offers` (userprofile_id, bookid) VALUES
('1','1'), ('1','2');

INSERT INTO `user_requested_books` (userprofile_id, bookid) VALUES
('2','1'), ('2','2');

INSERT INTO `book_author_book` (bookid, authorid) VALUES
(1, 1), (2, 2);

INSERT INTO `book_category_book` (bookid, categoryid) VALUES
(1, 1), (2, 2);


