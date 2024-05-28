create table if not exists orders
(
    id serial primary key
);

create table if not exists orders_line_items
(
    id       serial primary key,
    quantity int not null default 0,
    product  int not null default 0,
    orders    int references orders (id)
);