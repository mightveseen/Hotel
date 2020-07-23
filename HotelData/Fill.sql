USE HotelBase;
INSERT INTO `Attendances`(`name`, `section`, `price`)
VALUES 
	('Lunch', 'Food', 12.3),
	('Dinner', 'Food', 21.0),
    ('Breakfast', 'Food', 9.0),
    ('Playstation 4', 'Entertainment', 20.5);
INSERT INTO `Rooms`(`number`, `classification`, `room_number`, `capacity`, `status`, `price`)
VALUES 
	(203, 'President', 6, 4, 'RENTED', 330),
	(312, 'Business', 1, 2, 'FREE', 170),
	(401, 'Lux', 2, 4, 'FREE', 230),
    (105, 'Lux', 2, 4, 'RENTED',230),
    (506, 'President', 5, 10, 'SERVED', 430);
INSERT INTO `Guests`(`first_name`, `second_name`, `birthday`)
VALUES
	('Victoria', 'July', '1986-05-12'),
	('Robert', 'Johnson', '1967-12-01'),
	('Daniel', 'Blake', '1971-01-24');
INSERT INTO `Orders`(`order_date`, `guest_id`, `room_id`, `start_date`, `end_date`, `status`, `price`)
VALUES
	('2020-03-02T22:29:02.815', 2, 1, '2020-03-02', '2020-03-12', 'ACTIVE', 330),
    ('2020-03-02T22:29:16.230', 3, 4, '2020-03-02', '2020-05-02', 'ACTIVE', 230);
INSERT INTO `OrderAttendances`(`order_id`, `attendance_id`)
VALUES
	(1, 1),
    (1, 4),
    (1, 2),
    (2, 4);