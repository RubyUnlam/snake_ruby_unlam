package main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Sesion {

    private static Session session;
    private static Transaction tx;

    /**
     * Verifica que el usuario y mail no esten en uso, y que este ultimo sea valido.
     * Si alguno no cumpliera con esas condiciones, devuelve una cadena con el
     * mensaje de error.
     *
     * @param nombreUsuario
     * @param contrasenia
     * @param email
     * @return
     */
    public static RegistroUsuario registrarUsuario(String nombreUsuario, String contrasenia, String email) {
        session = SesionSingleton.getSessionFactory().openSession();
        Usuario usuario = new Usuario(nombreUsuario, contrasenia, email);
        RegistroUsuario registroMail = mailUsuarioValido(email);

        if (!registroMail.esRegistroEfectivo()) {
            return registroMail;
        }

        RegistroUsuario registroUsername = nombreUsuarioValido(nombreUsuario);

        if (!registroUsername.esRegistroEfectivo()) {
            return registroUsername;
        }

        registrarUsuario(usuario);
        session.close();
        return registroUsername;
    }

    /**
     * Verifica que el mail no se encuentre en la base de datos, y que contenga al
     * menos un punto y un arroba.
     *
     * @param email
     * @return RegistroUsuario
     */
    private static RegistroUsuario mailUsuarioValido(String email) {

        RegistroUsuario registro = new RegistroUsuario("", false);

        if (!email.contains("@") || !email.contains(".")) {
            registro.setMensaje("El mail debe contener al menos un punto y un arroba");
            return registro;
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        cq.from(Usuario.class);
        Root<Usuario> ru = cq.from(Usuario.class);
        cq.select(ru).where(cb.like(ru.get("email"), email));

        try {
            session.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            registro.setRegistroEfectivo(true);
            return registro;
        }
        registro.setMensaje("El mail se encuentra en uso");
        return registro;
    }

    public static boolean iniciarSesion(Usuario usuario) {
        session = SesionSingleton.getSessionFactory().openSession();

        boolean existeCuenta = true;

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

        cq.select(ru).where(predicates.toArray(new Predicate[]{}));

        try {
            session.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            existeCuenta = false;
        }

        return existeCuenta;

    }

    public static RegistroUsuario nombreUsuarioValido(String nombreUsuario) {

        RegistroUsuario registro = new RegistroUsuario("", false);

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        cq.from(Usuario.class);
        Root<Usuario> ru = cq.from(Usuario.class);
        cq.select(ru).where(cb.like(ru.get("nombreUsuario"), nombreUsuario));

        try {
            session.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            registro.setRegistroEfectivo(true);
            return registro;
        }
        registro.setMensaje("Nombre de usuario en uso.");
        return registro;
    }

    public static boolean registrarUsuario(Usuario usuario) {
        tx = session.beginTransaction();
        session.saveOrUpdate(usuario);
        tx.commit();
        return true;
    }

}
