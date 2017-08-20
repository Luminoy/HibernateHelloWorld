package entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Client {
	public static void main(String[] args) {
		Configuration cfg = new Configuration().configure();
		ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(registry);
		
		Session session = null;
		try {
			session = factory.openSession();
			//Transaction trs = session.beginTransaction();
			session.getTransaction().begin();
			
			User user = new User();
			user.setId(102);
			user.setName("Redick");
			user.setAge(34);
			
			session.save(user);
			//trs.commit();
			session.getTransaction().commit();
			System.out.println("插入记录成功！");
		} catch(Exception e) {
			System.out.println("插入记录发生错误！");
			session.getTransaction().rollback();
		} finally {
			if(session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
