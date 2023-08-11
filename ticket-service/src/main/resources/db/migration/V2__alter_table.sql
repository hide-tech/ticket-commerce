ALTER TABLE events
ADD COLUMN created_by varchar(255);
ALTER TABLE events
ADD COLUMN last_modified_by varchar(255);