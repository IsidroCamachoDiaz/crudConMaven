package accionesBasicasBaseDeDatos.util;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
///Esta clase la uso como herramientas durante varios procesos y la pongo static
public class Util {
	
	//Pedimos un numero y que lo ponga entre los valores
	public static int CapturaEntero(String texto,int min,int max){
		//ponemos una variable boleana para las comprobaciones
		boolean ok=true;
		int num=0;
		Scanner leer = new Scanner (System.in);
		do {
		try {	
			//Pedimos el numero si pone un formato incorrecto cambiara la variable y se 
			//repite el bucle
		System.out.println(texto+"del "+min+" a "+max);
		num=leer.nextInt();
		//Si no pone algun numer de los valores le avisara
			if(num<min||num>max) {
				System.out.println("Error: No puso un numero entre los valores pedidos");
			}
		}catch(NoSuchElementException ns) {
			System.out.println("Error: No puso un valor que no sea un numero");
			break;
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
			ok=false;
			break;
		}
		}while(!ok||num<min||num>max);
		return num;
	}
	//Reccorrera la lista para darle un id a la persona
	//el menu de la aplicacion
	public static void menu() {
		System.out.println("--------------------------------------------------");
		System.out.println("Bienvenido:");
		System.out.println("1-Crear Datos");
		System.out.println("2-Leer Datos");
		System.out.println("3-Actualizar Datos");
		System.out.println("4-Borrar Datos");
		System.out.println("0-Salir");
		System.out.println("--------------------------------------------------");
	}
}
