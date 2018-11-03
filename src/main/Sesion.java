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
import org.hibernate.cfg.Configuration;

public class Sesion {

	private Usuario usuario;
	// TODO: utilizar Singleton para que se instancie una sola vez.
	SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Usuario.class)
			.buildSessionFactory();
	Session session;
	Transaction tx;

	public Sesion(String nombreUsuario, String contrasenia) {
		this.usuario = new Usuario(nombreUsuario, contrasenia);
	}

	public boolean registrarUsuario() {
		session = factory.openSession();
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

		// query itself
		cq.select(ru).where(predicates.toArray(new Predicate[] {}));
		
		Usuario usuarioEncontrado = null;
		try {
			usuarioEncontrado = session.createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
			// TODO: Entra aca si no encuentra usuario. Catchear correctamente.
		}

		if (usuarioEncontrado == null) {
			existeCuenta = false;
		} else {
			System.out.println("Acceso correcto");
			// TODO: Podria implementarse manejo de excepciones para retornar un mensaje de
			// error
			// distinto al label segun la excepcion
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

		Usuario usuarioEncontrado = null;
		try {
			usuarioEncontrado = session.createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
			// TODO: Entra aca si no encuentra usuario. Catchear correctamente.
		}

		if (usuarioEncontrado == null) {
			nombreValido = true;
		} else {
			System.out.println("Nombre de usuario en uso");
			// TODO: Podria implementarse manejo de excepciones para retornar un mensaje de
			// error
			// distinto al label segun la excepcion
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
