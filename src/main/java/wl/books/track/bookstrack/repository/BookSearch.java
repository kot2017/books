package wl.books.track.bookstrack.repository;

import org.apache.lucene.search.*;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
 @Transactional
public class BookSearch {


    @PersistenceContext
    private EntityManager entityManager;


    public List search(String text, Integer limit){


        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Books.class).get();

        Query query = queryBuilder.keyword().onFields("title", "author", "isbn").matching(text).createQuery();

        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Books.class);

//----------

        SortField[] sortFields = new SortField[3];

        Sort sort = new Sort();


        sortFields[0] =   new SortField( "sortAuthor" , SortField.Type.STRING ) ;
        sortFields[1] =   new SortField( "sortTitle" , SortField.Type.STRING ) ;
        sortFields[2] =   new SortField( "sortIsbn" , SortField.Type.STRING ) ;



        sort.setSort(sortFields);

        jpaQuery.setSort( sort );

        int size = jpaQuery.getResultSize();

        if(limit !=null && limit<10000){
            jpaQuery.setMaxResults(limit);
        }else{
            jpaQuery.setMaxResults(1000);
        }



        List result   = jpaQuery.getResultList();


        return result;

    }

}
