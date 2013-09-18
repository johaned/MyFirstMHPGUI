package org.university.unicauca.tdi.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import org.dvb.ui.DVBColor;
import org.havi.ui.HComponent;
import org.havi.ui.HScene;
import org.havi.ui.HSinglelineEntry;
import org.havi.ui.HStaticIcon;
import org.havi.ui.HStaticText;
import org.havi.ui.HTextButton;
import org.havi.ui.HVisible;
import org.havi.ui.event.HActionListener;
import org.havi.ui.event.HKeyListener;
import org.university.unicauca.tdi.app.AppXlet;
import org.university.unicauca.tdi.model.Escena;

public class HS_moving extends HComponent implements Escena, HKeyListener,
		HActionListener {

	private XletContext ctx;
	private HScene scene;
	private AppXlet principal;

	// COMPONENTES DE LA ESCENA DE MOVIMIENTO
	private static final String RUTA_IMAGEN_PING = "org/university/unicauca/tdi/res/indice.png";
	private static final int DESP = 10;
	private static final String LB_MOVING_MSG = "BIENVENIDO ";
    private static final String LB_MOVING_INSTRUCTION = "Presione las flechas para generar movimiento";
    private HStaticText lbMoving;
    private HStaticText lbInstructions;
    private HStaticIcon imgTwitter;
    private Image image = null;


	public void cleaner() {
		scene.removeKeyListener(this);
		scene.removeAll();
		scene.repaint();
	}

	public void initializer(AppXlet principal) {
		this.ctx = principal.getContext();
		this.scene = principal.getScene();
		this.principal = principal;
		get_resources();
		config_container();

	}

	private void get_resources() {
		image = null;
        try {
            MediaTracker mediaTracker = new MediaTracker(scene);
            image = Toolkit.getDefaultToolkit().getImage(RUTA_IMAGEN_PING);
            mediaTracker.addImage(image, 0);
            mediaTracker.waitForAll();
        } catch (InterruptedException ex) {
            System.out.println("ERROR CARGANDO IMAGEN");
            ex.printStackTrace();
        }
	}

	private void config_container() {
		// Configurando la informacion del Componente, es importante para poder
		// acceder al metodo nativo repaint
		Rectangle rect = scene.getBounds();
		setBounds(rect);
		setVisible(true);
		// Añadiendo el componente a la escena
		scene.add(this);
		// añadiendo propiedades la escena
		scene.addKeyListener(this);
		scene.requestFocus();
		add_components();
		scene.repaint();
		scene.setVisible(true);

	}

	private void add_components() {
		scene.setBackgroundMode(HScene.BACKGROUND_FILL);
		scene.setBackground(Color.WHITE);
		
		lbMoving = new HStaticText(LB_MOVING_MSG + principal.getUsername().toUpperCase(), 30, 10, 580, 40);
        lbMoving.setBordersEnabled(false);
        lbMoving.setForeground(Color.black);
        lbMoving.setFont(new Font("Tiresias", Font.BOLD, 24));

        lbInstructions = new HStaticText(LB_MOVING_INSTRUCTION, 10, 50, 580, 70);
        lbInstructions.setBordersEnabled(false);
        lbInstructions.setForeground(Color.black);
        lbInstructions.setFont(new Font("Tiresias", Font.BOLD, 24));

        imgTwitter = new HStaticIcon(image);
        imgTwitter.setBounds(0, 0, 200, 200);
        set_pos_image(scene.getWidth()/2 - 64 , scene.getHeight()/2 - 64);
        scene.add(lbMoving);
        scene.add(lbInstructions);
        scene.add(imgTwitter);
        scene.addKeyListener(this);
        imgTwitter.addKeyListener(this);
        scene.requestFocus();

	}
    private void set_pos_image(int x, int y){
        imgTwitter.setLocation(x, y);
    }

	private void change_scene(String escena) {
		principal.setNomEscena(escena);
		cleaner();
		principal.getScene().removeAll();
		principal.pauseXlet();
		try {
			principal.startXlet();
		} catch (XletStateChangeException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent key) {
		System.out.println("estoy en la ventana principal metodo keylistener: "
				+ key.getKeyCode());
		switch (key.getKeyCode()) {
        case 40:// ARRIBA
        	set_pos_image(imgTwitter.getX(), imgTwitter.getY() + DESP);
            break;
        case 38://ABAJO
        	set_pos_image(imgTwitter.getX(), imgTwitter.getY() - DESP);
            break;
        case 37://DERECHA
        	set_pos_image(imgTwitter.getX() - DESP, imgTwitter.getY());
            break;
        case 39://IZQUIERDA
        	set_pos_image(imgTwitter.getX() + DESP, imgTwitter.getY());
            break;
        default:
            break;
    }
		
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void actionPerformed(ActionEvent event) {
	}

	public void setLogMsg(String msg) {
		System.out.println("LOG SCENE(" + principal.getNomEscena() + "): "
				+ msg);
	}

}