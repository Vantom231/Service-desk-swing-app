package dao;

import entities.MessagesArchive;
import entities.Messeges;
import entities.Reports;
import entities.ReportsArchive;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ReportsDao {


    public static List<Reports> getAllReports(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Reports> temp = null;

        try{
            Query query = session.createQuery("FROM Reports ");
            temp = query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }

        return temp;
    }
    public static List<Reports> getOpenReports(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Reports> temp = null;

        try{
            Query query = session.createQuery("FROM Reports where workerId = null");
            temp = query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }

        return temp;
    }
    public static List<Reports> getReportsByUserID(int UserId){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Reports> temp = null;

        try{
            Query query = session.createQuery("From Reports where userId = :uid ");
            query.setParameter("uid",UserId);
            return query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }
        return temp;
    }
    public static List<Reports> getReportsByWorkerID(int WorkerId){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Reports> temp = null;

        try{
            Query query = session.createQuery("From Reports where workerId = :wid");
            query.setParameter("wid",WorkerId);
            return query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally {
            session.close();
        }
        return temp;
    }
    public static boolean addReport(int userID,String title, int status, String category, int priority){
        Session session = HibernateUtils.getSessionFactory().openSession();
        Reports temp = new Reports();

        Timestamp time = Timestamp.from(Instant.now());
        temp.setUserId(userID);
//        temp.setWorkerId(workerID);
        temp.setTitle(title);
        temp.setStatus(status);
        temp.setCategory(category);
        temp.setPriority(priority);
        temp.setPostDate(time);
        temp.setStartDate(null);
        temp.setCloseDate(null);

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
    public static void archive(Reports report){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Messeges> messageList = MessagesDAO.getAllMessagesByReportID(report.getId());
        if( report.getStartDate() == null){
        }else{
            ReportsArchiveDAO.addReport(report.getUserId(),report.getWorkerId(),report.getTitle(),"0",report.getCategory(),report.getPostDate(),report.getStartDate(),report.getPriority());
            int lastID = HibernateUtils.getLastID();
            for (Messeges messeges : messageList) {
                MessagesArchiveDAO.addMessage(lastID,messeges.getDate(), messeges.getSender(), messeges.getMessage());
            }
        }


        try{
            session.getTransaction().begin();
            for (Messeges messeges : messageList) {
                session.remove(messeges);
            }
            session.remove(report);
            session.getTransaction().commit();

        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }

    }
    public static void takeForWorker(Reports report){
        Session session = HibernateUtils.getSessionFactory().openSession();
        report.setStartDate(Timestamp.from(Instant.now()));

        try{
            session.getTransaction().begin();
            session.update(report);
            session.getTransaction().commit();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }
    }
}
