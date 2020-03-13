package wl.books.track.bookstrack.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wl.books.track.bookstrack.api.BookApi;
import wl.books.track.bookstrack.api.CommentApi;
import wl.books.track.bookstrack.controllers.BookController;
import wl.books.track.bookstrack.entity.Books;
import wl.books.track.bookstrack.entity.Comment;
import wl.books.track.bookstrack.repository.BooksRepository;
import wl.books.track.bookstrack.repository.CommentRepository;
import wl.books.track.bookstrack.repository.CustomCommentRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    CustomCommentRepository customCommentRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookController bookController;


    public List<BookApi> getAllBooks(Integer page, Integer limit, String sortBy) {
        Pageable paging = PageRequest.of(page, limit, Sort.unsorted());

        Page<Books> pageBooks = booksRepository.findAll(paging);
        int total = pageBooks.getTotalPages();
        System.out.println("======> pageBook: " + total);
        List<BookApi> bookApiList = new ArrayList<>();

        List<Books> list = pageBooks.getContent();

        for (Books books : list
        ) {
            BookApi book = convertBooks(books, total);
            bookApiList.add(book);
        }
        return bookApiList;

    }


    private BookApi convertBooks(Books books, Integer total){
        BookApi book = new BookApi();
        book.setId(books.getId());
        if (books.getAuthor() != null) {
            book.setAuthor(books.getAuthor());
        }
        if (books.getTitle() != null) {
            book.setTitle(books.getTitle());
        }
        if (books.getPages() != null) {
            book.setPages(books.getPages());
        }
        if (books.getIsbn() != null) {
            book.setIsbn(books.getIsbn());
        }
        if (books.getRating() != null) {
            book.setRating(books.getRating());
        }
        if(total != null){
            book.setTotalPages(total);
        }
        return book;
    }

    public BookApi convertBookAndComment(Books books) {
        BookApi book = convertBooks(books, null);

        List<Comment> commentList = customCommentRepository.getCommentsByBookId(books.getId());
        if (commentList != null && !commentList.isEmpty()) {
            List<CommentApi> commentApiList = new ArrayList<>();

            for (Comment comment : commentList
            ) {
                CommentApi commentApi = new CommentApi();
                commentApi.setAuthor(comment.getAuthor());
                commentApi.setBody(comment.getBody());
                commentApi.setDate(comment.getDateCreation());
                commentApiList.add(commentApi);
            }
            book.setCommentsList(commentApiList);
        }
        return book;
    }


    public void saveComment(CommentApi commentApi, Integer id) {
        Comment comment = new Comment();
        comment.setAuthor(commentApi.getAuthor());
        comment.setBody(commentApi.getBody());
        // Optional<Books> books = booksRepository.findById(id);
        //   comment.setBooksByBookId( books.get());
        //  comment.setBooksByBookId(id);
        comment.setBookId(id);
        comment.setDateCreation(new Timestamp(System.currentTimeMillis()));
        comment.setUpdate(new Timestamp(System.currentTimeMillis()));

        System.out.println("=======>>  comment: " + comment.toString());

        commentRepository.save(comment);

    }


    public void saveBook(BookApi bookApi) {
        Books books = new Books();
        books.setAuthor(bookApi.getAuthor());
        books.setTitle(bookApi.getTitle());
        books.setIsbn(bookApi.getIsbn());
        books.setPages(bookApi.getPages());
        books.setRating(bookApi.getRating());

        booksRepository.save(books);

    }


}
