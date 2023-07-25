CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    date_time timestamp NOT NULL,
    version integer NOT NULL,
    created_date timestamp NOT NULL,
    last_modified_date timestamp NOT NULL
);

CREATE TABLE seats (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    sector varchar(255) NOT NULL,
    line varchar(255) NOT NULL,
    place varchar(255) NOT NULL,
    seat_type varchar(255) NOT NULL,
    seat_state varchar(255) NOT NULL,
    price float8 NOT NULL,
    event_id bigint NOT NULL,
    foreign key (event_id) references events(id)
);