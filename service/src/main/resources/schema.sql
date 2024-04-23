-- drop table if exists product;
create table if not exists product
(
    id        serial primary key,
    sku       text   not null,
    inventory bigint not null default 0
);

-- drop table if exists product_order;
create table if not exists cart
(
    id serial primary key
);

-- drop table if exists line_item;
create table if not exists line_item
(
    id            serial primary key not null ,
    product       bigint not null references product (id),
    cart      bigint not null references cart (id)
);