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
					.addAnnotatedClass(Puntaje.class)
					.addAnnotatedClass(Usuario.class)
					.buildSessionFactory();
		}
		return factory;
	}
}
