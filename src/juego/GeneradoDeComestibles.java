package juego;

import juego.comestible.*;

import java.awt.event.ActionEvent;
import java.util.Queue;
import javax.swing.Timer;
import java.awt.event.ActionListener;

import static utilidades.Constantes.CICLO_DE_JUEGO;

public class GeneradoDeComestibles {

    private Timer timer;

    public GeneradoDeComestibles(Queue<Comestible> comestibles, int jugadoresEnCampo) {
        this.timer = new Timer(CICLO_DE_JUEGO * 10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comestibles.size() < jugadoresEnCampo * 2) {
                    int proba = (int) (Math.random() * 101);
                    if (proba <= 60) {
                        comestibles.add(new Manzana());
                    } else if (proba <= 70) {
                        comestibles.add(new Pera());
                    } else if (proba <= 80) {
                        comestibles.add(new Coco());
                    } else if (proba <= 90) {
                        comestibles.add(new Limon());
                    } else {
                        comestibles.add(new Durazno());
                    }
                }
            }
        });
    }

    public void iniciar() {
        this.timer.start();
    }

    public void parar() {
        this.timer.stop();
    }
}
