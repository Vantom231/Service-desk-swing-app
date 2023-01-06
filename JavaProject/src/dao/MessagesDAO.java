package dao;

import entities.Messeges;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class MessagesDAO {

    public static List<Messeges> getAllMessages() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Messeges> temp = null;

        try{
            Query query = session.createQuery("From Messeges ");
            temp = query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }
        finally{
            session.close();
        }
        return temp;
    }
    public static List<Messeges> getAllMessagesByReportID(int reportID){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Messeges> temp = null;

        try{
            Query query = session.createQuery("From Messeges Where reportsId = :rid");
            query.setParameter("rid",reportID);
            temp = query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }
        finally{
            session.close();
        }
        return temp;
    }

    public static boolean addMessage(int reports_id,int sender,String message){
        Session session = HibernateUtils.getSessionFactory().openSession();
        Messeges temp = new Messeges();

        temp.setReportsId(reports_id);
        temp.setDate(Timestamp.from(Instant.now()));
        temp.setSender(sender);
        temp.setMessage(message);

        try{
            session.getTransaction().begin();
            session.persist(temp);
            session.getTransaction().commit();
        }catch(HibernateException e){
            //System.err.println(e);
            return false;
        }finally{
            session.close();
        }
        return true;
    }
}
