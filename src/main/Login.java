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

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombreUsuario;
	private JPasswordField txtContrasenia;
	private JLabel lblErrorRegistro;
	private JButton btnIniciarSesion;
	private Menu ventanaMenu;
	private boolean loggeado = false;
	Login login = this;
	
	/**
	 * Cuadro de dialogo para el inicio de sesion o registro de usuarios. 
	 * @param menu para poder acceder a los componentes del JFrame principal.
	 */
	public Login(Menu menu) {
		ventanaMenu = menu;
		
		// Propiedades del JDialog para el login.
		getContentPane().setLayout(null);
		setVisible(true);
		setBounds(0, 0, 330, 190);
		setLocationRelativeTo(menu);
		
		// Cuadro de texto para el nombre del usuario.
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(158, 47, 149, 26);
		txtNombreUsuario.setColumns(10);
		
		// Cuadro de texto para la contrasenia.
		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(158, 74, 149, 26);
		txtContrasenia.setColumns(10);

		// Botón de registro.
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setBounds(27, 120, 117, 29);
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				actionRegistrar();
				new Registro(login);
				
			}
		});
		
		// Botón de inicio de sesion.
		btnIniciarSesion = new JButton("Mandale Mecha");
		btnIniciarSesion.setBounds(168, 120, 140, 29);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionIniciarSesion();
			}
		});
		
		// Labels nombre de usuario, contrasenia y login
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario");
		lblNombreUsuario.setBounds(27, 52, 140, 16);

		JLabel lblContrasenia = new JLabel("Contraseña");
		lblContrasenia.setBounds(27, 79, 117, 16);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(6, 6, 318, 16);
		
		// Label para mostrar errores al registrarse/iniciar sesion
		lblErrorRegistro = new JLabel();
		lblErrorRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorRegistro.setBounds(6, 32, 318, 16);
		lblErrorRegistro.setVisible(false);
		lblErrorRegistro.setForeground(Color.RED);
		
		// Agregando componentes al panel.
		getContentPane().add(txtNombreUsuario);
		getContentPane().add(txtContrasenia);
		getContentPane().add(lblErrorRegistro);
		getContentPane().add(btnRegistrarse);
		getContentPane().add(btnIniciarSesion);
		getContentPane().add(lblNombreUsuario);
		getContentPane().add(lblContrasenia);
		getContentPane().add(lblLogin);
		
		iniciarSesionConEnter();
		
	}

	/**
	 * Llama al proceso de inicio de sesion, y muestra un error en caso de fallar. 
	 * @param nombreUsuario
	 * @param contrasenia
	 * @return verdadero o falso segun el exito del inicio de sesion.
	 */
	public boolean iniciarSesion(String nombreUsuario, char[] contrasenia) {
		Sesion sesion = new Sesion(nombreUsuario, contrasenia);
		boolean inicioCorrectamente = sesion.iniciarSesion();
		if (!inicioCorrectamente) {
			lblErrorRegistro.setText("Error al loggear. Verifique los datos");
			lblErrorRegistro.setVisible(true);
		}
		return inicioCorrectamente;
	}
	
	/**
	 * Inicia la validacion del usuario y la contrasenia, y llama al proceso de inicio de sesion. Cierra el dialogo 
	 * al iniciar correctamente la sesion.
	 */
	private void actionIniciarSesion() {
		if (!camposLoginVacios() && iniciarSesion(txtNombreUsuario.getText(), txtContrasenia.getPassword())) {
			cerrarDialogo();
		}
	}
	
	/**
	 * Valida que los campos de nombre de usuario y contrasenia no esten vacios.
	 * @return
	 */
	private boolean camposLoginVacios() {
		if(txtNombreUsuario.getText().isEmpty() || txtContrasenia.getPassword().length == 0) {
			lblErrorRegistro.setText("Rellene todos los campos");
			lblErrorRegistro.setVisible(true);
			return true;
		}
		return false;
	}
	
	/**
	 * Cierra la ventana del login y cambia el texto de bienvenida del menu principal.
	 */
	private void cerrarDialogo() {
		ventanaMenu.loggeado(txtNombreUsuario.getText());
		dispose();
	}
	
	/**
	 * Ejecuta el inicio de sesion al pulsar la tecla Enter en los JTextField.
	 */
	private void iniciarSesionConEnter() {
		txtContrasenia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnIniciarSesion.doClick();					
				}
			}
		});
		txtNombreUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnIniciarSesion.doClick();					
				}			
			}
		});
	}
	
	public boolean isLoggeado() {
		return loggeado;
	}

	public void setLoggeado(boolean loggeado) {
		this.loggeado = loggeado;
	}
}
