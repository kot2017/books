# books
Java backend API that keeps track of books read by a user. The application allows to:

1.	Add a new book (
title,
 author, 
ISBN number, 
number of pages, 
1-5 rating of how much the reader enjoyed it; 
nice to have:  validate ISBN number)
# http://localhost:8082/bookstrack/book/save  

2. List books entered into the system
# http://localhost:8082/bookstrack/books/all?page=0&limit=5

3. Edit/remove the book entered to the system
# http://localhost:8082/bookstrack/book/1
 

4. The API should respond in a timely fashion even when 10 million books are entered into the system. 
# http://localhost:8082/bookstrack/search/?q=123

5. Add API to add a comment to a book. Every book can have multiple comments. The List books API from point 2. should include latests 5 comments for every book.
# http://localhost:8082/bookstrack/comment/?bookId=1
{"body":" to jest ok...","author":"WL","date":null}
