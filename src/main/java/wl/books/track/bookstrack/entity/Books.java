package wl.books.track.bookstrack.entity;

import org.hibernate.search.annotations.*;

import javax.persistence.*;
import javax.persistence.Index;
import java.util.Objects;

import static org.hibernate.search.annotations.Store.NO;

@Entity
@Indexed
public class Books {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private Integer pages;
    private Integer rating;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Fields({
            @Field,
            @Field(name = "sortTitle", analyze = Analyze.YES, store = Store.NO)
    })
    @SortableField(forField = "sortTitle")
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Fields({
            @Field,
            @Field(name = "sortAuthor", analyze = Analyze.YES, store = Store.YES)
    })
    @SortableField(forField = "sortAuthor")
    @Column(name = "author", nullable = true, length = 50)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Fields({
            @Field,
            @Field(name = "sortIsbn", analyze = Analyze.YES, store = Store.YES)
    })
    @SortableField(forField = "sortIsbn")
    @Column(name = "isbn", nullable = true, length = 30)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Field(store = NO)
    @Column(name = "pages", nullable = true)
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Basic
    @Field(store = NO)
    @Column(name = "rating", nullable = true)
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return id == books.id &&
                Objects.equals(title, books.title) &&
                Objects.equals(author, books.author) &&
                Objects.equals(isbn, books.isbn) &&
                Objects.equals(pages, books.pages) &&
                Objects.equals(rating, books.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, pages, rating);
    }
}
