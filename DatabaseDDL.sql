create schema bookstrack collate utf8mb4_0900_ai_ci;

create table books
(
    id int auto_increment
        primary key,
    title varchar(50) null,
    author varchar(50) null,
    isbn varchar(30) null,
    pages int null,
    rating int null
);

create table comment
(
    id int auto_increment
        primary key,
    comment_body varchar(500) null,
    book_id int null,
    date_creation datetime null,
    date_update datetime null,
    author varchar(50) null

);



