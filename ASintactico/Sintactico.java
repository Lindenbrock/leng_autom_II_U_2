package ASintactico;

import java.util.ArrayList;
import java.util.Stack;

import ASintactico.Token.Tipos;

public class Sintactico {
	
	ArrayList<Tipos> lex = Lexico.lexema;
	ArrayList<String> type = Lexico.tipo;
	public static ArrayList<String> cad_sem = new ArrayList<String>();
	Stack<String> p_ent = new Stack();
	Stack<String> p_edo = new Stack();
	Boolean ban_proc = false,ban_sem=false;
	int pos=0;
	
	String edo_proc="0";
	
	public Sintactico() {
		for(int i=lex.size()-1;i>=0;i--) {
			p_ent.push(lex.get(i).toString());
		}
		
		p_edo.push("0");
		
		//int var1 , var2 ; char var3 ; var2 = var4 + var3 ;
		//int var1 , var2 ; var2 = var1 + ( var1 - ( var1 + var1 ) ) ;
				
		do {
			System.out.println(p_ent + " | " + p_edo);
			edo_proc=buscarEdoProc(p_ent.lastElement(),p_edo.lastElement());
			if(edo_proc.equals("P0")) 
				ban_proc=true;
			else 
				if(edo_proc.equals("P1"))
					reduceP(6,"P");
				 else 
					 if(edo_proc.equals("P2"))
					  	 reduceP(2,"P");
				 	  else 
				 		  if(edo_proc.equals("P3") || edo_proc.equals("P4") || edo_proc.equals("P5"))
				 		   	  reduceP(2,"Tipo");
				 	 	   else 
				 	 		   if(edo_proc.equals("P6"))
				 	 		   	   reduceP(6,"V");
				 	 	   		else 
				 	 	   			if(edo_proc.equals("P7"))
				 	 	   				reduceP(4,"V");
				 	 	   			else 
				 	 	   				if(edo_proc.equals("P8"))
				 	 	   					reduceP(8,"A");
				 	 	   				 else 
				 	 	   					 if(edo_proc.equals("P9") || edo_proc.equals("P10"))
				 	 	   					 	 reduceP(6,"E");
				 	 	   				 	  else 
				 	 	   				 		  if(edo_proc.equals("P11"))
				 	 	   				 		  	  reduceP(2,"E");
				 	 	   				 		  else
				 	 	   				 			  if(edo_proc.equals("P12") || edo_proc.equals("P13"))
				 	 	   						          reduceP(6,"T");
				 	 	   				 			  else
				 	 	   				 				  if(edo_proc.equals("P14"))
				 	 	   				 					  reduceP(2,"T");
				 	 	   				 				  else
				 	 	   				 					  if(edo_proc.equals("P15"))
				 	 	   				 						  reduceP(6,"F");
				 	 	   				 					  else
				 	 	   				 						  if(edo_proc.equals("P16"))
				 	 	   				 							  reduceP(2,"F");
				 	 	   				 						  else if(edo_proc.equals("")) {
				 	 	   				 							  	   System.out.println("Error sintáctico");
				 	 	   				 							  	   ban_proc=true;
				 	 	   				 						  	   } else {
				 	 	   				 						  		   	if(p_ent.size()>2) {
				 	 	   				 						  		   		if(p_ent.get(p_ent.size()-2).equals("igual")) {
				 	 	   				 						  		   			ban_sem=true;
				 	 	   				 						  		   		}
				 	 	   				 						  		   		pilaSemantica();
				 	 	   				 						  		   	}
				 	 	   				 						  		   	p_edo.push(p_ent.lastElement());
				 	 	   				 						  		   	p_edo.push(edo_proc);
				 	 	   				 						  		   	p_ent.pop();
				 	 	   				 						  	   }
			
			if(p_ent.isEmpty())
				ban_proc=true;
		}while(ban_proc==false);
		//System.out.println(cad_sem);
		if(p_ent.size()==1) {
			System.out.println("\nAnalisis sintáctico terminado exitosamente\n");
			Semantico sm = new Semantico();
		} else {
			System.out.println("\nAnálisis sintáctico terminado con errores\n");
			System.exit(0);
		}
	}

	private String buscarEdoProc(String lastElement, String lastElement2) {
		String p_cad=lastElement,p_edo=lastElement2,ep="";
		int pos_lex=0;
		int pos_edo=0;
		for(int i=0;i<Tabla.tablaAsintactico[0].length;i++) {
			if(Tabla.tablaAsintactico[0][i].equals(p_cad))pos_lex=i;
		}
		for(int i=0;i<Tabla.tablaAsintactico.length;i++) {
			if(Tabla.tablaAsintactico[i][0].equals(p_edo))pos_edo=i;
		}
		ep=Tabla.tablaAsintactico[pos_edo][pos_lex];
		return ep;
	}
	
	private void reduceP(int npop,String pr) {
		for(int i=0;i<npop;i++)
			p_edo.pop();
		p_edo.push(pr);
		edo_proc=buscarEdoProc(p_edo.lastElement(),p_edo.get(p_edo.size()-2));
		p_edo.push(edo_proc);
	}
	
	private void pilaSemantica() {
		if(ban_sem) {
			pos=type.size()-(p_ent.size());
			String t=type.get(pos);
			//if(t.equals("int") || t.equals("float") || t.equals("char") || t.equals("sd")) {
			cad_sem.add(t);			
		}		
	}
	
}
