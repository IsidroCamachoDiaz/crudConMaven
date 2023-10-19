package accionesBasicasBaseDeDatos.controladores;

import java.util.Scanner;


import accionesBasicasBaseDeDatos.util.Util;
import accionesBasicasBaseDeDatos.servicios.interfazAccionesPrincipal;
import accionesBasicasBaseDeDatos.servicios.implementacionAccionesPrincipales;

public class Inicio {
/**
 * Metodo principal de nuestra aplicacion
 * */
	public static void main(String[] args) {
		//Creamos lo necesario
				Scanner leer = new Scanner (System.in);
				int opcion=0;
				interfazAccionesPrincipal inter = new implementacionAccionesPrincipales();
				try {
				//Entra en el bucle 
				do {
					//Muestra el menu
					Util.menu();
					//Cogemos la opcion deseada
					opcion=Util.CapturaEntero("Introduzca una opcion", 0, 4);
					//Comprueba la opcion dependiendo de lo que necesite
					switch(opcion) {
					case 1:
						inter.CrearDatos();
						break;
					case 2:
						inter.LeerDatos();
						break;
					case 3:
						inter.ActualizarDatos();
						break;
					case 4:
						inter.BorrarDatos();
						break;
					}
					
				}while(opcion!=0);
				//Cierra el scanner
				leer.close();
				}catch(Exception e) {
					System.out.println("Error: "+e.getMessage());
				}
	}

}
