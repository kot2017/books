package wl.books.track.bookstrack.repository;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wl.books.track.bookstrack.entity.Books;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
 @Transactional
public class BookSearch {


    @PersistenceContext
    private EntityManager entityManager;


    public List search(String text){

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Books.class).get();

        Query query = queryBuilder.keyword().onFields("title", "author", "isbn").matching(text).createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Books.class);

        @SuppressWarnings("unchecked")
        List result = jpaQuery.getResultList();

        return result;

    }

}
