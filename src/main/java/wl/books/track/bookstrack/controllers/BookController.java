package wl.books.track.bookstrack.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import wl.books.track.bookstrack.api.BookApi;
import wl.books.track.bookstrack.api.CommentApi;
import wl.books.track.bookstrack.entity.Books;
import wl.books.track.bookstrack.repository.BookSearch;
import wl.books.track.bookstrack.repository.BooksRepository;
import wl.books.track.bookstrack.services.BookService;
import wl.books.track.bookstrack.services.BuildSearchIndex;
import wl.books.track.bookstrack.services.ValidationService;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.springframework.http.HttpEntity.EMPTY;

@Controller
@RequestMapping("/bookstrack")
public class BookController {

    @Autowired
    BooksRepository booksRepository;

    @Autowired
    BookService bookService;

    @Autowired
    ValidationService validationService;

    @Autowired
    BookSearch bookSearch;

    @Autowired
    BuildSearchIndex buildSearchIndex;

    @RequestMapping(value = "books/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getAllBooks(@RequestParam("page") String page,
                                         @RequestParam("limit") String limit) {


        boolean valid = validationService.validatePageAndLimit(page, limit);
        if (valid) {
            Integer pageInt = Integer.parseInt(page);
            Integer limitInt = Integer.parseInt(limit);
            try {
                List<BookApi> bookApiList = bookService.getAllBooks(pageInt, limitInt, null);
                return new ResponseEntity<>(bookApiList, HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>("wystąpił bład: " + e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("zła parametry", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "book/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getBook(@PathVariable Integer id) {
        Optional<Books> books = booksRepository.findById(id);
        if (books.isPresent()) {
            BookApi book = bookService.convertBookAndComment(books.get());
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
        return new ResponseEntity<>("Brak danych", HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "book/save", method = RequestMethod.POST)
    public ResponseEntity<?> saveBook(@RequestBody BookApi book) {
        boolean validIsbn = validationService.validateISBN(book.getIsbn());
        boolean validRating = validationService.validateRating(book.getRating());
        if (validIsbn && validRating) {
            bookService.saveBook(book);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>(" Błedny ISBN lub rating", HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "book/delete/{id}")
    public  ResponseEntity<?> deleteBook(@PathVariable Integer id){
        booksRepository.deleteById(id);
        return new ResponseEntity<>(" ", HttpStatus.OK);
    }


    @RequestMapping(value = "comment", method = RequestMethod.POST)
    public ResponseEntity<?> addComment(@RequestBody CommentApi comment, @RequestParam String bookId) {

        boolean valid = validationService.isNumeric(bookId);
        if (valid) {
            Integer idInt = Integer.parseInt(bookId);
            bookService.saveComment(comment, idInt);
            return new ResponseEntity<>("", HttpStatus.OK);
        }
        return new ResponseEntity<>("Błedny identyfikator ksiażki", HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> search(@RequestParam String q, @RequestParam int limit) {
        List searchResult = null;
        try {
            searchResult = bookSearch.search(q, limit);
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(searchResult, HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/build/index", method = RequestMethod.POST)
    public ResponseEntity<?> build() {
        buildSearchIndex.buildIndex();
        return new ResponseEntity<>("", HttpStatus.OK);
    }



}
