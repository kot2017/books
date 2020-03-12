package wl.books.track.bookstrack.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import wl.books.track.bookstrack.entity.Books;

public interface BooksRepository extends PagingAndSortingRepository<Books, Integer> {
}
