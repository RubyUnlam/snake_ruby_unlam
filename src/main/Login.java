package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class Login extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNombreUsuario;
	private JPasswordField txtContrasenia;
	private JLabel lblErrorRegistro;
	private boolean loggeado = false;
	/**
	 * Create the panel.
	 */
	public Login(Menu menu) {
		getContentPane().setLayout(null);
		setVisible(true);
		setBounds(0, 0, 330, 230);
		setLocationRelativeTo(menu);

		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(158, 60, 140, 26);
		getContentPane().add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(158, 87, 140, 26);
		getContentPane().add(txtContrasenia);
		txtContrasenia.setColumns(10);

		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorRegistro.setVisible(false);
				if(txtNombreUsuario.getText().isEmpty() || txtContrasenia.getPassword().length == 0) {
					lblErrorRegistro.setText("Rellene todos los campos");
					lblErrorRegistro.setVisible(true);
				} else if (registrarUsuario(txtNombreUsuario.getText(), txtContrasenia.getText())) {
					menu.loggeado(txtNombreUsuario.getText());
					dispose();
				}
			}
		});
		btnRegistrarse.setBounds(27, 120, 117, 29);
		getContentPane().add(btnRegistrarse);

		JButton btnIniciarSesion = new JButton("Mandale Mecha");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorRegistro.setVisible(false);
				if(txtNombreUsuario.getText().isEmpty() || txtContrasenia.getPassword().length == 0) {
					lblErrorRegistro.setText("Rellene todos los campos");
					lblErrorRegistro.setVisible(true);
				} else if (iniciarSesion(txtNombreUsuario.getText(), txtContrasenia.getText())) {
					menu.loggeado(txtNombreUsuario.getText());
					dispose();
				}
			}
		});
		btnIniciarSesion.setBounds(158, 120, 140, 29);
		getContentPane().add(btnIniciarSesion);

		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario");
		lblNombreUsuario.setBounds(27, 65, 140, 16);
		getContentPane().add(lblNombreUsuario);

		JLabel lblContrasenia = new JLabel("Contraseña");
		lblContrasenia.setBounds(27, 92, 117, 16);
		getContentPane().add(lblContrasenia);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(142, 6, 61, 16);
		getContentPane().add(lblLogin);

		lblErrorRegistro = new JLabel("Error al registrar");
		lblErrorRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorRegistro.setBounds(6, 32, 318, 16);
		lblErrorRegistro.setVisible(false);
		lblErrorRegistro.setForeground(Color.RED);
		getContentPane().add(lblErrorRegistro);


	}

	public boolean registrarUsuario(String nombreUsuario, String contrasenia) {
		Sesion sesion = new Sesion(nombreUsuario, contrasenia);
		boolean registroCorrectamente = sesion.registrarUsuario();
		if (!registroCorrectamente) {
			lblErrorRegistro.setVisible(true);
		}
		return registroCorrectamente;
	}

	public boolean iniciarSesion(String nombreUsuario, String contrasenia) {
		Sesion sesion = new Sesion(nombreUsuario, contrasenia);
		boolean inicioCorrectamente = sesion.iniciarSesion();
		if (!inicioCorrectamente) {
			lblErrorRegistro.setText("Error al loggear");
			lblErrorRegistro.setVisible(true);
		}
		return inicioCorrectamente;
	}

	public boolean isLoggeado() {
		return loggeado;
	}

	public void setLoggeado(boolean loggeado) {
		this.loggeado = loggeado;
	}
}
