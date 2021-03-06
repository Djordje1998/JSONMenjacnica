package rs.ac.bg.fon.ai.JSONMenjacnica.transakcija;

public class Transakcija {

	private String izvnornaValuta;
	private String krajnjaValuta;
	private double pocetniIznos;
	private double konvertovaniIznos;
	private String datumTransakcije;

	public Transakcija() {
		super();
	}

	public Transakcija(String izvnornaValuta, String krajnjaValuta, double pocetniIznos, double konvertovaniIznos,
			String datumTransakcije) {
		super();
		this.izvnornaValuta = izvnornaValuta;
		this.krajnjaValuta = krajnjaValuta;
		this.pocetniIznos = pocetniIznos;
		this.konvertovaniIznos = konvertovaniIznos;
		this.datumTransakcije = datumTransakcije;
	}

	public String getIzvnornaValuta() {
		return izvnornaValuta;
	}

	public void setIzvnornaValuta(String izvnornaValuta) {
		this.izvnornaValuta = izvnornaValuta;
	}

	public String getKrajnjaValuta() {
		return krajnjaValuta;
	}

	public void setKrajnjaValuta(String krajnjaValuta) {
		this.krajnjaValuta = krajnjaValuta;
	}

	public double getPocetniIznos() {
		return pocetniIznos;
	}

	public void setPocetniIznos(double pocetniIznos) {
		this.pocetniIznos = pocetniIznos;
	}

	public double getKonvertovaniIznos() {
		return konvertovaniIznos;
	}

	public void setKonvertovaniIznos(double konvertovaniIznos) {
		this.konvertovaniIznos = konvertovaniIznos;
	}

	public String getDatumTransakcije() {
		return datumTransakcije;
	}

	public void setDatumTransakcije(String datumTransakcije) {
		this.datumTransakcije = datumTransakcije;
	}

	@Override
	public String toString() {
		return "Transakcija [izvnornaValuta=" + izvnornaValuta + ", krajnjaValuta=" + krajnjaValuta + ", pocetniIznos="
				+ pocetniIznos + ", konvertovaniIznos=" + konvertovaniIznos + ", datumTransakcije=" + datumTransakcije
				+ "]";
	}

}
