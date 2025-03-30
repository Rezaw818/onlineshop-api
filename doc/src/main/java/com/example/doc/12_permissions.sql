insert into permission (id, description, name, parent_id)
values (1, 'دسترسی‌ها', 'root', null);

insert into permission (id, description, name, parent_id)
values (2, 'مدیریت لینک‌ها', 'nav_management', 1);

insert into permission (id, description, name, parent_id)
values (3, 'مدیریت محتوا', 'content_management', 1);

insert into permission (id, description, name, parent_id)
values (4, 'مدیریت اسلایدر', 'slider_management', 1);

insert into permission (id, description, name, parent_id)
values (5, 'مدیریت بلاگ', 'blog_management', 1);

insert into permission (id, description, name, parent_id)
values (6, 'مدیریت محصولات', 'product_management', 1);

insert into permission (id, description, name, parent_id)
values (7, 'مدیریت کاربران', 'user_management', 1);

insert into permission (id, description, name, parent_id)
values (8, 'مدیریت مشتریان', 'customer_management', 1);

insert into permission (id, description, name, parent_id)
values (9, 'مدیریت فایل', 'file_management', 1);

insert into permission (id, description, name, parent_id)
values (10, 'مدیریت سفارشات', 'invoice_management', 10);

insert into permission (id, description, name, parent_id)
values (11, 'داشبورد کاربر', 'user_dashboard', 1);

insert into permission (id, description, name, parent_id)
values (200, 'ایجاد لینک جدید', 'add_nav', 2);
insert into permission (id, description, name, parent_id)
values (201, 'ویرایش لینک', 'edit_nav', 2);
insert into permission (id, description, name, parent_id)
values (202, 'حذف لینک', 'delete_nav', 2);
insert into permission (id, description, name, parent_id)
values (203, 'لیست لینک‌ها', 'list_nav', 2);

insert into permission (id, description, name, parent_id)
values (300, 'ایجاد محتوا جدید', 'add_content', 3);
insert into permission (id, description, name, parent_id)
values (301, 'ویرایش محتوا', 'edit_content', 3);
insert into permission (id, description, name, parent_id)
values (302, 'حذف محتوا', 'delete_content', 3);
insert into permission (id, description, name, parent_id)
values (303, 'لیست محتوا', 'list_content', 3);

insert into permission (id, description, name, parent_id)
values (400, 'ایجاد اسلایدر جدید', 'add_slider', 4);
insert into permission (id, description, name, parent_id)
values (401, 'ویرایش اسلایدر', 'edit_slider', 4);
insert into permission (id, description, name, parent_id)
values (402, 'حذف اسلایدر', 'delete_slider', 4);
insert into permission (id, description, name, parent_id)
values (403, 'لیست اسلایدر', 'list_slider', 4);

insert into permission (id, description, name, parent_id)
values (500, 'ایجاد بلاگ جدید', 'add_blog', 5);
insert into permission (id, description, name, parent_id)
values (501, 'ویرایش بلاگ', 'edit_blog', 5);
insert into permission (id, description, name, parent_id)
values (502, 'حذف بلاگ', 'delete_blog', 5);
insert into permission (id, description, name, parent_id)
values (503, 'لیست بلاگ', 'list_blog', 5);

insert into permission (id, description, name, parent_id)
values (600, 'ایجاد محصول جدید', 'add_product', 6);
insert into permission (id, description, name, parent_id)
values (601, 'ویرایش محصول', 'edit_product', 6);
insert into permission (id, description, name, parent_id)
values (602, 'حذف محصول', 'delete_product', 6);
insert into permission (id, description, name, parent_id)
values (603, 'لیست محصولات', 'list_product', 6);

insert into permission (id, description, name, parent_id)
values (610, 'ایجاد رنگ جدید', 'add_color', 6);
insert into permission (id, description, name, parent_id)
values (611, 'ویرایش رنگ', 'edit_color', 6);
insert into permission (id, description, name, parent_id)
values (612, 'لیست رنگ‌ها', 'list_color', 6);

insert into permission (id, description, name, parent_id)
values (620, 'ایجاد سایز جدید', 'add_size', 6);
insert into permission (id, description, name, parent_id)
values (621, 'ویرایش سایز', 'edit_size', 6);
insert into permission (id, description, name, parent_id)
values (622, 'لیست سایزها', 'list_size', 6);

insert into permission (id, description, name, parent_id)
values (630, 'ایجاد دسته‌بندی محصول جدید', 'add_product_category', 6);
insert into permission (id, description, name, parent_id)
values (631, 'ویرایش دسته‌بندی محصول', 'edit_product_category', 6);
insert into permission (id, description, name, parent_id)
values (632, 'لیست دسته‌بندی محصولات', 'list_product_category', 6);

insert into permission (id, description, name, parent_id)
values (700, 'ایجاد کاربر جدید', 'add_user', 7);
insert into permission (id, description, name, parent_id)
values (701, 'ویرایش کاربر', 'edit_user', 7);
insert into permission (id, description, name, parent_id)
values (702, 'حذف کاربر', 'delete_user', 7);
insert into permission (id, description, name, parent_id)
values (703, 'لیست کاربران', 'list_user', 7);

insert into permission (id, description, name, parent_id)
values (800, 'ایجاد مشتری جدید', 'add_customer', 8);
insert into permission (id, description, name, parent_id)
values (801, 'ویرایش مشتری', 'edit_customer', 8);
insert into permission (id, description, name, parent_id)
values (802, 'حذف مشتری', 'delete_customer', 8);
insert into permission (id, description, name, parent_id)
values (803, 'لیست مشتریان', 'list_customer', 8);

insert into permission (id, description, name, parent_id)
values (900, 'آپلود فایل جدید', 'add_file', 9);
insert into permission (id, description, name, parent_id)
values (901, 'حذف فایل', 'delete_file', 9);
insert into permission (id, description, name, parent_id)
values (902, 'لیست فایل‌ها', 'list_file', 9);

insert into permission (id, description, name, parent_id)
values (1000, 'لیست سفارشات', 'list_invoice', 10);
insert into permission (id, description, name, parent_id)
values (1001, 'مشاهده سفارش', 'info_invoice', 10);

insert into permission (id, description, name, parent_id)
values (1100, 'لیست فاکتورها', 'list_my_invoice', 10);
insert into permission (id, description, name, parent_id)
values (1101, 'مشاهده فاکتور', 'info_my_invoice', 10);
insert into permission (id, description, name, parent_id)
values (1102, 'ویرایش اطلاعات کاربری', 'edit_my_user', 10);