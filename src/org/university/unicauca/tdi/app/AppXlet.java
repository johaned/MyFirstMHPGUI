package org.university.unicauca.tdi.app;
/**
 * 
 * Autor: Johan Tique
 * 
 */
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HSceneTemplate;
import org.university.unicauca.tdi.model.Escena;

public class AppXlet implements Xlet {
	private XletContext context;
	private HScene scene;
	private String nomEscena = "HS_welcome";
	private HSceneFactory factory;
	private HSceneTemplate hst;
	private Escena interfaz;
	private String username = "";

	public void destroyXlet(boolean arg0) throws XletStateChangeException {
		// TODO Auto-generated method stub

	}

	public void initXlet(XletContext context) throws XletStateChangeException {
		setLogMsg("Iniciando APPXLET PRACTICE 4 XLET " + System.currentTimeMillis());
		this.context = context;
		createScene();
	}

	public void pauseXlet() {
		// TODO Auto-generated method stub

	}

	public void startXlet() throws XletStateChangeException {
		setLogMsg("Start XLET");
		System.out.println("RUNNING...");
		//Limpio todos los componentes que tenga la escena
		scene.removeAll();
		System.out.println("OPENING =>" + nomEscena);
		//Cargo la escena y la despliego dinamicamente
		loadScene(nomEscena);
		scene.repaint();
	}

	private void createScene() {
		HSceneFactory factory = HSceneFactory.getInstance();
		HSceneTemplate hst = new HSceneTemplate();
		hst.setPreference(HSceneTemplate.SCENE_SCREEN_DIMENSION,
				new org.havi.ui.HScreenDimension(1, 1), HSceneTemplate.REQUIRED);
		hst.setPreference(HSceneTemplate.SCENE_SCREEN_LOCATION,
				new org.havi.ui.HScreenPoint(0, 0), HSceneTemplate.REQUIRED);
		scene = factory.getBestScene(hst);
		Rectangle rect = scene.getBounds();
		scene.setBounds(rect);
		scene.setRenderMode(HScene.IMAGE_CENTER);
		scene.repaint();
		// scene.add(this);
		scene.setVisible(true);
		// this.requestFocus();
	}

	private void loadScene(String escena) {
		try {
			//Se toma un referencia de la clase asociada a la escena 
			Class escInt = Class.forName("org.university.unicauca.tdi.scene."
					+ escena);
			if (escInt == null)
				System.out.println("clase nula");
			//Se instancia la escena
			interfaz = (Escena) escInt.newInstance();
			//Se inicializa la escena
			interfaz.initializer(this);
		} catch (Exception e) {
			System.out.println("ERROR: Could not load class: " + e);
		}
	}

	public void setLogMsg(String msg) {
		System.out.println("LOG: " + msg);
	}

	public String getNomEscena() {
		return nomEscena;
	}

	public void setNomEscena(String nomEscena) {
		this.nomEscena = nomEscena;
	}

	public XletContext getContext() {
		return context;
	}

	public HScene getScene() {
		return scene;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
