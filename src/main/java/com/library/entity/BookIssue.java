package com.library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_issues")
public class BookIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public BookIssue() {}

    public BookIssue(Book book, Member member, LocalDate issueDate) {
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    @Override
    public String toString() {
        return "BookIssue{id=" + id + ", book=" + book.getTitle() +
                ", member=" + member.getName() + ", issueDate=" + issueDate +
                ", returnDate=" + returnDate + "}";
    }
}