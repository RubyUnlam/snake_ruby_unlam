package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel lblUsuario;
	private JButton btnIniciarSesion;
	private JButton btnJugar;
	private JPanel pnlSalas;
	private JList lstSalas;
	private JLabel lblSala;
	private JButton btnCrearSala;
	private JButton btnIngresarALaSala ;
	private JList list;
	DefaultListModel<String> listModel;
	
	public Menu() {
		setResizable(false);
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		lblUsuario = new JLabel("Debe iniciar sesion para poder ingresar a las salas.");
		lblUsuario.setLocation(10, 30);
		lblUsuario.setBounds(6, 50, 398, 14);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBounds(12, 15, 398, 23);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirLogin();
			}
		});
		
		btnJugar = new JButton("Jugar");
		btnJugar.setBounds(163, 365, 89, 23);
		btnJugar.setEnabled(true);
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Juego.iniciar();
			}
		});
		
		pnlSalas = new JPanel();
		pnlSalas.setBackground(Color.WHITE);
		pnlSalas.setBounds(6, 96, 299, 104);
		pnlSalas.setLayout(null);
		pnlSalas.setEnabled(false);

		lstSalas = new JList();
		lstSalas.setBounds(0, 0, 299, 104);
		lstSalas.setEnabled(false);
	    listModel = new DefaultListModel<>();
	    lstSalas.setModel(listModel);
		pnlSalas.add(lstSalas);
		
		lblSala = new JLabel("Salas activas");
		lblSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblSala.setBounds(33, 76, 272, 16);
		lblSala.setEnabled(false);
		
		btnCrearSala = new JButton("Crear sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirCreacionSalas();
			}
		});
		btnCrearSala.setBounds(304, 96, 106, 29);
		btnCrearSala.setEnabled(false);
		
		btnIngresarALaSala = new JButton("Ingresar\n");
		btnIngresarALaSala.setBounds(304, 126, 106, 74);
		btnIngresarALaSala.setEnabled(false);
		
		setLocationRelativeTo(null);
		
		contentPane.add(lblUsuario);
		contentPane.add(btnCrearSala);
		contentPane.add(btnIngresarALaSala);
		contentPane.add(lblSala);
		contentPane.add(btnIniciarSesion);
		contentPane.add(btnJugar);
		contentPane.add(pnlSalas);
		
		setContentPane(contentPane);
	}
	
	private void abrirLogin() {
		new Login(this);
	}
	
	private void abrirCreacionSalas() {
		new CreacionSala(this);
	}
	
	public void loggeado(String texto) {
		btnIngresarALaSala.setEnabled(true);
		btnCrearSala.setEnabled(true);
		lblSala.setEnabled(true);
		lstSalas.setEnabled(true);
		pnlSalas.setEnabled(true);

		lblUsuario.setText("Sesión iniciada. Usuario: " + texto);
		lblUsuario.setVisible(true);
	}
	
	public void agregarSala(Sala sala) {
		actualizarSalas();
	}
	
	public void actualizarSalas() {
		List<Sala> listaSalas = Sesion.buscarSalas();
		for (Sala sala : listaSalas) {
			listModel.addElement(sala.toString());			
		}
	}

	public static void main(String[] args) {
		new Menu().setVisible(true);
	}
}
