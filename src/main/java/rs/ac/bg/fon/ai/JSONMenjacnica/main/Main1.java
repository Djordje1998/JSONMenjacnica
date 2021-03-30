package rs.ac.bg.fon.ai.JSONMenjacnica.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import rs.ac.bg.fon.ai.JSONMenjacnica.transakcija.Transakcija;

public class Main1 {

	// http://api.currencylayer.com/live?access_key=2e4baadf5c5ae6ba436f53ae5558107f&source=USD&currencies=EUR
	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "2e4baadf5c5ae6ba436f53ae5558107f";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "CAD";
	private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public static void main(String[] args) {

		Transakcija t1 = new Transakcija();
		t1.setIzvnornaValuta(SOURCE);
		t1.setKrajnjaValuta(CURRENCIES);
		t1.setDatumTransakcije(format.format(new Date()));
		t1.setPocetniIznos(411);
		double konvertovano = -1;
		
		try {
			Gson gson = new Gson(); 
			URL url = new URL(BASE_URL + "/live?access_key=" + API_KEY + "&source=" + SOURCE + "&currencies=" + CURRENCIES);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JsonObject respond = gson.fromJson(in, JsonObject.class);
			if(respond.get("success").getAsBoolean()) {
				double kurs = respond.get("quotes").getAsJsonObject().get(SOURCE+CURRENCIES).getAsDouble();
				konvertovano = kurs * t1.getPocetniIznos();
				t1.setKonvertovaniIznos(konvertovano);
			}else {
				throw new Exception("Can't connect to API");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(t1);
		
	}

}
