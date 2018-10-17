package depositos;

public class Deposito {
	int sup_base;
	int prof;
	double volumen;
	double vmax;
	double vol_x_metro;
	
	public Deposito(int sup_base, int prof) {
		this.sup_base = sup_base;
		this.prof = prof;
		this.volumen = 0;
		this.vmax = sup_base*prof;
		this.vol_x_metro = this.vmax/prof;
	}
	
	
}
