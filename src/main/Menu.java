package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private List<Sala> listaSalas;
	private DefaultListModel<String> listModel;
	private List<String> listUsuarios;

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
		lblUsuario.setBounds(6, 50, 398, 20);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);

		btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBounds(6, 15, 398, 23);
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirLogin();
			}
		});

		btnJugar = new JButton("Jugar");
		btnJugar.setBounds(54, 365, 106, 23);
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
		btnCrearSala.setBounds(6, 82, 200, 70);
		btnCrearSala.setEnabled(false);

		btnIngresarALaSala = new JButton("Ver salas creadas\n");
		btnIngresarALaSala.setEnabled(false);
		btnIngresarALaSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verSalas();
			}
		});
		btnIngresarALaSala.setBounds(204, 82, 200, 70);

		setLocationRelativeTo(null);

		contentPane.add(lblUsuario);
		contentPane.add(btnCrearSala);
		contentPane.add(btnIngresarALaSala);
		contentPane.add(btnIniciarSesion);
		contentPane.add(btnJugar);

		setContentPane(contentPane);

		JPanel pnlJugadores = new JPanel();
		pnlJugadores.setForeground(Color.WHITE);
		pnlJugadores.setBounds(5, 210, 200, 150);
		contentPane.add(pnlJugadores);
		pnlJugadores.setLayout(null);

		lstJugadores = new JList<String>();
		lstJugadores.setBackground(new Color(255, 240, 245));
		lstJugadores.setBounds(0, 0, 200, 149);
		lstJugadores.setModel(listModel);
		pnlJugadores.add(lstJugadores);

		pnlDetallesSala = new JPanel();
		pnlDetallesSala.setBackground(new Color(255, 240, 245));
		pnlDetallesSala.setLayout(null);
		pnlDetallesSala.setBounds(205, 210, 200, 150);
		contentPane.add(pnlDetallesSala);

		lblCantidadMaxJugadores = new JLabel("Cantidad maxima de jugadores");
		lblCantidadMaxJugadores.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblCantidadMaxJugadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidadMaxJugadores.setBounds(6, 78, 188, 18);
		lblCantidadMaxJugadores.setVisible(false);
		pnlDetallesSala.add(lblCantidadMaxJugadores);

		lblCantidadIA = new JLabel("Cantidad de bots");
		lblCantidadIA.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidadIA.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblCantidadIA.setBounds(6, 42, 188, 18);
		lblCantidadIA.setVisible(false);
		pnlDetallesSala.add(lblCantidadIA);

		lblCreador = new JLabel("Creador");
		lblCreador.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreador.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblCreador.setBounds(6, 114, 188, 18);
		lblCreador.setVisible(false);
		pnlDetallesSala.add(lblCreador);

		lblNombreSala = new JLabel("Nombre de la sala");
		lblNombreSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreSala.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblNombreSala.setVisible(false);
		lblNombreSala.setBounds(6, 6, 188, 18);
		pnlDetallesSala.add(lblNombreSala);

		lblValorCantidadMaximaJugadores = new JLabel("");
		lblValorCantidadMaximaJugadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorCantidadMaximaJugadores.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorCantidadMaximaJugadores.setBounds(6, 96, 188, 18);
		pnlDetallesSala.add(lblValorCantidadMaximaJugadores);

		lblValorCantidadIA = new JLabel("");
		lblValorCantidadIA.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorCantidadIA.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorCantidadIA.setBounds(6, 60, 188, 18);
		pnlDetallesSala.add(lblValorCantidadIA);

		lblValorNombreSala = new JLabel("");
		lblValorNombreSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorNombreSala.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorNombreSala.setBounds(6, 24, 188, 18);
		pnlDetallesSala.add(lblValorNombreSala);

		lblValorCreador = new JLabel("");
		lblValorCreador.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorCreador.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorCreador.setBounds(6, 132, 188, 18);
		pnlDetallesSala.add(lblValorCreador);

		btnSalirSala = new JButton("Salir de la sala");
		btnSalirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSalirSala();
			}
		});
		btnSalirSala.setEnabled(false);
		btnSalirSala.setBounds(251, 365, 122, 23);
		contentPane.add(btnSalirSala);

		lvlDetalleSala = new JLabel("DETALLES DE LA SALA");
		lvlDetalleSala.setBounds(0, 163, 411, 15);
		contentPane.add(lvlDetalleSala);
		lvlDetalleSala.setForeground(new Color(0, 0, 0));
		lvlDetalleSala.setHorizontalAlignment(SwingConstants.CENTER);

		lvlJugadoresEnSala = new JLabel("Jugadores en esta sala");
		lvlJugadoresEnSala.setHorizontalAlignment(SwingConstants.CENTER);
		lvlJugadoresEnSala.setForeground(new Color(0, 0, 0));
		lvlJugadoresEnSala.setBounds(6, 190, 200, 16);
		contentPane.add(lvlJugadoresEnSala);

		lblConfiguracionJuego = new JLabel("Configuracion del juego");
		lblConfiguracionJuego.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracionJuego.setForeground(new Color(0, 0, 0));
		lblConfiguracionJuego.setBounds(204, 190, 200, 16);
		contentPane.add(lblConfiguracionJuego);
				
		listaSalas = new ArrayList<>();
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
		usuarioActual = usuario;
		lblUsuario.setText("Sesión iniciada. Usuario: " + usuarioActual);
		lblUsuario.setVisible(true);
	}

	public void crearMiSala(Sala sala) {
		lblValorNombreSala.setText(sala.getNombreSala());
		lblValorCantidadMaximaJugadores.setText(String.valueOf(sala.getCantidadJugadores()));
		lblValorCantidadIA.setText(String.valueOf(sala.getCantidadIA()));
		lblValorCreador.setText(usuarioActual);
		
		lstJugadores.setEnabled(true);
		pnlDetallesSala.setEnabled(true);
		btnSalirSala.setEnabled(true);
		btnJugar.setEnabled(true);
		
		lblValorCantidadIA.setVisible(true);
		lblValorCantidadMaximaJugadores.setVisible(true);
		lblValorCreador.setVisible(true);
		lblValorNombreSala.setVisible(true);
		
		lblNombreSala.setVisible(true);
		lblCantidadMaxJugadores.setVisible(true);
		lblCantidadIA.setVisible(true);
		lblCreador.setVisible(true);
		
		btnSalirSala.setEnabled(true);
		btnJugar.setEnabled(true);
		
		listaSalas.add(sala);
		listModel.removeAllElements();
		listModel.addElement(usuarioActual);
		
		salaActual = sala;
	}

	public void conectadoASala(Sala sala) {
		lblValorNombreSala.setText(sala.getNombreSala());
		lblValorCantidadMaximaJugadores.setText(String.valueOf(sala.getCantidadJugadores()));
		lblValorCantidadIA.setText(String.valueOf(sala.getCantidadIA()));
		lblValorCreador.setText(sala.getNombreCreador());
		lblNombreSala.setVisible(true);
		lblCantidadMaxJugadores.setVisible(true);
		lblCantidadIA.setVisible(true);
		lblCreador.setVisible(true);
		lblValorCantidadIA.setVisible(true);
		lblValorCantidadMaximaJugadores.setVisible(true);
		lblValorCreador.setVisible(true);
		lblValorNombreSala.setVisible(true);
		lstJugadores.setEnabled(true);
		pnlDetallesSala.setEnabled(true);
		btnSalirSala.setEnabled(true);
		btnJugar.setEnabled(true);
		salaActual = sala;
		listModel.removeAllElements();
		listModel.addElement(usuarioActual);			
		// TODO: Agregar logica para recibir otros jugadores con cliente servidor
	}

	public void verSalas() {
		new SalasCreadas(this);
	}

	public String getUsuarioActual() {
		return usuarioActual;
	}
	
	public List<Sala> getListaSalas() {
		return listaSalas;
	}
	
	public void actionSalirSala() {
		listModel.removeAllElements();
		lblValorCantidadIA.setVisible(false);
		lblValorCantidadMaximaJugadores.setVisible(false);
		lblValorCreador.setVisible(false);
		lblValorNombreSala.setVisible(false);
		lblCantidadIA.setVisible(false);
		lblCantidadMaxJugadores.setVisible(false);
		lblCreador.setVisible(false);
		lblNombreSala.setVisible(false);
		lstJugadores.setEnabled(false);
		pnlDetallesSala.setEnabled(false);
		btnJugar.setEnabled(false);
		btnSalirSala.setEnabled(false);
	}
	
	public static void main(String[] args) {
		new SesionSingleton().getSessionFactory();
		new Menu().setVisible(true);
	}
}
