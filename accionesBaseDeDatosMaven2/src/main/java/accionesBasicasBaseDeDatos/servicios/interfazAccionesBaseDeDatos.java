package accionesBasicasBaseDeDatos.servicios;

import java.sql.Connection;
import java.util.List;

import accionesBasicasBaseDeDatos.dtos.Libro;

public interface interfazAccionesBaseDeDatos {
	/**
	 * Conecta con la base de datos mediantes los parametros de .properties
	 * @return Conexion
	 * */
	public Connection conexionBaseDeDatos();
	/**
	 * Hace la query en la base de datos y devuelve la lista de libros del resultado
	 * @return Lista de Libros
	 * */
	public List <Libro> insertarQuery(String query,Connection conexion);
	/**
	 * Cierra la conexiond De Base de Datos
	 * 
	 * */
	public void cerrarBaseDeDatos(Connection conexion);
	/**
	 * Inserta la query de base de datos y crea un libro
	 * */
	public void insertarDatos(List <Libro> libros ,Connection conexion);
	/**
	 *Inserta la query en la Base de datos para actualizar
	 * */
	public void ActualizarDatos(Libro libro1 ,Connection conexion);
	/**
	 * Inserta la query en la Base de datos para borrar un libro
	 * */
	public void BorrarDatos(Libro libro1 ,Connection conexion);
}
