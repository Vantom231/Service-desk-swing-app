package dao;

import entities.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.List;

public class UsersDAO {
    public static List<Users> getAllUsers(){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Users> temp = null;

        try{
            Query query = session.createQuery("FROM Users");
            temp = query.list();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }

        return temp;
    }
    public static String getUserNameByID(int UserID){
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Users> temp = null;

        try{
            Query query = session.createQuery("FROM Users WHERE id = :ID");
            query.setParameter("ID",UserID);
            temp = query.list();
            return temp.get(0).getUsername();
        }catch(HibernateException e){
            System.err.println(e);
        }finally{
            session.close();
        }

        return null;
    }
    public static boolean addUser(String username , String password, int account_lvl , String email , int status){
        Session session = HibernateUtils.getSessionFactory().openSession();
        Users temp = new Users();

        temp.setUsername(username);
        temp.setPassword(password);
        temp.setAccountLvl(account_lvl);
        temp.setEmail(email);
        temp.setStatus(status);

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
    public static Users checkLogin(String login,String password){
        Session session = HibernateUtils.getSessionFactory().openSession();
        try{
            Query query = session.createQuery("FROM Users s WHERE s.username = :log AND s.password = :pw");
            query.setParameter("log",login);
            query.setParameter("pw",password);
            return (Users) query.list().get(0);
        }catch(HibernateException e){
            System.err.println(e);
            return null;
        }catch(Exception a){
            throw(a);
        }finally{
            session.close();
        }
    }
}
