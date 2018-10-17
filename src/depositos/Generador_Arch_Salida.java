package depositos;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Generador_Arch_Salida {
	String output;
	ArrayList<Deposito> arr;
	int vol_a_ing;
	
	public Generador_Arch_Salida(String input, String output) throws FileNotFoundException {
		this.output = output;
		this.arr = new ArrayList<Deposito>();
		Procesador_Entrada p = new Procesador_Entrada(input);
		this.vol_a_ing=p.procesar(arr);
	}
	
	public int desborde() {
		int v_tot=0;
		for(Deposito x:arr)
			v_tot+=x.vmax;
		return v_tot-vol_a_ing;//preguntar si da negativo y escribir el valor absoluto
	}
	public void generar_salida() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(output));
		int desborde = this.desborde();
		if (desborde < 0) {
			pw.println("Rebasan: " + Math.abs(desborde));
			pw.close();
			return;
		}
		
		pw.close();
	}
	
}
