package dao;

import entities.Reports;
import entities.ReportsArchive;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ReportsArchiveDAO {

    public static List<ReportsArchive> getAllReports(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<ReportsArchive> temp = null;

        try{
            Query query = session.createQuery("FROM ReportsArchive ");
            temp = query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }

        return temp;
    }
    public static List<ReportsArchive> getReportsByUserID(int UserId){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<ReportsArchive> temp = null;

        try{
            Query query = session.createQuery("From ReportsArchive where userId = :uid ");
            query.setParameter("uid",UserId);
            return query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }
        return temp;
    }
    public static List<ReportsArchive> getReportsByWorkerID(int WorkerId){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<ReportsArchive> temp = null;

        try{
            Query query = session.createQuery("From ReportsArchive where workerId = :wid");
            query.setParameter("wid",WorkerId);
            return query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally {
            session.close();
        }
        return temp;
    }
    public static boolean addReport(int userID, int workerID, String title, String status, String category, Timestamp post_date, Timestamp start_date,int priority){
        Session session = HibernateUtils.getSessionFactory().openSession();
        ReportsArchive temp = new ReportsArchive();

        Timestamp time = Timestamp.from(Instant.now());
        temp.setUserId(userID);
        temp.setWorkerId(workerID);
        temp.setTitle(title);
        temp.setStatus(status);
        temp.setCategory(category);
        temp.setPriority(priority);
        temp.setPostDate(post_date);
        temp.setStartDate(start_date);
        temp.setCloseDate(time);

        try{
            session.getTransaction().begin();
            session.persist(temp);
            session.getTransaction().commit();
        }catch(HibernateException e){
//            System.err.println(e);
            return false;
        }finally {
            session.close();
        }
        return true;
    }
}
