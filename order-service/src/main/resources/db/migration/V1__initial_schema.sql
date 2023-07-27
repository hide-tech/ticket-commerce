CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    event_id BIGINT NOT NULL,
    seat_id NOT NULL,
    event_name varchar(255) NOT NULL,
    seat_sector varchar(255) NOT NULL,
    seat_line varchar(255) NOT NULL,
    seat_place varchar(255) NOT NULL,
    event_date_time timestamp,
    price float8,
    order_status varchar(255) NOT NULL,
    created_date timestamp NOT NULL,
    last_modified_date timestamp NOT NULL,
    version integer NOT NULL
);