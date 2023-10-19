package accionesBasicasBaseDeDatos.servicios;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import accionesBasicasBaseDeDatos.dtos.Libro;

public class implementacionAccionesBaseDeDatos implements interfazAccionesBaseDeDatos {

	@Override
	public Connection conexionBaseDeDatos() {
		//Creamos la conexion
		Connection BaseDatos = null;
		//Cogemos los datos del archivo .propeties
		String [] parametros = pasaParametros("C:\\\\Users\\\\Puesto3\\\\git\\\\pruebaBaseDeDatos\\\\src\\\\modelo\\\\claves.properties");
		try {
			
		 //Se pasa los datos de conexion
	    BaseDatos = DriverManager.getConnection(parametros[0], parametros[1], parametros[2]);
	    //Comprobamos la conexion
	    boolean esValidad=BaseDatos.isValid(5000);
	    //Si no es validad la conexion lo ponemos a null
	    if(!esValidad)
	    	BaseDatos=null;
	    //Excepciones
		}catch(SQLException e) {
			System.out.println("Se produjo un fallo en la conexion de datos en la implementacion"+e.getMessage());
		}
		//Devuelve la co nexion
		return BaseDatos;
	}

	@Override
	public List<Libro> insertarQuery(String query, Connection conexion) {
		//Creamos el stament y el resulset
		Statement st = null;
		ResultSet rs= null;
		//Creamos la lista a null
		List <Libro> libros=null;
		try {
			//Se relaciona la base de datos
		    st = conexion.createStatement();
		    //Se mete la query
		    rs = st.executeQuery(query);
		    //Metemoe el resultado en el metodo que devuelve la lista de libros llena
		    libros=pasaDto(rs);
		    //Cerramos los objetos
		    st.close();
		    rs.close();
		    //Excepciones
		}catch(SQLException eq) {
			System.out.println("Se produjo un fallo en la query en la implementacion de accion de base de datos "+eq.getMessage());
		}
		return libros;
	}

	@Override
	public void cerrarBaseDeDatos(Connection conexion) {
		try {
			//Cerramos la conexion
		conexion.close();
		//Excepciones
		}catch(SQLException e) {
			System.out.println("no se pudo desconectar la  base de datos en la implementacion"+e.getMessage());	
		}
	}

	@Override
	public void insertarDatos(List<Libro> libros, Connection conexion) {
		//Ponemos a null el stament
		PreparedStatement st = null;
		//Esta variable sirve por si hay algun problema dice los libros que se han insertado
		int e=0;
		try {
			//Se relaciona la base de datos
		    //Se hace un bucle para insertar todos los libroos
		    for(int i=0;i<libros.size();i++) {
		    	//Se crea la query
		    	String query2="INSERT INTO gbp_almacen.gbp_alm_cat_libros (titulo,autor,isbn,edicion) VALUES (?,?,?,?)";
		    	st = conexion.prepareStatement(query2);
		    	st.setString(1, libros.get(i).getTitulo());
		    	st.setString(2, libros.get(i).getAutor());
		    	st.setString(3, libros.get(i).getIsbn());;
		    	st.setInt(4, libros.get(i).getEdicion());
		         //Se ejecuta la query
		    	st.executeUpdate();
		    	//Se iguala la variable
		          e=i;
		    }
		    //Se cierra la conexion
		    st.close();
		    //Excepciones
		}catch(SQLException ed) {
			System.out.println("no se insertar los datos en la implementcion"+ed.getMessage()+"Se ha insertado hasta "+e);	
		}

	}

	@Override
	public void ActualizarDatos(Libro libro1, Connection conexion) {
		//Se crea el PreparedStanmet a null
		PreparedStatement st = null;
		try {
			//Se crea la query con los interrogantes donde vaya el valor
			String sql = "UPDATE gbp_almacen.gbp_alm_cat_libros SET titulo=?,autor=?,isbn=?,edicion=? WHERE id_libro=?";
		   //Metemos la query
			st = conexion.prepareStatement(sql);
			//Ponemos los valores donde van los interrogantes
		    st.setString(1, libro1.getTitulo());
		    st.setString(2, libro1.getAutor());
		    st.setString(3, libro1.getIsbn());
		    st.setInt(4, libro1.getEdicion());
		    st.setLong(5, libro1.getIdLibro());
		    //Se ejecuta la query
		    st.executeUpdate();
		    //Se cierra la consulta
		    st.close();
		    //Excepciones
		}catch(SQLException ed) {
			System.out.println("no se ha podido insertar los datos en la implementcion"+ed.getMessage());	
		}

	}
	public void BorrarDatos(Libro libro1 ,Connection conexion) {
		//Se pone el PreparedStament a NULL
		PreparedStatement st = null;
		try {
			//Se crea la query con los interrogantes donde vaya el valor
			String sql = "DELETE FROM gbp_almacen.gbp_alm_cat_libros WHERE id_libro=?";
			 //Metemos la query
		    st = conexion.prepareStatement(sql);
		  //Ponemos los valores donde van los interrogantes
		    st.setLong(1, libro1.getIdLibro());  
		    //eJECUTAMOS LA QUERY
		    st.executeUpdate();
		    //Se cierra la consulta
		    st.close();
		    //Excepciones
		}catch(SQLException ed) {
			System.out.println("no se insertar los datos en la implementcion"+ed.getMessage());	
		}
	}
	//Este metodo se usa para coger los datos de la base de datos
	private String[] pasaParametros(String ruta) {
		//Creamos el objeto properties
		Properties properties= new Properties();
		//Creamos el vector donde van los datos
		String [] parametros = new String [3];
		try {
			//Indicamos donde esta el fichero
			properties.load(new FileInputStream(new File(ruta)));
			//Cogemos los avlores y lso metemos e el vector 
			parametros[0]=properties.getProperty("jdbc");
			parametros[1]=properties.getProperty("USUARIO");
			parametros[2]=properties.getProperty("CLAVE");
		//Excepciones
		}catch(Exception e) {
			System.out.println("Se produjo un error en PasaParemetros de la implementacion "+e.getMessage());
		}
		return parametros;
	}
	//Este metodo sirve paar coger los resultados y merterlos en la lista
	private List<Libro> pasaDto(ResultSet rs){
		//Creamos la lista
		List <Libro> libros = new ArrayList <Libro>();
		try {
			//Creamos el bucle para que vaya cogiendo cada linea y vamos metiendo cada uno en la lista
		 while(rs.next() ) {
			    libros.add( new Libro(rs.getInt("id_libro"),rs.getString("titulo"),rs.getString("autor"),rs.getString("isbn"),rs.getInt("edicion")));
			    }
		 //Excepciones
		}catch(SQLException sq) {
			System.out.println("Se produjo un error de lectura en la implementacion "+sq.getMessage());
		}
		return libros;
		
	}

}
