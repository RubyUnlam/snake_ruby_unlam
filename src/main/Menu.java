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
	private Sala salaActual;
	private List<Sala> listaSalas;
	private JList<String> lstJugadores;
	private DefaultListModel<String> listModel;

	private JPanel contentPane;
	private JPanel pnlDetallesSala;
	private JButton btnIniciarSesion;
	private JButton btnJugar;
	private JButton btnCrearSala;
	private JButton btnVerSalasCreadas;
	private JButton btnSalirSala;
	private JLabel lblUsuario;
	private JLabel lblValorCantidadMaximaJugadores;
	private JLabel lblValorCantidadIA;
	private JLabel lblValorNombreSala;
	private JLabel lblValorCreador;
	private JLabel lblDetalleSala;
	private JLabel lblJugadoresEnSala;
	private JLabel lblConfiguracionJuego;
	private JLabel lblCantidadMaxJugadores;
	private JLabel lblCantidadIA;
	private JLabel lblCreador;
	private JLabel lblNombreSala;

	public Menu() {
		setResizable(false);
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 416);
		setLocationRelativeTo(null);
		
		JPanel pnlJugadores = new JPanel();
		pnlJugadores.setBounds(5, 210, 200, 150);
		pnlJugadores.setLayout(null);

		listModel = new DefaultListModel<>();
		lstJugadores = new JList<String>();
		lstJugadores.setBackground(new Color(255, 240, 245));
		lstJugadores.setBounds(0, 0, 200, 149);
		lstJugadores.setModel(listModel);
		pnlJugadores.add(lstJugadores);

		pnlDetallesSala = new JPanel();
		pnlDetallesSala.setBackground(new Color(255, 240, 245));
		pnlDetallesSala.setLayout(null);
		pnlDetallesSala.setBounds(205, 210, 200, 150);

		lblUsuario = new JLabel("Debe iniciar sesion para poder ingresar a las salas.");
		lblUsuario.setLocation(10, 30);
		lblUsuario.setBounds(6, 50, 398, 20);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		
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
		
		lblDetalleSala = new JLabel("DETALLES DE LA SALA");
		lblDetalleSala.setBounds(0, 163, 411, 15);
		lblDetalleSala.setForeground(new Color(0, 0, 0));
		lblDetalleSala.setHorizontalAlignment(SwingConstants.CENTER);

		lblJugadoresEnSala = new JLabel("Jugadores en esta sala");
		lblJugadoresEnSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblJugadoresEnSala.setForeground(new Color(0, 0, 0));
		lblJugadoresEnSala.setBounds(6, 190, 200, 16);

		lblConfiguracionJuego = new JLabel("Configuracion del juego");
		lblConfiguracionJuego.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracionJuego.setForeground(new Color(0, 0, 0));
		lblConfiguracionJuego.setBounds(204, 190, 200, 16);

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
		btnCrearSala.setBounds(6, 82, 200, 70);
		btnCrearSala.setEnabled(false);
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirCreacionSalas();
			}
		});

		btnVerSalasCreadas = new JButton("Ver salas creadas\n");
		btnVerSalasCreadas.setEnabled(false);
		btnVerSalasCreadas.setBounds(204, 82, 200, 70);
		btnVerSalasCreadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirSalasCreadas();
			}
		});
		
		btnSalirSala = new JButton("Salir de la sala");
		btnSalirSala.setEnabled(false);
		btnSalirSala.setBounds(251, 365, 122, 23);
		btnSalirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salirSala();
			}
		});
						
		contentPane = new JPanel();
		contentPane.add(pnlJugadores);
		contentPane.setBackground(new Color(204, 204, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.add(pnlDetallesSala);
		contentPane.add(btnSalirSala);
		contentPane.add(lblDetalleSala);
		contentPane.add(lblUsuario);
		contentPane.add(btnCrearSala);
		contentPane.add(btnVerSalasCreadas);
		contentPane.add(btnIniciarSesion);
		contentPane.add(btnJugar);
		contentPane.add(lblJugadoresEnSala);
		contentPane.add(lblConfiguracionJuego);
		setContentPane(contentPane);
	}

	private void abrirLogin() {
		new Login(this);
	}

	private void abrirCreacionSalas() {
		new CreacionSala(this);
	}

	public void abrirSalasCreadas() {
		new SalasCreadas(this);
	}

	public void loggeado(String usuario) {
		listaSalas = new ArrayList<>();
		btnVerSalasCreadas.setEnabled(true);
		btnCrearSala.setEnabled(true);
		usuarioActual = usuario;
		lblUsuario.setText("Sesión iniciada. Usuario: " + usuarioActual);
		lblUsuario.setVisible(true);
	}

	public void crearMiSala(Sala sala) {
		salaActual = sala;		
		listaSalas.add(salaActual);
		btnSalirSala.setEnabled(true);
		btnJugar.setEnabled(true);
		actualizarJugadoresYDetalles();
	}

	public void conectadoASala(Sala sala) {
		salaActual = sala;
		actualizarJugadoresYDetalles();
		// TODO: Agregar logica para recibir otros jugadores con cliente servidor
	}
	
	public void salirSala() {
		listModel.removeAllElements();
		cambiarEstadoDetallesSala(false);
	}
	
	private void actualizarJugadoresYDetalles() {
		limpiarListaJugadores();
		actualizarDetallesSalaActual();
		cambiarEstadoDetallesSala(true);
	}
	
	private void limpiarListaJugadores() {
		listModel.removeAllElements();
		listModel.addElement(usuarioActual);
	}
	
	private void actualizarDetallesSalaActual() {
		lblValorNombreSala.setText(salaActual.getNombreSala());
		lblValorCantidadMaximaJugadores.setText(String.valueOf(salaActual.getCantidadJugadores()));
		lblValorCantidadIA.setText(String.valueOf(salaActual.getCantidadIA()));
		lblValorCreador.setText(salaActual.getNombreCreador());
	}
	
	private void cambiarEstadoDetallesSala(boolean estado) {
		lblValorCantidadIA.setVisible(estado);
		lblValorCantidadMaximaJugadores.setVisible(estado);
		lblValorCreador.setVisible(estado);
		lblValorNombreSala.setVisible(estado);
		lblCantidadIA.setVisible(estado);
		lblCantidadMaxJugadores.setVisible(estado);
		lblCreador.setVisible(estado);
		lblNombreSala.setVisible(estado);
		lstJugadores.setEnabled(estado);
		pnlDetallesSala.setEnabled(estado);
		btnJugar.setEnabled(estado);
		btnSalirSala.setEnabled(estado);
	}

	public String getUsuarioActual() {
		return usuarioActual;
	}
	
	public List<Sala> getListaSalas() {
		return listaSalas;
	}
	
	//Inicia el menu del juego.
	public static void main(String[] args) {
		//Inicializo una instancia de SessionFactory al iniciar la aplicacion.
		SesionSingleton.getSessionFactory();
		//Genero el menu
		new Menu().setVisible(true);
	}
}
