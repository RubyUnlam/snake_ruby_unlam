package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;

import javax.swing.Timer;

public class GeneradorDeComestibles extends Thread {
	
	private int delay; 
	private Queue<Comestible> comestibles;
	
	GeneradorDeComestibles(int delay, Queue<Comestible> comestibles) {
		this.delay = delay;
		this.comestibles = comestibles;
	}

	public void run(){		
		Timer comestibleTimer = new Timer(delay * 20, new ActionListener() {
		@Override
			public void actionPerformed(ActionEvent e) {
				comestibles.add(new Manzana()); //TODO METER LOGICA DE CREACION DE COMESTIBLES
			}
		});
		comestibleTimer.start();
	}

}
