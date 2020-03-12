package wl.books.track.bookstrack.repository;

import org.springframework.stereotype.Service;
import wl.books.track.bookstrack.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

@Service
public class CustomCommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private volatile CriteriaBuilder cb;
    private volatile Metamodel m;
    private volatile EntityType<Comment> comment_;
    private volatile CriteriaQuery<Comment> cq;

    private volatile Root<Comment> commentRoot;


    CriteriaBuilder getCb() {
        CriteriaBuilder result = cb;
        if (result == null) {
            synchronized (this) {
                result = cb;
                if (result == null) {
                    cb = result = entityManager.getCriteriaBuilder();
                }
            }
        }
        return result;
    }


    Metamodel getM() {
        Metamodel result = m;
        if (result == null) {
            synchronized (this) {
                result = m;
                if (result == null) {
                    m = result = entityManager.getMetamodel();
                }
            }
        }
        return result;
    }


    EntityType<Comment> getComment_() {
        EntityType<Comment> result = comment_;
        if (result == null) {
            synchronized (this) {
                result = comment_;
                if (result == null) {
                    result = comment_ = m.entity(Comment.class);
                }
            }
        }
        return result;
    }


    CriteriaQuery<Comment> getCq() {
        CriteriaQuery<Comment> result = cq;
        if (result == null) {
            synchronized (this) {
                result = cq;
                if (result == null) {
                    result = cq = cb.createQuery(Comment.class);
                }
            }
        }
        return result;
    }


    Root<Comment> getCommentRoot() {
        Root<Comment> result = commentRoot;
        if (result == null) {
            synchronized (this) {
                result = commentRoot;
                if (result == null) {
                    result = commentRoot = cq.from(Comment.class);
                }
            }
        }
        return result;
    }

    private void initialize() {
        cb = getCb();
        m = getM();
        cq = getCq();
    }


    public List<Comment> getCommentsByBookId(Integer id){
        initialize();
        comment_ = getComment_();
        commentRoot = getCommentRoot();
        cq.select(commentRoot).where(cb.equal(commentRoot.get("bookId"), id));
        cq.orderBy(cb.desc(commentRoot.get("dateCreation")));
        TypedQuery<Comment> typedQuery = entityManager.createQuery(cq);
        typedQuery.setMaxResults(5);
        final List<Comment> resultList = typedQuery.getResultList();
        return  resultList;

    }





}
