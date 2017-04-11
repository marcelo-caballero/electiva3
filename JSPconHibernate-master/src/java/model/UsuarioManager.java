/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import java.util.*;
import org.hibernate.Criteria;
/**
 *
 * @author Acer
 */
public class UsuarioManager {
    Session session = null;
    public String mess = "";
    int maxResults = 100;
    
     public UsuarioManager()
    {
        this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }
     
    public Usuario getUsuario(String cuenta, String contraseña)
    {
        Usuario usu = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            Query q = session.createQuery("from Usuario as usuario where usuario.cuenta=" + "'"+cuenta+"' and usuario.contrasena= '"+contraseña+"'");
            usu = (Usuario) q.uniqueResult();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }

        return usu;
    }
    
    public Usuario getUsuario(String cuenta)
    {
        Usuario usu = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            Query q = session.createQuery("from Usuario as usuario where usuario.cuenta=" + "'"+cuenta+"'");
            usu = (Usuario) q.uniqueResult();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }

        return usu;
    }
    
    public List getUsuarios()
    {
        List<Usuario> usuarioList = null;

        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        try {
            Query q = session.createQuery ("from Usuario usuario order by usuario.cuenta");
            usuarioList = (List<Usuario>) q.list();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return usuarioList;
    }

    public boolean saveUsuario(Usuario usuario) {
        
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            session.saveOrUpdate(usuario);
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteUsuario(Usuario usuario) {
        
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        try
        {
            session.delete(usuario);
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Usuario> searchUsuarios(Usuario usuarioSample) {
        
        List<Usuario> usuarioList = null;
        
        if(!session.isOpen())
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();

        org.hibernate.Transaction tx = session.beginTransaction();
        
        Criteria crit = session.createCriteria(Usuario.class);
        
        Usuario usuario = new Usuario();
        usuario.setCuenta(usuarioSample.getCuenta());
        
        Example example = Example.create(usuario).ignoreCase().excludeZeroes().enableLike(MatchMode.ANYWHERE);
        crit.add(example);
       
        try {
            usuarioList = (List<Usuario>) crit.list();
            tx.commit();
        }
        catch (HibernateException ex) {
            mess = ex.getMessage();
            tx.rollback();
            ex.printStackTrace();
        }
        return usuarioList;
    }
    
}
