package main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SesionSingleton {
	public static SessionFactory factory;

	private SesionSingleton() {
	}

	public static synchronized SessionFactory getSessionFactory() {
		if (factory == null) {
			factory = new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(Usuario.class)
					.addAnnotatedClass(Sala.class)
					.addAnnotatedClass(JugadorSala.class)
					.buildSessionFactory();
		}
		return factory;
	}
}
