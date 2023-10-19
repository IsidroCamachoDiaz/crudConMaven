package accionesBasicasBaseDeDatos.servicios;

public interface interfazAccionesPrincipal {
	/**
	 * Metodo que inserta los libros en la base de datos
	 * */
	public void CrearDatos();
	/**
	 * Metodo que hace las consultas en la base de datos y los mete en listas
	 * */
	public void LeerDatos();
	/**
	 * Metodo que actualiza los libros que hay dentro de la base de datos
	 * */
	public void ActualizarDatos();
	/**
	 * Metodo que borra los libros que hay insertado en la base de datos
	 * */
	public void BorrarDatos();
}
