package com.fabien_astiasaran.ori_massages_api.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "t_messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(name = "msg_date")
    private LocalDateTime msgDate = LocalDateTime.now();

    @Column(name = "content")
    private String content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(author, message.author) && Objects.equals(msgDate, message.msgDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, msgDate);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user=" + author +
                ", date=" + msgDate +
                ", content='[PROTECTED]" +
                '}';
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public LocalDateTime getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(LocalDateTime msgDate) {
        this.msgDate = msgDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
