package accionesBasicasBaseDeDatos.servicios;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import accionesBasicasBaseDeDatos.dtos.Libro;
import accionesBasicasBaseDeDatos.util.Util;

public class implementacionAccionesPrincipales implements interfazAccionesPrincipal {

	@Override
	public void CrearDatos() {
		//Preguntamos cuantso quiere crear
		int creaciones = Util.CapturaEntero("Cuantos entidades quiere crear:", 1, 100);
		//Creamos la lista donde meteremos lsoq ue vamos a crear
		List <Libro> librosCrear= new ArrayList <Libro> ();
		//Creamos la interfaz de las acciones
		interfazAccionesBaseDeDatos inter = new implementacionAccionesBaseDeDatos();
		try {
			//Conectamos
		Connection c=inter.conexionBaseDeDatos();
		Scanner leer= new Scanner(System.in);
		//Creamos un bucle donde inremos preguntamos los valores que iremos metiendo en la lista
		for(int i=0;i<creaciones;i++) {
			System.out.println("Titulo del libro");
			String titulo=leer.next();
			System.out.println("Nombre del autor del libro");
			String autor=leer.next();
			System.out.println("ISBN del libro");
			String sdbn=leer.next();
			int edicion=Util.CapturaEntero("Ediccion del libro", 1, 100);
			librosCrear.add(new Libro(0,titulo,autor,sdbn,edicion));
		}
		//Usamos la accion de insertar
		inter.insertarDatos(librosCrear, c);
		System.out.println("Se han creado los "+librosCrear.size()+" libros");
		//Cerramos conexion
		inter.cerrarBaseDeDatos(c);
		//Excepciones
		}catch(NoSuchElementException ns) {
			System.out.println("Error no puso el tipo indicado: "+ns.getMessage());
		}catch(Exception e) {
			System.out.println("Error en la implementacion de acciones principales: "+e.getMessage());
		}
	}

	@Override
	public void LeerDatos() {
		//Preguntamos la opcion
		int opcion = Util.CapturaEntero("Que sea ver:\n1-Todos los datos\n2-Filtar\n", 1, 2);
		//Creamos la interfaz
		interfazAccionesBaseDeDatos inter = new implementacionAccionesBaseDeDatos();
		Scanner leer = new Scanner (System.in);
		try {
			//Conectamos
		Connection c=inter.conexionBaseDeDatos();
		//Comprobamos que este disponible
		if(c!=null) {
			//Se cre ala lista donde ira el resultado
		List <Libro> libros;
		switch(opcion) {
			case 1:
				//Creamos la query en el parametro de la interfaz
				libros=inter.insertarQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros", c);
				//Muestra
				MostrarDatos(libros);
				break;
			case 2:
				//Preguntamos que quiere filtar
				int opcion2 = Util.CapturaEntero("Por que desea Filtar:\n1-ID\n2-Autor\n", 1, 2);
				switch(opcion2) {
				case 1:
					//Pedimos id
					System.out.println("Por que id desea filtrar");
					long id=leer.nextLong();
					//Creamos la query en el parametro de la interfaz
					libros=inter.insertarQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros WHERE	id_libro="+id, c);
					//Si no se encuentra
					if(libros.isEmpty())
						System.out.println("No se encontro esa id en la base de datos");
					//Si se encuentra se muestra
					else
						MostrarDatos(libros);
					break;
				case 2:
					//Se piede el autor
					System.out.println("Introduzca el nombre del autor que desea filtar");
					String autor = leer.next();
					//Creamos la query en el parametro de la interfaz
					libros=inter.insertarQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros WHERE	autor='"+autor+"'", c);
					//Si no se encuentra
					if(libros.isEmpty())
						System.out.println("No se encontro el autor en la base de datos");
					//Si se encuentra se muestra
					else
						MostrarDatos(libros);
					break;
				}
				break;
			}
		}
		//Si la conexion esta nula
		else
			System.out.println("Dio problemas la conexion");
		//Excepciones
		}catch(Exception e) {
			System.out.println("Error en la implementacion de Acciones Principales "+e.getMessage());
		}

	}

	@Override
	public void ActualizarDatos() {
		//Creamos la interfaz
		interfazAccionesBaseDeDatos inter = new implementacionAccionesBaseDeDatos();
		try {
		//Conectamos
		Connection c=inter.conexionBaseDeDatos();
		Scanner leer= new Scanner(System.in);
		//Si conexion no es nula
		if(c!=null) {
			//Creamos la lista
			List <Libro> libros;
			//Creamos la query en el parametro de la interfaz
			libros=inter.insertarQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros", c);
			//Muestra para el que quiere actualizar
			MostrarDatos(libros);
			//se pide el que quiere actualizar
			int actualizar=Util.CapturaEntero("Introduzca el id del libro que quiere actualizar", 1, 1000);
			int posicion=0;
			//Si hemos borramos anteriormente los id seran mayores que los de lista
			//Compruebo que posisicon tiene el id que quiere actualizar
			for(int i=0;i<libros.size();i++) {
				if(actualizar==libros.get(i).getIdLibro()) {
					posicion=i;
				}
			}
			//Pedimos los valores
			System.out.println("Titulo del libro");
			libros.get(posicion).setTitulo(leer.next());
			System.out.println("Autor del libro");
			libros.get(posicion).setAutor(leer.next());
			System.out.println("ISBN del libro");
			libros.get(posicion).setIsbn(leer.next());
			libros.get(posicion).setEdicion(Util.CapturaEntero("Edicion del Libro", 1, 100));
			//Actualizmos y metemos el libro que quremos actualiza
			inter.ActualizarDatos(libros.get(posicion), c);						
		}
		//Si no hay Conexion
		else {
			System.out.println("Hubo problemas en la conexion de base de datos");
		}
		//Desconectamos
		inter.cerrarBaseDeDatos(c);
		//Excepciones
		}catch(Exception e) {
			System.out.println("Error en la implementacion de Acciones Principales "+e.getMessage());
		}
		
	}

	@Override
	public void BorrarDatos() {
		//Creamos la implemntacion
		interfazAccionesBaseDeDatos inter = new implementacionAccionesBaseDeDatos();
		try {
			//Conectamos
		Connection c=inter.conexionBaseDeDatos();
		Scanner leer= new Scanner(System.in);
		//Comprobamos conexion
		if(c!=null) {			
			List <Libro> libros;
			libros=inter.insertarQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros", c);
			MostrarDatos(libros);
			int borrar=Util.CapturaEntero("Introduzca el id del libro que quiere actualizar", 1, 1000);
			//Si hemos borramos anteriormente los id seran mayores que los de lista
			//Compruebo que posisicon tiene el id que quiere actualizar
			int posicion=0;
			for(int i=0;i<libros.size();i++) {
				if(borrar==libros.get(i).getIdLibro()) {
					posicion=i;
				}
			}
			//Creamos este string para verificar que lo quiere borrar
			String seguridad="";
			do {
				System.out.println("Esta seguro de querer borrar este libro?\nPonga SI QUIERO en mayusculas");
				seguridad=leer.next();
			}while(seguridad.equals("SI QUIERO"));
			//Borramos libro
			inter.BorrarDatos(libros.get(posicion), c);	
			System.out.println("El libro se borro correctamente");
		}
		//Si no fuera la conexion
		else {
			System.out.println("Hubo problemas en la conexion de base de datos");
		}
		//Desconectamos
		inter.cerrarBaseDeDatos(c);
		//Excepciones
		}catch(Exception e) {
			System.out.println("Error en la implementacion de Acciones Principales "+e.getMessage());
		}

	}
	//Este metodo sirve para mostrar todos lo libros
	private void MostrarDatos(List <Libro> libros) {
		//Creamos un bucle para enseñar cada libro
		for(int i=0;i<libros.size();i++) {
			System.out.println(libros.get(i).toString());
		}
	}

}
