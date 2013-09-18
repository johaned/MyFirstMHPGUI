package org.university.unicauca.tdi.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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

public class HS_welcome extends HComponent implements Escena, HKeyListener,
		HActionListener {

	private XletContext ctx;
	private HScene scene;
	private AppXlet principal;

	// COMPONENTES DE LA ESCENA DE INGRESO
	private static final String LB_SIGNIN_MSG = "Para ingresar al sistema especifique su nombre";
	private static final String LB_SIGNIN_NAME = "Nombre: ";
	private static final String BT_SIGNIN = "Ingresar";
	private HStaticText lbMsg;
	private HStaticText lbName;
	private HSinglelineEntry txName;
	private HTextButton btSignin;

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
		// nothing...
	}

	private void config_container() {
		// Configurando la informacion del Componente, es importante para poder
		// acceder al metodo nativo repaint
		Rectangle rect = scene.getBounds();
		setBounds(rect);
		setVisible(true);
		// A�adiendo el componente a la escena
		scene.add(this);
		// a�adiendo propiedades la escena
		scene.addKeyListener(this);
		scene.requestFocus();
		add_components();
		scene.repaint();
		scene.setVisible(true);

	}

	private void add_components() {
		scene.setBackgroundMode(HScene.BACKGROUND_FILL);
		scene.setBackground(Color.black);

		lbMsg = new HStaticText(LB_SIGNIN_MSG, 30, 10, 580, 40);
		lbMsg.setBordersEnabled(false);
		lbMsg.setForeground(Color.white);
		lbMsg.setFont(new Font("Tiresias", Font.BOLD, 24));

		lbName = new HStaticText(LB_SIGNIN_NAME, 10, 50, 580, 70);
		lbName.setBordersEnabled(false);
		lbName.setForeground(Color.white);
		lbName.setFont(new Font("Tiresias", Font.BOLD, 24));

		txName = new HSinglelineEntry();
		txName.setBounds(70, 100, 300, 40);
		txName.setEnabled(true);
		txName.setTextContent("", HVisible.NORMAL_STATE);
		txName.setBordersEnabled(true);
		txName.setForeground(Color.blue);
		txName.setBackground(Color.white);
		txName.setBackgroundMode(HVisible.BACKGROUND_FILL);
		txName.setFont(new Font("Tiresias", Font.BOLD, 24));
		txName.setMaxChars(30);
		txName.setEditMode(true);
		txName.setHorizontalAlignment(HVisible.HALIGN_LEFT);

		btSignin = new HTextButton(BT_SIGNIN, 200, 220, 100, 40);
		btSignin.setTextContent(BT_SIGNIN, HVisible.FOCUSED_STATE);
		btSignin.setBordersEnabled(true);
		btSignin.setForeground(Color.white);
		btSignin.setBackground(Color.LIGHT_GRAY);
		btSignin.setBackgroundMode(HVisible.BACKGROUND_FILL);
		btSignin.setFont(new Font("Tiresias", Font.BOLD, 24));

		scene.add(lbMsg);
		scene.add(lbName);
		scene.add(txName);
		scene.add(btSignin);
		scene.addKeyListener(this);

		btSignin.addHActionListener(this);
		txName.addHKeyListener(this);
		txName.requestFocus();
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
		case 10:
			principal.setUsername(txName.getTextContent(HVisible.ALL_STATES));
			btSignin.requestFocus();
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
		change_scene("HS_moving");
	}

	public void setLogMsg(String msg) {
		System.out.println("LOG SCENE(" + principal.getNomEscena() + "): "
				+ msg);
	}

}