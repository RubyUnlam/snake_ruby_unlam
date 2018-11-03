package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel lblUsuario;
	private JButton btnAbrir;
	private JButton btnJugar;
	private String nombreUsuarioLoggeado;
	
	public Menu() {
		
		setResizable(false);
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblUsuario = new JLabel("Debe iniciar sesion para poder jugar.");
		lblUsuario.setLocation(10, 30);
		lblUsuario.setBounds(6, 45, 398, 14);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblUsuario);
		
		btnAbrir = new JButton("Iniciar Sesion");
		btnAbrir.setBounds(100, 10, 304, 23);
		btnAbrir.setLocation(100, 10);
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirLogin();
			}
		});

		
		btnJugar = new JButton("Jugar");
		btnJugar.setBounds(177, 98, 89, 23);
		btnJugar.setLocation(10, 10);
		btnJugar.setEnabled(false);
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO: Aca se inicia el juego con un solo usuario, hay que ver
				// como se haria con la implementacion de salas.
				Juego.iniciar(nombreUsuarioLoggeado);
			}
		});

		contentPane.add(btnAbrir);
		contentPane.add(btnJugar);
		
		setLocationRelativeTo(null);
	}
	
	private void abrirLogin() {
		new Login(this);
	}
	
	public void loggeado(String texto) {
		nombreUsuarioLoggeado = texto;
		btnJugar.setEnabled(true);
		lblUsuario.setText("Sesión iniciada. Usuario: " + nombreUsuarioLoggeado);
		lblUsuario.setVisible(true);
	}

	public static void main(String[] args) {
		new Menu().setVisible(true);
	}
}
