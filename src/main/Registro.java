package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Registro extends JDialog {

	private static final long serialVersionUID = 1L;

	private Login ventanaLogin;
	private JTextField txtNombreUsuario;
	private JPasswordField txtContrasenia;
	private JLabel lblInformativo;
	private JTextField txtEmail;
	private JButton btnRegistrarse;
	private JButton btnCerrar;

	/**
	 * Cuadro de dialogo para el registro de usuarios.
	 * 
	 * @param login para poder acceder a los componentes del Login.
	 */
	public Registro(Login login) {
		ventanaLogin = login;

		// Propiedades del JDialog para el login.
		getContentPane().setLayout(null);
		setVisible(true);
		setBounds(0, 0, 330, 190);
		setLocationRelativeTo(login);

		// Cuadro de texto para el nombre del usuario.
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(158, 35, 149, 26);
		txtNombreUsuario.setEnabled(true);

		// Cuadro de texto para la contrasenia.
		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(158, 62, 149, 26);
		txtContrasenia.setEnabled(true);

		JLabel lblTitulo = new JLabel("Registrar usuario");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(6, 6, 318, 16);

		// Labels nombre de usuario, contrasenia y login
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario");
		lblNombreUsuario.setBounds(27, 40, 140, 16);

		JLabel lblContrasenia = new JLabel("Contraseña");
		lblContrasenia.setBounds(27, 67, 117, 16);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(27, 97, 117, 16);

		txtEmail = new JTextField();
		txtEmail.setBounds(158, 92, 149, 26);

		// Label para mostrar errores al registrarse/iniciar sesion
		lblInformativo = new JLabel();
		lblInformativo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformativo.setBounds(6, 20, 318, 16);
		lblInformativo.setVisible(false);
		lblInformativo.setForeground(Color.RED);

		// Boton de registro.
		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setHorizontalAlignment(SwingConstants.CENTER);
		btnRegistrarse.setBounds(27, 125, 280, 29);
		btnRegistrarse.setEnabled(true);
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionRegistrar();
			}
		});
		
		// Boton cerrar
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setHorizontalAlignment(SwingConstants.CENTER);
		btnCerrar.setBounds(27, 125, 280, 29);
		btnCerrar.setVisible(false);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarDialogo();
			}
		});

		// Agregando componentes al panel.
		getContentPane().add(txtNombreUsuario);
		getContentPane().add(txtContrasenia);
		getContentPane().add(lblInformativo);
		getContentPane().add(txtEmail);
		getContentPane().add(btnRegistrarse);
		getContentPane().add(btnCerrar);
		getContentPane().add(lblNombreUsuario);
		getContentPane().add(lblContrasenia);
		getContentPane().add(lblTitulo);
		getContentPane().add(lblEmail);

//		iniciarSesionConEnter();
	}
	
	/**
	 * Inicia la validacion del usuario y la contrasenia, y llama al proceso de registro de usuario.
	 */
	private void actionRegistrar() {
		if (!camposRegistroVacios() && registrarUsuario(txtNombreUsuario.getText(), txtContrasenia.getPassword(), txtEmail.getText())) {
			lblInformativo.setText("Se registro el usuario: " + txtNombreUsuario.getText());
			lblInformativo.setVisible(true);
			btnRegistrarse.setVisible(false);
			txtContrasenia.setEnabled(false);
			txtEmail.setEnabled(false);
			txtNombreUsuario.setEnabled(false);
			btnCerrar.setVisible(true);
		}
	}
	
	/**
	 * Valida que los campos de nombre de usuario y contrasenia no esten vacios.
	 * @return
	 */
	private boolean camposRegistroVacios() {
		if(txtNombreUsuario.getText().isEmpty() || txtContrasenia.getPassword().length == 0) {
			lblInformativo.setText("Rellene todos los campos");
			lblInformativo.setVisible(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Llama al proceso de registro del usuario, y muestra un error en caso de fallar. 
	 * @param nombreUsuario
	 * @param contrasenia
	 * @return verdadero o falso segun el exito del registro.
	 */
	public boolean registrarUsuario(String nombreUsuario, char[] contrasenia, String email) {
		Sesion sesion = new Sesion(nombreUsuario, contrasenia, email);
		boolean registroCorrectamente = sesion.registrarUsuario();
		if (!registroCorrectamente) {
			lblInformativo.setText("Error al registrar. Usuario en uso");
			lblInformativo.setVisible(true);
		}
		return registroCorrectamente;
	}
	
	/**
	 * Cierra la ventana del login y cambia el texto de bienvenida del menu principal.
	 */
	private void cerrarDialogo() {
		dispose();
	}

}
