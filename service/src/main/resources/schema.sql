create table if not exists purchase
(
    id         serial primary key,
    username   text not null,
    product_id int  not null,
    quantity   int  not null default 1
);