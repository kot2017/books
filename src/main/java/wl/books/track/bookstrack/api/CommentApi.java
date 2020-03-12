package wl.books.track.bookstrack.api;

import java.time.LocalDateTime;
import java.util.Date;

public class CommentApi {

    private String body;
    private String author;
    private Date date;  //creation date


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "CommentApi{" +
                "body='" + body + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                '}';
    }
}
