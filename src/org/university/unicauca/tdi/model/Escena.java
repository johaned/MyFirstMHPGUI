package org.university.unicauca.tdi.model;

import org.university.unicauca.tdi.app.AppXlet;

public interface Escena {
	public final int VK_ROJO=403;
	public final int VK_VERDE=404;
	public final int VK_AMARILLO=405;
	public final int VK_AZUL=406;
	public final int VK_IZQ=37;
	public final int VK_DER=39;
	public final int VK_ARR=38;
	public final int VK_ABA=40;
	public final int VK_OK=10;
	
	public abstract void initializer(AppXlet appXlet);
	public abstract void cleaner();
}
