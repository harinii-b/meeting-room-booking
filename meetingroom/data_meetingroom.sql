use meeting_room_db;
INSERT INTO meeting_rooms (room_name, capacity, location, has_projector, has_video_conference, amenities) 
SELECT 'Conference Room A', 20, 'Main Building 1st Floor', TRUE, TRUE, 'Whiteboard, Coffee machine'
WHERE NOT EXISTS (SELECT 1 FROM meeting_rooms WHERE room_name = 'Conference Room A');

INSERT INTO meeting_rooms (room_name, capacity, location, has_projector, has_video_conference, amenities) 
SELECT 'Conference Room B', 12, 'Main Building 2nd Floor', TRUE, FALSE, 'Whiteboard'
WHERE NOT EXISTS (SELECT 1 FROM meeting_rooms WHERE room_name = 'Conference Room B');

INSERT INTO meeting_rooms (room_name, capacity, location, has_projector, has_video_conference, amenities) 
SELECT 'Boardroom', 8, 'Executive Wing', TRUE, TRUE, 'Premium audio system, Refreshments'
WHERE NOT EXISTS (SELECT 1 FROM meeting_rooms WHERE room_name = 'Boardroom');

INSERT INTO meeting_rooms (room_name, capacity, location, has_projector, has_video_conference, amenities) 
SELECT 'Huddle Space 1', 4, 'Wing A', FALSE, FALSE, 'Whiteboard'
WHERE NOT EXISTS (SELECT 1 FROM meeting_rooms WHERE room_name = 'Huddle Space 1');

INSERT INTO meeting_rooms (room_name, capacity, location, has_projector, has_video_conference, amenities) 
SELECT 'Huddle Space 2', 4, 'Wing B', FALSE, FALSE, 'Whiteboard'
WHERE NOT EXISTS (SELECT 1 FROM meeting_rooms WHERE room_name = 'Huddle Space 2');

select * from meeting_room_bookings;
select * from users;