DROP DATABASE IF EXISTS HotelBase;
CREATE DATABASE IF NOT EXISTS HotelBase;
USE HotelBase;
CREATE TABLE IF NOT EXISTS `Attendances` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50),
    `section` VARCHAR(50),
    `price` DOUBLE NOT NULL
);
CREATE TABLE IF NOT EXISTS `Guests` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(50),
    `second_name` VARCHAR(50),
    `birthday` DATE
);
CREATE TABLE IF NOT EXISTS `Rooms` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `number` INT,
    `classification` VARCHAR(50),
    `room_number` SMALLINT,
    `capacity` SMALLINT,
    `status` VARCHAR(20),
    `price` DOUBLE NOT NULL
);
CREATE TABLE IF NOT EXISTS `Orders` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `order_date` DATETIME,
    `guest_id` INT,
    `room_id` INT,
    `start_date` DATE,
    `end_date` DATE,
    `status` VARCHAR(20),
    `price` DOUBLE NOT NULL,
	FOREIGN KEY (`room_id`) REFERENCES `Rooms`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`guest_id`) REFERENCES `Guests`(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS `OrderAttendances` (
	`order_id` INT,
    `attendance_id` INT,
	FOREIGN KEY (`attendance_id`) REFERENCES `Attendances`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`order_id`) REFERENCES `Orders`(`id`) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS `Users` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50),
	`password` VARCHAR(120),
    `role` VARCHAR(20)
);

	