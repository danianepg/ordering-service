insert into orders(id, customer, order_date, status) values (1, 'Daniane', now(), 'OPEN'); 
insert into order_products(id, quantity, product_id) values (1, 1, 1);
insert into order_products(id, quantity, product_id) values (2, 2, 2);
insert into orders_products(order_id, products_id) values (1, 1);
insert into orders_products(order_id, products_id) values (1, 2);

insert into orders(id, customer, order_date, status) values (2, 'Dobby', now(), 'IN_TRANSIT'); 
insert into order_products(id, quantity, product_id) values (3, 1, 3);
insert into order_products(id, quantity, product_id) values (4, 2, 4);
insert into orders_products(order_id, products_id) values (2, 3);
insert into orders_products(order_id, products_id) values (2, 4);

insert into orders(id, customer, order_date, status) values (3, 'Arya', now(), 'DELIVERED'); 
insert into order_products(id, quantity, product_id) values (5, 1, 5);
insert into order_products(id, quantity, product_id) values (6, 2, 6);
insert into orders_products(order_id, products_id) values (3, 5);
insert into orders_products(order_id, products_id) values (3, 6);

insert into orders(id, customer, order_date, status) values (4, 'Gimli', now(), 'CANCELED'); 
insert into order_products(id, quantity, product_id) values (7, 1, 1);
insert into order_products(id, quantity, product_id) values (8, 2, 2);
insert into orders_products(order_id, products_id) values (4, 7);
insert into orders_products(order_id, products_id) values (4, 8);


