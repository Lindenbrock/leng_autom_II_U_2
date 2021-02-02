package ASintactico;

public class Token {
	
	public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private Tipos tipo;
    private String valor;
	
	public enum Tipos {
		id("^var[\\d*]$"),
	    entero("^int$"),
	    flotante("^float$"),
	    caracter("^char$"),
	    igual("^[=]$"),
	    coma("^[,]$"),
	    pcoma("^[;]$"),
	    suma("^[+]$"),
	    resta("^[-]$"),
	    mult("^[*]$"),
	    div("^[/]$"),
	    abreP("^[(]$"),
	    cierraP("^[)]$"),
	    $("[$]");
		
	    public final String patron;
	    Tipos(String s) {
	        this.patron = s;
	    }
	}
}
