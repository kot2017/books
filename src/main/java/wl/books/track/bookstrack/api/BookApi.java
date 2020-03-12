package wl.books.track.bookstrack.api;

import java.util.List;

public class BookApi {

    private String title;
    private String author;
    private String isbn;
    private int pages;
    private int rating;
    private List<CommentApi> commentsList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<CommentApi> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CommentApi> commentsList) {
        this.commentsList = commentsList;
    }
}
