package entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Reports {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Basic
    @Column(name = "worker_id", nullable = true)
    private Integer workerId;
    @Basic
    @Column(name = "title", nullable = false, length = -1)
    private String title;
    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;
    @Basic
    @Column(name = "category", nullable = false, length = -1)
    private String category;
    @Basic
    @Column(name = "post_date", nullable = true)
    private Timestamp postDate;
    @Basic
    @Column(name = "start_date", nullable = true)
    private Timestamp startDate;
    @Basic
    @Column(name = "close_date", nullable = true)
    private Timestamp closeDate;
    @Basic
    @Column(name = "priority", nullable = false)
    private int priority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Reports{" +
                "id=" + id +
                ", userId=" + userId +
                ", workerId=" + workerId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", category='" + category + '\'' +
                ", postDate=" + postDate +
                ", startDate=" + startDate +
                ", closeDate=" + closeDate +
                ", priority=" + priority +
                '}';
    }
}
