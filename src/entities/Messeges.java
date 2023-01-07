package entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Messeges {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "reports_id", nullable = false)
    private int reportsId;
    @Basic
    @Column(name = "date", nullable = false)
    private Timestamp date;
    @Basic
    @Column(name = "sender", nullable = false)
    private int sender;
    @Basic
    @Column(name = "message", nullable = false, length = -1)
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReportsId() {
        return reportsId;
    }

    public void setReportsId(int reportsId) {
        this.reportsId = reportsId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Messeges{" +
                "id=" + id +
                ", reportsId=" + reportsId +
                ", date=" + date +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                '}';
    }
}
