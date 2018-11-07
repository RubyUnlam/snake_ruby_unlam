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
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Sesion {

	private Usuario usuario;
	// TODO: Esto podría ejecutarse al iniciar el servidor. 
	private SessionFactory factory = SesionSingleton.getSessionFactory();
	private Session session;
	private Transaction tx;
	public Sesion(String nombreUsuario, char[] contraseniaChar, String email) {
		session = factory.openSession();
		String contrasenia = new String(contraseniaChar);
		this.usuario = new Usuario(nombreUsuario, contrasenia, email);
	}
	public Sesion(String nombreUsuario, char[] contraseniaChar) {
		session = factory.openSession();
		String contrasenia = new String(contraseniaChar);
		this.usuario = new Usuario(nombreUsuario, contrasenia);
	}

	public boolean registrarUsuario() {
		try {
			if (nombreUsuarioValido(usuario)) {
				return registrarUsuario(usuario);
			}
		} catch (HibernateException e) {
			return false;
		} finally {
			session.close();
		}
		return false;
	}

	public boolean iniciarSesion() {
		session = factory.openSession();
		try {
			return iniciarSesion(usuario);
		} catch (HibernateException e) {
			return false;
		} finally {
			session.close();
		}
	}

	public boolean iniciarSesion(Usuario usuario) {

		boolean existeCuenta = true;

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

	public boolean nombreUsuarioValido(Usuario usuario) {

		boolean nombreValido = false;

		System.out.println("Verificando existencia de nombre de usuario: " + usuario.getNombreUsuario());

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
		cq.from(Usuario.class);
		Root<Usuario> ru = cq.from(Usuario.class);
		cq.select(ru).where(cb.like(ru.get("nombreUsuario"), usuario.getNombreUsuario()));

		try {
			session.createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
			System.out.println("El nombre de usuario está disponible para ser registrado");
			nombreValido = true;
		}

		return nombreValido;

	}

	public boolean registrarUsuario(Usuario usuario) {
		tx = session.beginTransaction();
		session.saveOrUpdate(usuario);
		tx.commit();
		return true;
	}

}
