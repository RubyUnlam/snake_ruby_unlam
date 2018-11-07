package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;

	private String usuarioActual;
	private JPanel contentPane;
	private JPanel pnlDetallesSala;
	private JButton btnIniciarSesion;
	private JButton btnJugar;
	private JButton btnCrearSala;
	private JButton btnIngresarALaSala;
	private JLabel lblUsuario;
	private JLabel lblValorCantidadMaximaJugadores;
	private JLabel lblValorCantidadIA;
	private JLabel lblValorNombreSala;
	private JLabel lblValorCreador;
	DefaultListModel<String> listModel;
	private JLabel lvlDetalleSala;
	private JLabel lvlJugadoresEnSala;
	private JLabel lblConfiguracionJuego;

	private JList<String> lstJugadores;

	private JLabel lblCantidadMaxJugadores;

	private JLabel lblCantidadIA;

	private JLabel lblCreador;

	private JLabel lblNombreSala;

	private JButton btnSalirSala;
	
	private Sala salaActual;

	public Menu() {
		setResizable(false);
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 416);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		listModel = new DefaultListModel<>();
		
		lblUsuario = new JLabel("Debe iniciar sesion para poder ingresar a las salas.");
		lblUsuario.setLocation(10, 30);
		lblUsuario.setBounds(6, 50, 398, 14);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);

		btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBounds(6, 15, 398, 23);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirLogin();
			}
		});

		btnJugar = new JButton("Jugar");
		btnJugar.setBounds(43, 365, 106, 23);
		btnJugar.setEnabled(false);
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Juego.iniciar(salaActual);
			}
		});

		btnCrearSala = new JButton("Crear sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirCreacionSalas();
			}
		});
		btnCrearSala.setBounds(6, 89, 200, 74);
		btnCrearSala.setEnabled(false);

		btnIngresarALaSala = new JButton("Ver salas creadas\n");
		btnIngresarALaSala.setBounds(204, 89, 200, 74);
		btnIngresarALaSala.setEnabled(false);

		setLocationRelativeTo(null);

		contentPane.add(lblUsuario);
		contentPane.add(btnCrearSala);
		contentPane.add(btnIngresarALaSala);
		contentPane.add(btnIniciarSesion);
		contentPane.add(btnJugar);

		setContentPane(contentPane);

		JPanel pnlJugadores = new JPanel();
		pnlJugadores.setForeground(Color.WHITE);
		pnlJugadores.setBounds(6, 210, 200, 149);
		contentPane.add(pnlJugadores);
		pnlJugadores.setLayout(null);

		lstJugadores = new JList<String>();
		lstJugadores.setBackground(new Color(255, 240, 245));
		lstJugadores.setBounds(1, 0, 200, 149);
		lstJugadores.setModel(listModel);
		pnlJugadores.add(lstJugadores);

		pnlDetallesSala = new JPanel();
		pnlDetallesSala.setBackground(new Color(255, 240, 245));
		pnlDetallesSala.setLayout(null);
		pnlDetallesSala.setBounds(204, 210, 200, 149);
		contentPane.add(pnlDetallesSala);

		lblCantidadMaxJugadores = new JLabel("Cantidad maxima de jugadores");
		lblCantidadMaxJugadores.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblCantidadMaxJugadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidadMaxJugadores.setBounds(6, 0, 188, 24);
		lblCantidadMaxJugadores.setVisible(false);
		pnlDetallesSala.add(lblCantidadMaxJugadores);

		lblCantidadIA = new JLabel("Cantidad de bots");
		lblCantidadIA.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidadIA.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblCantidadIA.setBounds(6, 36, 188, 24);
		lblCantidadIA.setVisible(false);
		pnlDetallesSala.add(lblCantidadIA);

		lblCreador = new JLabel("Creador");
		lblCreador.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreador.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblCreador.setBounds(6, 103, 188, 24);
		lblCreador.setVisible(false);
		pnlDetallesSala.add(lblCreador);

		lblNombreSala = new JLabel("Nombre de la sala");
		lblNombreSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreSala.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblNombreSala.setBounds(6, 73, 188, 24);
		lblNombreSala.setVisible(false);
		pnlDetallesSala.add(lblNombreSala);

		lblValorCantidadMaximaJugadores = new JLabel("");
		lblValorCantidadMaximaJugadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorCantidadMaximaJugadores.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorCantidadMaximaJugadores.setBounds(6, 17, 188, 24);
		pnlDetallesSala.add(lblValorCantidadMaximaJugadores);

		lblValorCantidadIA = new JLabel("");
		lblValorCantidadIA.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorCantidadIA.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorCantidadIA.setBounds(6, 53, 188, 24);
		pnlDetallesSala.add(lblValorCantidadIA);

		lblValorNombreSala = new JLabel("");
		lblValorNombreSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorNombreSala.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorNombreSala.setBounds(6, 85, 188, 24);
		pnlDetallesSala.add(lblValorNombreSala);

		lblValorCreador = new JLabel("");
		lblValorCreador.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorCreador.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorCreador.setBounds(6, 121, 188, 24);
		pnlDetallesSala.add(lblValorCreador);

		btnSalirSala = new JButton("Salir de la sala");
		btnSalirSala.setEnabled(false);
		btnSalirSala.setBounds(259, 365, 122, 23);
		contentPane.add(btnSalirSala);
		
		lvlDetalleSala = new JLabel("DETALLES DE LA SALA SELECCIONADA\n");
		lvlDetalleSala.setBounds(0, 170, 411, 16);
		contentPane.add(lvlDetalleSala);
		lvlDetalleSala.setForeground(new Color(0, 0, 0));
		lvlDetalleSala.setHorizontalAlignment(SwingConstants.CENTER);
		
		lvlJugadoresEnSala = new JLabel("Jugadores en esta sala");
		lvlJugadoresEnSala.setHorizontalAlignment(SwingConstants.CENTER);
		lvlJugadoresEnSala.setForeground(new Color(0, 0, 0));
		lvlJugadoresEnSala.setBounds(6, 193, 200, 16);
		contentPane.add(lvlJugadoresEnSala);
		
		lblConfiguracionJuego = new JLabel("Configuracion del juego");
		lblConfiguracionJuego.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracionJuego.setForeground(new Color(0, 0, 0));
		lblConfiguracionJuego.setBounds(204, 193, 200, 16);
		contentPane.add(lblConfiguracionJuego);
	}

	private void abrirLogin() {
		new Login(this);
	}

	private void abrirCreacionSalas() {
		new CreacionSala(this);
	}

	public void loggeado(String usuario) {
		btnIngresarALaSala.setEnabled(true);
		btnCrearSala.setEnabled(true);
//		lblSala.setEnabled(true);
//		lstSalas.setEnabled(true);
//		pnlSalas.setEnabled(true);
		usuarioActual = usuario;
		lblUsuario.setText("Sesión iniciada. Usuario: " + usuarioActual);
		lblUsuario.setVisible(true);
	}

	public void crearMiSala(Sala sala) {
		lblValorNombreSala.setText(sala.getNombreSala());
		lblValorCantidadMaximaJugadores.setText(String.valueOf(sala.getCantidadJugadores()));
		lblValorCantidadIA.setText(String.valueOf(sala.getCantidadIA()));
		lblValorCreador.setText(usuarioActual);
		lblNombreSala.setVisible(true);
		lblCantidadMaxJugadores.setVisible(true);
		lblCantidadIA.setVisible(true);
		lblCreador.setVisible(true);
		JugadorSala jugadorSala = new JugadorSala(usuarioActual, sala.getNombreSala());
		new Sesion().conectarJugadorSala(jugadorSala);
		listModel.addElement(usuarioActual);
		btnSalirSala.setEnabled(true);
		btnJugar.setEnabled(true);
		salaActual = sala;
	}

//	public void actualizarSalas() {
//		List<Sala> listaSalas = Sesion.buscarSalas();
//		for (Sala sala : listaSalas) {
//			listModel.addElement(sala.toString());
//		}
//	}

	public static void main(String[] args) {
		new Menu().setVisible(true);
	}
	
	public String getUsuarioActual() {
		return usuarioActual;
	}
}
