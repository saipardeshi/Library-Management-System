package com.library.dao;

import com.library.entity.Book;
import com.library.entity.BookIssue;
import com.library.entity.Member;
import com.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class BookIssueDAO {

    public Long issueBook(Long bookId, Long memberId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Book book = session.get(Book.class, bookId);
            Member member = session.get(Member.class, memberId);

            if (book == null || member == null) {
                throw new IllegalArgumentException("Book or Member not found");
            }
            if (!book.isAvailable()) {
                throw new IllegalStateException("Book is already issued");
            }

            book.setAvailable(false);
            session.merge(book);

            BookIssue issue = new BookIssue(book, member, LocalDate.now());
            session.persist(issue);

            tx.commit();
            return issue.getId();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void returnBook(Long issueId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            BookIssue issue = session.get(BookIssue.class, issueId);
            if (issue == null) throw new IllegalArgumentException("Issue record not found");

            issue.setReturnDate(LocalDate.now());
            issue.getBook().setAvailable(true);

            session.merge(issue);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<BookIssue> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM BookIssue", BookIssue.class).list();
        }
    }
}