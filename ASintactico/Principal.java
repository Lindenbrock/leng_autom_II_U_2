package ASintactico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Principal {
	
	static String string="",cad="";
	static int op=0;
	static BufferedReader lee= new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Escriba la cadena a analizar");
		string=lee.readLine();
		
		/*Principal pl= new Principal();
		pl.start(string);*/
		Lexico lx = new Lexico(string);
		
	}
	
}
