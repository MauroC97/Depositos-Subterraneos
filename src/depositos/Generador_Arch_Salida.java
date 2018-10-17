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
	int depositos_cargados;
	int dist_a_superficie;
	
	public Generador_Arch_Salida(String input, String output) throws FileNotFoundException {
		this.output = output;
		this.arr = new ArrayList<Deposito>();
		Procesador_Entrada p = new Procesador_Entrada(input);
		this.vol_a_ing=p.procesar(arr);
		this.depositos_cargados=0;
	}
	
	public int desborde() {
		int v_tot=0;
		for(Deposito x:arr)
			v_tot+=x.vmax;
		return v_tot-vol_a_ing;//preguntar si da negativo y escribir el valor absoluto
	}
	public void llenar_depositos() {
		int total_dep = arr.size();
		int mts_a_emparejar;
		double vol_x_m_total=0;
		int i,j;
		Deposito d,d_sig;
	/* 			manejo del primer deposito 		*/	
		this.depositos_cargados++;
		d=arr.get(0);
		d_sig=arr.get(1);
		mts_a_emparejar=d.prof-d_sig.prof;
		vol_x_m_total+=d.vol_x_metro;
		if(vol_a_ing < vol_x_m_total*mts_a_emparejar)
		{
			d.volumen+=vol_a_ing;
			this.dist_a_superficie= (int)(d.volumen/d.vol_x_metro);
			return;
		}
		d.volumen+=vol_x_m_total*mts_a_emparejar;
		vol_a_ing-=vol_x_m_total*mts_a_emparejar;
	/* 		manejo de todos los depositos intermedios 	*/
		for(i=1;i<total_dep-1;i++)
		{
			this.depositos_cargados++;
			d=arr.get(i);
			d_sig=arr.get(i+1);
			mts_a_emparejar=d.prof-d_sig.prof;
			vol_x_m_total+=d.vol_x_metro;
			if(vol_a_ing < vol_x_m_total*mts_a_emparejar)
			{
				vol_a_ing/=vol_x_m_total;//ahora son los metros que puedo ingresar con el volumen que me queda
				for (j=0;j<i;j++)
				{
					d=arr.get(j);
					d.volumen+=d.vol_x_metro*(int)vol_a_ing;
				}
				this.dist_a_superficie= (int)(d.volumen/d.vol_x_metro);
				return;
			}
			for (j=0;j<i;j++)
			{
				d=arr.get(j);
				d.volumen+=d.vol_x_metro*mts_a_emparejar;
				vol_a_ing-=d.vol_x_metro*mts_a_emparejar;
			}
		}
	/* manejo del volumen restante al emparejar todos los depositos */
		d=arr.get(total_dep-1);
		mts_a_emparejar=d.prof;
		vol_x_m_total+=d.vol_x_metro;
		vol_a_ing/=vol_x_m_total;//ahora son los metros que puedo ingresar con el volumen que me queda
		for (Deposito x: arr)
		{
			x.volumen+=x.vol_x_metro*(int)vol_a_ing;
		}
		this.depositos_cargados=total_dep;
		this.dist_a_superficie= (int)(d.volumen/d.vol_x_metro);
		return;
		
	}
	public void generar_salida() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(output));
		int desborde = this.desborde();
		if (desborde < 0) {
			pw.println("Rebasan: " + Math.abs(desborde));
			pw.close();
			return;
		}
		this.llenar_depositos();
		
		
		pw.close();
	}
	
}
