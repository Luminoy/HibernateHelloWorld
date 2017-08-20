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
		
		Session session1 = null;
		Session session2 = null;
		try {
			session1 = factory.openSession();
			session2 = factory.openSession();
			
			//User user2 = (User) session2.load(User.class, 102);
			Transaction trs1 = session1.getTransaction();
			Transaction trs2 = session2.getTransaction();
			
			trs1.begin();
			User user1 = (User) session1.load(User.class, 101);
			User user2 = (User) session2.load(User.class, 101);
			user1.setName("XXXX111");
			System.out.println("trs1 version: "+user1.getVersion());
			
			
			trs2.begin();
			
			user2.setName("YYYYY222");
			System.out.println("trs2 version: "+user2.getVersion());
			
			trs1.commit();
			trs2.commit();
			

			session1.close();
			session2.close();
			
			System.out.println("成功！");
		} catch(Exception e) {
			System.out.println("发生错误！");
			session1.getTransaction().rollback();
			session2.getTransaction().rollback();
		} finally {
			if(session1 != null && session1.isOpen()) {
				session1.close();
			}
			if(session2 != null && session2.isOpen()) {
				session2.close();
			}
		}
	}
}
