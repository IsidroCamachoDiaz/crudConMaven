package arquitecturaBaseDeDatos.controladores;

import java.sql.Connection;
import java.sql.SQLException;

import arquitecturaBaseDeDatos.servicios.implementacionConexion;
import arquitecturaBaseDeDatos.servicios.interfazConexion;

public class principal {

	public static void main(String[] args) {
		interfazConexion icon = new implementacionConexion();
		Connection con =icon.conexionBaseDeDatos();
		try {
		if(con.isValid(3000))
			System.out.println("La conexion esta operativa");
		}catch(SQLException sqle) {
			System.out.println("Hubo problemas con la conexion");
		}

	}

}
