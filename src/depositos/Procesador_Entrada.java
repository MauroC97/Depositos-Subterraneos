package depositos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Procesador_Entrada {
	String input;

	public Procesador_Entrada(String input) {
		this.input = input;
	}
	
	public int procesar(ArrayList<Deposito> arr) throws FileNotFoundException {
		int vol_a_ingresar,tam,i;
		Scanner sc = new Scanner(new File(input));
		tam=sc.nextInt();
		for(i=0;i<tam;i++)
			arr.add(new Deposito(sc.nextInt(),sc.nextInt()));
		vol_a_ingresar=sc.nextInt();
		sc.close();
		return vol_a_ingresar;
	}
}
