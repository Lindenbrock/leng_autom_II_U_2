package ASintactico;

import java.util.ArrayList;
import java.util.Stack;

public class Semantico {
	Stack<String> p_sem = new Stack();
	Stack<String> p_exp = new Stack();
	ArrayList<String> cad = Sintactico.cad_sem;
	boolean ban_sem=false;
	int ap;
	
	public Semantico() {
		cad.add("$");
		System.out.println(cad);
		ap=0;
		do {
			String element = cad.get(ap);
			if(element.equalsIgnoreCase("sd")) {
				ban_sem=true;
				mensaje("Error: tipo de dato desconocido");
			}else 				
			
			if(element.equals("int") || element.equals("float") || element.equals("char"))
				p_sem.push(element);
			else
				
			if(element.equals("+"))
				casoSumaResta("+");
			else
				
			if(element.equals("-"))
				casoSumaResta("-");
			else
				
			if(element.equals("/"))
				casoMultDiv("/");
			else
				
			if(element.equals("*"))
				casoMultDiv("*");
			else
				
			if(element.equals("("))
				p_exp.push("(");
			else
				
			if(element.equals(")"))
				casoCierraP();
			else
				
			if(element.equals("$"))
				casoFin();
			
			ap++;
			System.out.println(p_sem+"  "+p_exp);
		}while(ban_sem==false);
		
		
		if(p_sem.size()==1 && !p_sem.firstElement().equals("error")) {
			System.out.println("Analisis semántico realizado con éxito");
		}else {
			System.out.println("Analisis semántico realizado: 1 error detectado");
		}
	}

	private void casoFin() {
		while(p_sem.size()>2) {
			if(!p_exp.isEmpty()) {
				if(p_exp.lastElement().equals("("))
					p_exp.pop();
				else {
					aplicaRegla(p_sem.lastElement(),p_sem.get(p_sem.size()-2));
					p_exp.pop();
				}
			}			
		}
		aplicaRegla(p_sem.lastElement(),p_sem.get(p_sem.size()-2));
		ban_sem=true;
		
	}

	private void casoCierraP() {
		if(p_exp.lastElement().equals("("))
			p_exp.pop();
		else { 
			p_exp.pop();
			aplicaRegla(p_sem.lastElement(),p_sem.get(p_sem.size()-2));
		}	
	}

	private void casoMultDiv(String signo) {
		if(p_exp.isEmpty()) {
			p_exp.push(signo);	
		}else {
			if(p_exp.lastElement().equals("/") || p_exp.lastElement().equals("*")) {
				aplicaRegla(p_sem.lastElement(),p_sem.get(p_sem.size()-2));
				p_exp.push(signo);	
			} else 
				p_exp.push(signo);
		}		
	}

	private void casoSumaResta(String signo) {
		if(p_exp.isEmpty()) {
			p_exp.push(signo);
		}else {
			if(p_exp.lastElement().equals("+") || p_exp.lastElement().equals("-") || p_exp.lastElement().equals("*") || p_exp.lastElement().equals("/")) {
				aplicaRegla(p_sem.lastElement(),p_sem.get(p_sem.size()-2));
				p_exp.push(signo);
			} else
				p_exp.push(signo);
		}
		
				
	}

	private void aplicaRegla(String element1, String element2) {
		int pos1=0,pos2=0;
		char val_regla;
		for(int i=0;i<5;i++)
			if(Tabla.tablaSemantica[0][i].equals(element1))
				pos1=i;
		for(int i=0;i<5;i++)
			if(Tabla.tablaSemantica[0][i].equals(element2))
				pos2=i;
		val_regla=Tabla.tablaSemantica[pos1][pos2].charAt(0);
		if(val_regla=='0') {
			mensaje("Los tipos de dato no coinciden");
			ban_sem=true;
		}else {
			p_sem.pop();
			p_sem.pop();
			p_sem.push(Tabla.tablaSemantica[pos1][pos2].substring(2));
		}
	}
	
	public void mensaje(String mens) {
		System.out.println(mens);
	}
}
