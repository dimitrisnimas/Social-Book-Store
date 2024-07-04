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
DROP TABLE IF EXISTS`user_book` ;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `users` (
  userid int not NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) DEFAULT 'guest',
  PRIMARY KEY (userid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `userprofile` (
    userid int NOT NULL AUTO_INCREMENT,
    userfullname varchar(255) DEFAULT NULL,
    username varchar(255) DEFAULT NULL, 
    age int NOT NULL,
    address varchar(255),
    phonenumber int, 
    PRIMARY KEY (userid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `bookcategory` (
    categoryid int NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    PRIMARY KEY (categoryid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `bookauthor` (
    authorid int NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    userid int NOT NULL,
    PRIMARY KEY (authorid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `book` (
    bookid int NOT NULL AUTO_INCREMENT,
    title varchar(255) DEFAULT NULL,
    userid int NOT NULL,
    authorid int not NULL,
    description text,
    PRIMARY KEY (bookid),
    FOREIGN KEY (userid) REFERENCES `userprofile`(userid),
    FOREIGN KEY (authorid) REFERENCES `bookauthor`(authorid)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `user_categories` (
  userid int NOT NULL,
  categoryid int NOT NULL,
  PRIMARY KEY (userid, categoryid),
  FOREIGN KEY (userid) REFERENCES `userprofile`(userid),
  FOREIGN KEY (categoryid) REFERENCES `bookcategory`(categoryid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_authors` (
  userid int NOT NULL,
  authorid int NOT NULL,
  PRIMARY KEY (userid, authorid),
  FOREIGN KEY (userid) REFERENCES `userprofile`(userid),
  FOREIGN KEY (authorid) REFERENCES `bookauthor`(authorid)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_book` (
  `userid` INT NOT NULL,
  `bookid` INT NOT NULL,
  PRIMARY KEY (`userid`, `bookid`),
  FOREIGN KEY (`userid`) REFERENCES `userprofile` (`userid`) ON DELETE CASCADE,
  FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `book_author_book` (
  `bookid` INT NOT NULL,
  `authorid` INT NOT NULL,
  PRIMARY KEY (`bookid`, `authorid`),
  FOREIGN KEY (`bookid`) REFERENCES `book` (`bookid`) ON DELETE CASCADE,
  FOREIGN KEY (`authorid`) REFERENCES `bookauthor` (`authorid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `users` (username, password, role) VALUES
('john_doe', 'password123', 'GUEST'),
('user1', '12345', 'USER'),
('bob_jackson', 'bobspassword', 'GUEST');

