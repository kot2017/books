package wl.books.track.bookstrack.repository;

import org.springframework.data.repository.CrudRepository;
import wl.books.track.bookstrack.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    public Comment findCommentByBookId(Integer id);

}
