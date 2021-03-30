package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

//http://api.currencylayer.com/live?access_key=2e4baadf5c5ae6ba436f53ae5558107f&source=USD&currencies=EUR

public class Main1 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "CAD";
	private static final int VALUE = 411;
	private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static double zamenaKursa(double pocetniIznos, String method, String endpoint) throws Exception {
		double konvertovano = -1;
		try {
			URL url = new URL(BASE_URL + "/" + endpoint + "?access_key=" + API_KEY + "&source=" + SOURCE
					+ "&currencies=" + CURRENCIES);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JsonObject respond = gson.fromJson(in, JsonObject.class);
			if (respond.get("success").getAsBoolean()) {
				double kurs = respond.get("quotes").getAsJsonObject().get(SOURCE + CURRENCIES).getAsDouble();
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

		Transakcija t1 = new Transakcija(SOURCE, CURRENCIES, VALUE, -1, format.format(new Date()));

		try {
			t1.setKonvertovaniIznos(zamenaKursa(t1.getPocetniIznos(), "GET", "live"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doslo je do greske sa komunikacion API servisa!");
		}

		try (FileWriter file = new FileWriter("prva_transakcija.json")) {
			gson.toJson(t1, file);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doslo je do greske pisanja Json fajla!");
		}
	}
}
