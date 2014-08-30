package com.retni.applacegps;

public class Alojamiento {
	
	private String objectId;
	
	private String dir_escrita, descripcion, fotografia, titulo, tipo_aloj;
	
	private int capacidad, num_piezas, num_camas, num_banos, precio;
	private double dir_latitud, dir_longitud;
	
	private boolean higiene, aire_acondicionado, calefaccion, cocina, internet, piscina;
	private boolean lavadora, desayuno, estacionamiento, mascota, quincho, tv, telefono;
	 
	public String getObjectId() {
	  return objectId;
	}	 
	public void setObjectId( String objectId ) {
	  this.objectId = objectId;
	}
	
	//String**********************************************************************************************	
	//Dir_escrita
	public String getDir_escrita() {
		return dir_escrita;
	}	
	public void setDir_escrita( String dir_escrita ) {
		this.dir_escrita = dir_escrita;
	}
	
	//Descripcion	 
	public String getDescripcion() {
		return descripcion;
	}	
	public void setDescripciona( String descripcion ) {
		this.descripcion = descripcion;
	}
	
	//fotografia	 
	public String getFotografia() {
		return fotografia;
	}	 
	public void setFotografia( String fotografia ) {
	  this.fotografia = fotografia;
	}
	
	//Título	 
	public String getTitulo() {
	  return titulo;
	}	 
	public void setTitulo( String titulo ) {
	  this.titulo = titulo;
	}
	
	//tipo_aloj
	public String getTipo_aloj() {
	  return tipo_aloj;
	}	 
	public void setTipo_aloj( String tipo_aloj ) {
	  this.tipo_aloj = tipo_aloj;
	}
	
	//int***************************************************************************************************
	//Capacidad
	public int getCapacidad() {
	  return capacidad;
	}	 
	public void setCapacidad( int capacidad ) {
	  this.capacidad = capacidad;
	}	
	
	//num_piezas
	public int getPiezas() {
	  return num_piezas;
	}	 
	public void setPiezas( int num_piezas ) {
	  this.num_piezas = num_piezas;
	}
	
	//num_camas
	public int getCamas() {
	  return num_camas;
	}	 
	public void setCamas( int num_camas ) {
	  this.num_camas = num_camas;
	}
	
	//num_banos
	public int getBanos() {
	  return num_banos;
	}	 
	public void setBanos( int num_banos ) {
	  this.num_banos = num_banos;
	}
	
	//precio
	public int getPrecio() {
	  return precio;
	}	 
	public void setPrecio( int precio ) {
	  this.precio = precio;
	}
	
	//double**********************************************************************************************
	//dir_latitud
	public double getLatitud() {
	  return dir_latitud;
	}	 
	public void setLatitud( double dir_latitud ) {
	  this.dir_latitud = dir_latitud;
	}
	
	//dir_longitud
	public double getLongitud() {
	  return dir_longitud;
	}	 
	public void setLongitud( double dir_longitud ) {
	  this.dir_longitud = dir_longitud;
	}
	
	//Boolean********************************************************************************************	
	//Higiene
	public boolean getHigiene() {
	  return higiene;
	}	 
	public void setHigiene( boolean higiene ) {
	  this.higiene = higiene;
	}
	
	//Aire_acondiconado
	public boolean getAire_acond() {
	  return aire_acondicionado;
	}	 
	public void setAire_acond( boolean aire_acondicionado ) {
	  this.aire_acondicionado = aire_acondicionado;
	}
	
	//Calefacion
	public boolean getCalefaccion() {
	  return calefaccion;
	}	 
	public void setCalefaccion( boolean calefaccion ) {
	  this.calefaccion = calefaccion;
	}
	
	//Cocina
	public boolean getCocina() {
	  return cocina;
	}	 
	public void setCocina( boolean cocina ) {
	  this.cocina = cocina;
	}
	
	//Internet
	public boolean getInternet() {
	  return internet;
	}	 
	public void setInternet( boolean internet ) {
	  this.internet = internet;
	}
	
	//Piscina
	public boolean getPiscina() {
	  return piscina;
	}	 
	public void setPiscina( boolean piscina ) {
	  this.piscina = piscina;
	}
	
	//Lavadora
	public boolean getLavadora() {
	  return lavadora;
	}	 
	public void setLavadora( boolean lavadora ) {
	  this.lavadora = lavadora;
	}
	
	//Desayuno
	public boolean getDesayuno() {
	  return desayuno;
	}	 
	public void setDesayuno( boolean desayuno ) {
	  this.desayuno = desayuno;
	}
	
	//Estacionamiento
	public boolean getEstacionamiento() {
	  return estacionamiento;
	}	 
	public void setEstacionamiento( boolean estacionamiento ) {
	  this.estacionamiento = estacionamiento;
	}
	
	//Mascota
	public boolean getMascota() {
	  return mascota;
	}	 
	public void setMascota( boolean mascota ) {
	  this.mascota = mascota;
	}
	
	//Quincho
	public boolean getQuincho() {
	  return quincho;
	}	 
	public void setQuincho( boolean quincho ) {
	  this.quincho = quincho;
	}
	
	//Tv
	public boolean getTv() {
	  return tv;
	}	 
	public void setTv( boolean tv ) {
	  this.tv = tv;
	}
	
	//Telefono
	public boolean getTelefono() {
	  return telefono;
	}	 
	public void setTelefono( boolean telefono ) {
	  this.telefono = telefono;
	}	
}
