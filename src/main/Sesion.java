package main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Sesion {

	private static Session session;
	private static Transaction tx;

	public static boolean registrarUsuario(String nombreUsuario, String contrasenia, String email) {
		session = SesionSingleton.getSessionFactory().openSession();
		Usuario usuario = new Usuario(nombreUsuario, contrasenia, email);
		try {
			if (nombreUsuarioValido(nombreUsuario)) {
				return registrarUsuario(usuario);
			}
		} catch (HibernateException e) {
			return false;
		} finally {
			session.close();
		}
		return false;
	}

	public static boolean iniciarSesion(String nombreUsuario, String contrasenia) {
		session = SesionSingleton.getSessionFactory().openSession();
		Usuario usuario = new Usuario(nombreUsuario, contrasenia);
		try {
			return iniciarSesion(usuario);
		} catch (HibernateException e) {
			return false;
		} finally {
			session.close();
		}
	}

	public static boolean iniciarSesion(Usuario usuario) {

		boolean existeCuenta = true;
		
		//TODO: cambiar por logger.
		System.out.println("Validando inicio de sesión: " + usuario.getNombreUsuario());

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		cq.from(Usuario.class);
		Root<Usuario> ru = cq.from(Usuario.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (usuario.getNombreUsuario() == null || !usuario.getNombreUsuario().isEmpty()) {
			predicates.add(cb.like(ru.get("nombreUsuario"), usuario.getNombreUsuario()));
		}

		if (usuario.getContrasenia() == null || !usuario.getContrasenia().isEmpty()) {
			predicates.add(cb.like(ru.get("contrasenia"), usuario.getContrasenia()));
		}

		cq.select(ru).where(predicates.toArray(new Predicate[] {}));

		try {
			session.createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
			existeCuenta = false;
		}

		return existeCuenta;

	}

	public static boolean nombreUsuarioValido(String nombreUsuario) {

		boolean nombreValido = false;
		//TODO: cambiar por logger.
		System.out.println("Verificando existencia de nombre de usuario: " + nombreUsuario);

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		cq.from(Usuario.class);
		Root<Usuario> ru = cq.from(Usuario.class);
		cq.select(ru).where(cb.like(ru.get("nombreUsuario"), nombreUsuario));

		try {
			session.createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
			//TODO: cambiar por logger.
			System.out.println("El nombre de usuario está disponible para ser registrado");
			nombreValido = true;
		}

		return nombreValido;
	}

	public static boolean registrarUsuario(Usuario usuario) {
		tx = session.beginTransaction();
		session.saveOrUpdate(usuario);
		tx.commit();
		return true;
	}

}
