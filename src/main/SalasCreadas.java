package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class SalasCreadas extends JDialog {

	private static final long serialVersionUID = -2515309292855432466L;

	private Menu ventanaMenu;
	private List<Sala> listaSalas;
	private DefaultListModel<String> listModel = new DefaultListModel<>();

	private JLabel lblValorCantidadMaximaJugadores;
	private JLabel lblValorCantidadIA;
	private JLabel lblDificultadIA;
	private JLabel lblValorCreador;
	private JLabel lblCantidadMaxJugadores;
	private JLabel lblDificultad;
	private JLabel lblCantidadIA;
	private JLabel lblCreador;
	private JLabel lblPasswordSala;
	private JLabel lblInformativo;
	private JPanel pnlDetallesSala;
	private JList<String> lstSalas;
	private JPasswordField pswSala;
	private JButton btnConectar;


	public SalasCreadas(Menu menu) {
		
		ventanaMenu = menu;
		setBounds(100, 100, 450, 315);
		setLocationRelativeTo(menu);
		
		JLabel lblSalasCreadas = new JLabel("Salas creadas");
		lblSalasCreadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblSalasCreadas.setBounds(6, 5, 400, 16);

		JPanel pnlSalas = new JPanel();
		pnlSalas.setLayout(null);
		pnlSalas.setForeground(Color.WHITE);
		pnlSalas.setBounds(6, 40, 200, 190);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 200, 190);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlSalas.add(scrollPane);

		lstSalas = new JList<String>();
		lstSalas.setBounds(0, 0, 200, 149);
		lstSalas.setModel(listModel);
		lstSalas.setEnabled(false);
		lstSalas.setBackground(new Color(255, 240, 245));
		scrollPane.setViewportView(lstSalas);
		lstSalas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarDetalleSala();
			}
		});

		pnlDetallesSala = new JPanel();
		pnlDetallesSala.setBackground(new Color(255, 240, 245));
		pnlDetallesSala.setLayout(null);
		pnlDetallesSala.setBounds(206, 40, 200, 190);

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

		lblDificultad = new JLabel("Dificultad Bots");
		lblDificultad.setHorizontalAlignment(SwingConstants.CENTER);
		lblDificultad.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		lblDificultad.setBounds(6, 73, 188, 24);
		lblDificultad.setVisible(false);
		pnlDetallesSala.add(lblDificultad);

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

		lblDificultadIA = new JLabel("");
		lblDificultadIA.setHorizontalAlignment(SwingConstants.CENTER);
		lblDificultadIA.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblDificultadIA.setBounds(6, 85, 188, 24);
		pnlDetallesSala.add(lblDificultadIA);

		lblValorCreador = new JLabel("");
		lblValorCreador.setHorizontalAlignment(SwingConstants.CENTER);
		lblValorCreador.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblValorCreador.setBounds(6, 121, 188, 24);
		pnlDetallesSala.add(lblValorCreador);

		btnConectar = new JButton("Conectar");
		btnConectar.setEnabled(false);
		btnConectar.setBounds(289, 236, 117, 29);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectarASala();
			}
		});
		
		pswSala = new JPasswordField();
		pswSala.setEnabled(false);
		pswSala.setBounds(138, 236, 142, 26);

		lblPasswordSala = new JLabel("Ingrese Password");
		lblPasswordSala.setEnabled(false);
		lblPasswordSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordSala.setBounds(6, 240, 129, 16);

		lblInformativo = new JLabel("");
		lblInformativo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformativo.setBounds(6, 21, 400, 16);

		getContentPane().add(pswSala);
		getContentPane().add(lblPasswordSala);
		getContentPane().add(lblInformativo);
		getContentPane().add(pnlDetallesSala);
		getContentPane().add(btnConectar);
		getContentPane().setLayout(null);
		getContentPane().add(lblSalasCreadas);
		getContentPane().add(pnlSalas);
		
		actualizarSalas();

		setVisible(true);
	}

	private void actualizarSalas() {
		listaSalas = new ArrayList<Sala>();
		listaSalas.addAll(ventanaMenu.getListaSalas());
		for (Sala sala : listaSalas) {
			listModel.addElement(sala.getNombreSala());
		}
		if (listaSalas.size() > 0) {
			lstSalas.setEnabled(true);
			btnConectar.setEnabled(true);
		}
	}

	private void conectarASala() {
		if (!lstSalas.isSelectionEmpty()) {
			Sala sala = listaSalas.get(lstSalas.getSelectedIndex());
			if (!sala.getContrasenia().isEmpty() && pswSala.getPassword().length == 0) {
				mostrarMensajeInformativo("Debe ingresar password para esta sala");
			} else if (!String.valueOf(pswSala.getPassword()).equals(sala.getContrasenia())) {
				mostrarMensajeInformativo("Password erroneo, ingrese nuevamente");
			} else {
				ventanaMenu.conectadoASala(sala);
				dispose();
			}
		}
	}

	private void pedirPassword() {
		lblPasswordSala.setEnabled(true);
		pswSala.setEnabled(true);
	}

	private void mostrarDetalleSala() {
		if (lstSalas.isEnabled()) {
			Sala sala = listaSalas.get(lstSalas.getSelectedIndex());

			lblPasswordSala.setEnabled(false);
			pswSala.setEnabled(false);
			pswSala.setText("");
			mostrarMensajeInformativo("");
			
			lblDificultadIA.setText(String.valueOf(sala.getDificultadIA()));
			lblValorCantidadMaximaJugadores.setText(String.valueOf(sala.getCantidadJugadores()));
			lblValorCantidadIA.setText(String.valueOf(sala.getCantidadIA()));
			lblValorCreador.setText(ventanaMenu.getUsuarioActual());
			lblDificultad.setVisible(true);
			lblCantidadMaxJugadores.setVisible(true);
			lblCantidadIA.setVisible(true);
			lblCreador.setVisible(true);
			
			if (!sala.getContrasenia().isEmpty()) {
				pedirPassword();
			}
		}
	}

	/**
	 * Muestra mensaje de error o informativo.
	 * 
	 * @param mensaje
	 */
	public void mostrarMensajeInformativo(String mensaje) {
		lblInformativo.setForeground(Color.RED);
		lblInformativo.setText(mensaje);
		lblInformativo.setVisible(true);
	}
}
