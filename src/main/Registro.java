package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Registro extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField txtNombreUsuario;
	private JPasswordField txtContrasenia;
	private JLabel lblInformativo;
	private JTextField txtEmail;
	private JButton btnRegistrarse;
	private Login ventanaLogin;

	/**
	 * Cuadro de dialogo para el registro de usuarios.
	 * 
	 * @param login para poder acceder a los componentes del Login.
	 */
	public Registro(Login login) {
		ventanaLogin = login;
		// Propiedades del JDialog para el registro.
		setTitle("Registro");
		getContentPane().setLayout(null);
		setVisible(true);
		setBounds(0, 0, 350, 200);
		setLocationRelativeTo(login);

		// Cuadro de texto para el nombre del usuario.
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(158, 35, 149, 26);
		txtNombreUsuario.setEnabled(true);

		// Cuadro de texto para la contrasenia.
		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(158, 62, 149, 26);
		txtContrasenia.setEnabled(true);

		// Cuadro de texto para el email.
		txtEmail = new JTextField();
		txtEmail.setBounds(158, 92, 149, 26);

		JLabel lblTitulo = new JLabel("Registrar usuario");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(6, 4, 318, 16);

		// Labels nombre de usuario, contrasenia y login
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario");
		lblNombreUsuario.setBounds(27, 40, 140, 16);

		JLabel lblContrasenia = new JLabel("Password");
		lblContrasenia.setBounds(27, 67, 117, 16);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(27, 97, 117, 16);

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

		// Agregando componentes al panel.
		getContentPane().add(txtNombreUsuario);
		getContentPane().add(txtContrasenia);
		getContentPane().add(lblInformativo);
		getContentPane().add(txtEmail);
		getContentPane().add(btnRegistrarse);
		getContentPane().add(lblNombreUsuario);
		getContentPane().add(lblContrasenia);
		getContentPane().add(lblTitulo);
		getContentPane().add(lblEmail);

		registrarseConEnter();
	}
	
	/**
	 * Inicia la validacion del usuario y la contrasenia, y llama al proceso de registro de usuario.
	 */
	private void actionRegistrar() {
		if (!camposRegistroVacios() && registrarUsuario(txtNombreUsuario.getText(), txtContrasenia.getPassword(), txtEmail.getText())) {
			dispose();
			ventanaLogin.actualizarUsuario(txtNombreUsuario.getText());
		}
	}
	
	/**
	 * Valida que los campos de nombre de usuario y contrasenia no esten vacios.
	 * @return
	 */
	private boolean camposRegistroVacios() {
		if(txtNombreUsuario.getText().isEmpty() || txtContrasenia.getPassword().length == 0 || txtEmail.getText().isEmpty()) {
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
		RegistroUsuario registro = Sesion.registrarUsuario(nombreUsuario, String.valueOf(contrasenia), email);
		if (!registro.esRegistroEfectivo()) {
			lblInformativo.setText(registro.getMensaje());
			lblInformativo.setVisible(true);
		}
		return registro.esRegistroEfectivo();
	}
	
	/**
	 * Ejecuta el inicio de sesion al pulsar la tecla Enter en los JTextField.
	 */
	private void registrarseConEnter() {
		txtContrasenia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRegistrarse.doClick();					
				}
			}
		});
		txtNombreUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRegistrarse.doClick();					
				}			
			}
		});
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnRegistrarse.doClick();					
				}			
			}
		});
	}

}
