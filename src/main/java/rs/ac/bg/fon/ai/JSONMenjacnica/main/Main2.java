package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

//http://api.currencylayer.com/historical?access_key=2e4baadf5c5ae6ba436f53ae5558107f&date=2020-07-04&source=USD&currencies=EUR
//http://api.currencylayer.com/historical?access_key=2e4baadf5c5ae6ba436f53ae5558107f&date=2020-07-04&source=USD&currencies=CAD
//http://api.currencylayer.com/historical?access_key=2e4baadf5c5ae6ba436f53ae5558107f&date=2020-07-04&source=USD&currencies=CHF

public class Main2 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES1 = "EUR";
	private static final String CURRENCIES2 = "CAD";
	private static final String CURRENCIES3 = "CHF";
	private static final int VALUE = 100;
	private static final String DATUMKURSA = "2020-07-04";

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static double zamenaKursaHistorical(double pocetniIznos, String method, String endpoint, String currencies)
			throws Exception {
		double konvertovano = -1;
		try {
			URL url = new URL(BASE_URL + "/" + endpoint + "?access_key=" + API_KEY + "&date=" + DATUMKURSA + "&source="
					+ SOURCE + "&currencies=" + currencies);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JsonObject respond = gson.fromJson(in, JsonObject.class);
			if (respond.get("success").getAsBoolean()) {
				double kurs = respond.get("quotes").getAsJsonObject().get(SOURCE + currencies).getAsDouble();
				konvertovano = kurs * pocetniIznos;
				return konvertovano;
			} else {
				throw new Exception("Can't connect to API");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String[] args) {

		Transakcija t1 = new Transakcija(SOURCE, CURRENCIES1, VALUE, -1, DATUMKURSA);
		Transakcija t2 = new Transakcija(SOURCE, CURRENCIES2, VALUE, -1, DATUMKURSA);
		Transakcija t3 = new Transakcija(SOURCE, CURRENCIES3, VALUE, -1, DATUMKURSA);

		try {
			t1.setKonvertovaniIznos(
					zamenaKursaHistorical(t1.getPocetniIznos(), "GET", "historical", t1.getKrajnjaValuta()));
			t2.setKonvertovaniIznos(
					zamenaKursaHistorical(t2.getPocetniIznos(), "GET", "historical", t2.getKrajnjaValuta()));
			t3.setKonvertovaniIznos(
					zamenaKursaHistorical(t3.getPocetniIznos(), "GET", "historical", t3.getKrajnjaValuta()));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doslo je do greske sa komunikacion API servisa!");
		}

		Transakcija[] transakcije = { t1, t2, t3 };

		try (FileWriter file = new FileWriter("ostale_transakcije.json")) {
			gson.toJson(transakcije, file);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doslo je do greske pisanja Json fajla!");
		}
	}
}
