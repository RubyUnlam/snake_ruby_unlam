package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class CreacionSala extends JDialog {

	private static final long serialVersionUID = 3146453246362725770L;
	
	private JTextField txtNombreSala;
	private JTextField txtMapa;
	private JTextField txtTiempo;
	private JPasswordField txtContrasenia;
	private JLabel lblInformativo;
	private JComboBox<Integer> cmbIA;
	private JComboBox<Integer> cmbJugadores;
	private JSpinner spinner;
	private Menu ventanaMenu;

	/**
	 * Create the dialog.
	 */
	public CreacionSala(Menu menu) {
		
		ventanaMenu = menu;
		
		setBounds(100, 100, 356, 260);
		setVisible(true);
		setLocationRelativeTo(menu);
		JLabel lblNombreSala = new JLabel("Nombre de la sala");
		lblNombreSala.setBounds(6, 33, 135, 16);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(6, 61, 61, 16);

		JLabel lblCantidadDeJugadores = new JLabel("Cantidad de Jugadores");
		lblCantidadDeJugadores.setBounds(6, 89, 150, 16);

		JLabel lblCantidadDeIa = new JLabel("Cantidad de IA");
		lblCantidadDeIa.setBounds(6, 117, 111, 16);

		JLabel lblTiempo = new JLabel("Tiempo");
		lblTiempo.setBounds(6, 145, 61, 16);

		JLabel lblMapa = new JLabel("Mapa");
		lblMapa.setBounds(6, 173, 61, 16);

		txtNombreSala = new JTextField();
		txtNombreSala.setBounds(168, 28, 182, 26);
		txtNombreSala.setColumns(10);

		txtMapa = new JTextField();
		txtMapa.setColumns(10);
		txtMapa.setBounds(168, 168, 182, 26);
		txtMapa.setEnabled(false);

		txtTiempo = new JTextField();
		txtTiempo.setColumns(10);
		txtTiempo.setBounds(168, 140, 182, 26);
		txtTiempo.setEnabled(false);

		JButton btnCrearSala = new JButton("Crear sala");
		btnCrearSala.setBounds(115, 202, 117, 30);
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionCrearSala();
			}
		});

		JLabel lblCreacionDeSala = new JLabel("Creacion de sala");
		lblCreacionDeSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreacionDeSala.setBounds(6, 5, 344, 16);

		txtContrasenia = new JPasswordField();
		txtContrasenia.setBounds(168, 56, 182, 26);

		lblInformativo = new JLabel("");
		lblInformativo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformativo.setBounds(6, 14, 344, 16);

		cmbIA = new JComboBox<Integer>();
		cmbIA.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3}));
		cmbIA.setBounds(168, 113, 64, 27);

		cmbJugadores = new JComboBox<Integer>();
		cmbJugadores.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4}));
		cmbJugadores.setBounds(168, 85, 64, 27);
		
		spinner = new JSpinner();
		spinner.setBounds(296, 112, 54, 26);
		SpinnerModel spinnerModel = new SpinnerNumberModel(50, 0, 100, 5);
		spinner.setModel(spinnerModel);
		
		JLabel lblDificultad = new JLabel("Dificultad");
		lblDificultad.setBounds(233, 117, 69, 16);
		
		getContentPane().setLayout(null);
		getContentPane().add(lblNombreSala);
		getContentPane().add(lblPassword);
		getContentPane().add(lblCantidadDeJugadores);
		getContentPane().add(lblCantidadDeIa);
		getContentPane().add(lblTiempo);
		getContentPane().add(lblMapa);
		getContentPane().add(btnCrearSala);
		getContentPane().add(lblCreacionDeSala);
		getContentPane().add(txtNombreSala);
		getContentPane().add(txtMapa);
		getContentPane().add(txtTiempo);
		getContentPane().add(txtContrasenia);
		getContentPane().add(lblInformativo);
		getContentPane().add(cmbIA);
		getContentPane().add(cmbJugadores);
		getContentPane().add(spinner);
		getContentPane().add(lblDificultad);

	}

	/**
	 * Inicia la validacion del usuario y la contrasenia, y llama al proceso de
	 * creaciom de sala.
	 */
	private void actionCrearSala() {
		String password = String.valueOf(txtContrasenia.getPassword());
		Sala sala = new Sala(txtNombreSala.getText(), password,
				(int) cmbJugadores.getSelectedItem(), (int) cmbIA.getSelectedItem(),
				ventanaMenu.getUsuarioActual(), (int)spinner.getValue());
		if (!camposCreacionSalaVacios() && cantidadJugadoresValida() && crearSala(sala)) {
			dispose();
			ventanaMenu.crearMiSala(sala);
		}
	}

	/**
	 * Valida que los campos de nombre de sala, contrasenia, y cantidad de jugadores
	 * no esten vacios.
	 * @return
	 */
	private boolean camposCreacionSalaVacios() {
		if (txtNombreSala.getText().isEmpty()) {
			mostrarMensajeInformativo("Nombre de sala es obligatorio");
			return true;
		}
		return false;
	}
	
	/**
	 * Valida que la cantidad maxima de jugadres sea valida
	 * @return
	 */
	private boolean cantidadJugadoresValida() {
		int cantidadTotalJugadores = (int)cmbIA.getSelectedItem() + (int)cmbJugadores.getSelectedItem();
		if (cantidadTotalJugadores > 4) {
			mostrarMensajeInformativo("Cantidad de jugadores mayor a 4");
			return false;
		}
		return true;
	}

	/**
	 * Llama al proceso de creacion de sala, y muestra un error en caso de fallar.
	 * @param nombreSala
	 * @param contrasenia
	 * @return verdadero o falso segun el exito de la creacion.
	 */
	public boolean crearSala(Sala sala) {
//		Sesion sesion = new Sesion();
//		boolean creoCorrectamente = sesion.crearSala(sala);
		if (!ventanaMenu.getListaSalas().contains(sala)) {
			return true;
		} else {
			mostrarMensajeInformativo("Error al crear sala. Nombre en uso");
			return false;
		}
	}
		
	/**
	 * Muestra mensaje de error o informativo.
	 * @param mensaje
	 */
	public void mostrarMensajeInformativo(String mensaje) {
		lblInformativo.setForeground(Color.RED);
		lblInformativo.setText(mensaje);
		lblInformativo.setVisible(true);
	}
}
