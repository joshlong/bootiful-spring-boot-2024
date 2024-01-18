create table if not exists customer
(
    id   serial primary key not null,
    name text               not null unique
);

create table if not exists product
(
    id          serial primary key not null,
    customer    bigint             not null references customer (id),
    name        text               not null unique,
    description text               not null unique,
    inventory   bigint             not null default 0
);

create table if not exists product_orders
(
    id       serial primary key,
    customer bigint not null references customer (id),
    product  bigint not null references product (id)
);