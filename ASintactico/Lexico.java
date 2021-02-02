package ASintactico;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ASintactico.Token.Tipos;

public class Lexico {
	
	String[] lexemas;
	public static ArrayList<String> valor = new ArrayList<String>();
	public static ArrayList<Tipos> lexema = new ArrayList<Tipos>();
	public static ArrayList<String> tipo = new ArrayList<String>();
	static Boolean ban_seguir=true,ban_int=false,ban_float=false,ban_char=false;
	
	public Lexico(String cadena) {
		if(cadena.equals("")) {
			System.out.println("Cadena vacía");
		}else{
			cadena=cadena+" $";
			cadena=cadena.trim();
			lexer(cadena);
		}
	}
	
	private void lexer(String input) {
        final ArrayList<Token> tokens = new ArrayList<Token>();
        final StringTokenizer st = new StringTokenizer(input);

        while(st.hasMoreTokens()) {
            String palabra = st.nextToken();
            boolean matched = false;

            for (Tipos tokenTipo : Tipos.values()) {
                Pattern patron = Pattern.compile(tokenTipo.patron);
                Matcher matcher = patron.matcher(palabra);
                if(matcher.find()) {
                    Token tk = new Token();
                    tk.setTipo(tokenTipo);
                    tk.setValor(palabra);
                    tokens.add(tk);
                    matched = true;
                    lexema.add(tk.getTipo());
                    valor.add(tk.getValor());
                    //System.out.println("(" + tk.getTipo() + ": " + tk.getValor() + ")");
                }
            }

            if (!matched) {
                System.out.println("Se encontró un token invalido: " + palabra);
                ban_seguir=false;
            }
        }
        if(ban_seguir) {
        	asignaTipo();
        	tablaSimbolos();
        	System.out.println("\nAnálisis léxico terminado exitosamente\n");
        	Sintactico sintac = new Sintactico();
        }else {
        	System.out.println("\nAnálisis léxico terminado con errores");
        	System.exit(0);
        }
    }

	private void asignaTipo() {
		for(int i=0;i<lexema.size();i++) {
			if(lexema.get(i).toString().equals("flotante")) {
				ban_float=true;
				tipo.add("float");
			}
			if(lexema.get(i).toString().equals("entero")) {
				ban_int=true;
				tipo.add("int");
			}
			if(lexema.get(i).toString().equals("caracter")) {
				ban_char=true;
				tipo.add("char");
			}
			if(lexema.get(i).toString().equals("id")) {
				if(ban_float==true)
					tipo.add("float");
				else
					if(ban_int==true)
						tipo.add("int");
					else
						if(ban_char==true)
							tipo.add("char");
						else
							buscaAsigna(valor.get(i),i);
			}
			if(lexema.get(i).toString().equals("pcoma")) {
				if(ban_float==true)
					ban_float=false;
				else
					if(ban_int==true)
						ban_int=false;
					else
						if(ban_char==true)
							ban_char=false;
				tipo.add(";");
			}
			if(lexema.get(i).toString().equals("coma")) {
				tipo.add(",");
			}
			if(lexema.get(i).toString().equals("suma")) {
				tipo.add("+");
			}
			if(lexema.get(i).toString().equals("resta")) {
				tipo.add("-");
			}
			if(lexema.get(i).toString().equals("mult")) {
				tipo.add("*");
			}
			if(lexema.get(i).toString().equals("div")) {
				tipo.add("/");
			}
			if(lexema.get(i).toString().equals("igual")) {
				tipo.add("=");
			}
			if(lexema.get(i).toString().equals("abreP")) {
				tipo.add("(");
			}
			if(lexema.get(i).toString().equals("cierraP")) {
				tipo.add(")");
			}
			if(lexema.get(i).toString().equals("$")) {
				tipo.add("fin");
			}
		}
	}
	
	private void buscaAsigna(String val, int lim) {
		boolean ban_id=false;
		for(int i=0;i<lim;i++) {
			if(valor.get(i).equals(val)) {
				tipo.add(tipo.get(i));
				ban_id=true;
				i=lim;
			}
		}
		if(!ban_id)
			tipo.add("sd");
	}
	
	private void tablaSimbolos() {
		System.out.println("\n-- Tabla de simbolos--");
		for(int i=0;i<lexema.size();i++) {
			System.out.println(lexema.get(i)+" --> "+valor.get(i)+" --> "+tipo.get(i));
		}
		
	}
	
}
