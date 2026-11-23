package com.library.main;

import com.library.dao.BookDAO;
import com.library.dao.BookIssueDAO;
import com.library.dao.MemberDAO;
import com.library.entity.Book;
import com.library.entity.Member;
import com.library.util.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        MemberDAO memberDAO = new MemberDAO();
        BookIssueDAO issueDAO = new BookIssueDAO();

        // ISBN and email are unique columns, so add a per-run suffix to keep the demo re-runnable.
        String runId = Long.toString(System.currentTimeMillis(), 36);

        Long book1Id = bookDAO.save(new Book("Clean Code", "Robert C. Martin", "ISBN001-" + runId));
        Long book2Id = bookDAO.save(new Book("Effective Java", "Joshua Bloch", "ISBN002-" + runId));
        System.out.println("Books added.");

        Long memberId = memberDAO.save(new Member("Harry Patil", "harry+" + runId + "@example.com"));
        System.out.println("Member added.");

        System.out.println("\n--- All Books ---");
        bookDAO.findAll().forEach(System.out::println);

        Long issueId = issueDAO.issueBook(book1Id, memberId);
        System.out.println("\nBook issued. Issue ID: " + issueId);

        System.out.println("\n--- Books After Issue ---");
        bookDAO.findAll().forEach(System.out::println);

        Book book2 = bookDAO.findById(book2Id);
        book2.setTitle("Effective Java (3rd Edition)");
        bookDAO.update(book2);
        System.out.println("\nBook updated: " + bookDAO.findById(book2Id));

        issueDAO.returnBook(issueId);
        System.out.println("\nBook returned. Availability restored.");

        System.out.println("\n--- All Issue Records ---");
        issueDAO.findAll().forEach(System.out::println);

        bookDAO.delete(book2Id);
        System.out.println("\nBook deleted: id=" + book2Id);

        HibernateUtil.shutdown();
    }
}