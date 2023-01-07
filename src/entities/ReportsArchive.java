package entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reports_archive", schema = "projectdb")
public class ReportsArchive {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Basic
    @Column(name = "worker_id", nullable = false)
    private int workerId;
    @Basic
    @Column(name = "status", nullable = true, length = -1)
    private String status;
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
    @Basic
    @Column(name = "title", nullable = false, length = -1)
    private String title;

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

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ReportsArchive{" +
                "id=" + id +
                ", userId=" + userId +
                ", workerId=" + workerId +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", postDate=" + postDate +
                ", startDate=" + startDate +
                ", closeDate=" + closeDate +
                ", priority=" + priority +
                ", title='" + title + '\'' +
                '}';
    }
}
