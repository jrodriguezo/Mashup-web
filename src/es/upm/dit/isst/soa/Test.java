package es.upm.dit.isst.soa;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;

import org.json.JSONArray;
import org.json.JSONObject;

//se prueba en esta ruta: http://localhost:8080/ISST-19-rest/rest/getTrip?origen=Madrid&destino=London
@Path("getTrip")
public class Test {

	@GET 
	@Produces({"text/html"})
	public String getTrip(@QueryParam("origen") String origen, 
						  @QueryParam("destino") String destino){
		
		
		Client client = ClientBuilder.newClient();
		//peticion construida https://restcountries.eu/rest/v2/capital/origen
		String cOrigen = client.target("https://restcountries.eu/rest/v2/capital")
		        .path(origen)
				.request()
		        .get(String.class);
		//peticion construida https://restcountries.eu/rest/v2/capital/destino
		String cDestino = client.target("https://restcountries.eu/rest/v2/capital")
		        .path(destino)
				.request()
		        .get(String.class);
		//peticion construida http://api.openweathermap.org/data/2.5/weather?q=origen&appid=8ab34a232d8758dc83f79ae5632fb563&units=metric
		String tOrigen = client.target("http://api.openweathermap.org/data/2.5/weather")
				.queryParam("q", origen)
				.queryParam("appid", "8ab34a232d8758dc83f79ae5632fb563")
			    .queryParam("units", "metric")
				.request()
				.get(String.class);
		//peticion construida http://api.openweathermap.org/data/2.5/weather?q=destino&appid=8ab34a232d8758dc83f79ae5632fb563&units=metric
		String tDestino = client.target("http://api.openweathermap.org/data/2.5/weather")
				.queryParam("q", destino)
				.queryParam("appid", "8ab34a232d8758dc83f79ae5632fb563")
			    .queryParam("units", "metric")
				.request()
				.get(String.class);
		
		
		//Recuperamos los JSON
			//si viene en un array
			//si viene en un objeto (diccionario)
		JSONArray arr1 = new JSONArray(cOrigen);
		JSONArray arr2 = new JSONArray(cDestino);
		JSONObject jsn3 = new JSONObject(tOrigen);
		JSONObject jsn4 = new JSONObject(tDestino);
		
		//Sacamos los parametros deseados
		String ciudadO = arr1.getJSONObject(0).getString("capital");
		String ciudadD = arr2.getJSONObject(0).getString("capital");
		String paisO = arr1.getJSONObject(0).getString("name");
		String paisD = arr2.getJSONObject(0).getString("name");
		String monedaO = arr1.getJSONObject(0).getJSONArray("currencies").getJSONObject(0).getString("code");
		String monedaD = arr2.getJSONObject(0).getJSONArray("currencies").getJSONObject(0).getString("code");
		String temperaturaO = jsn3.getJSONObject("main").get("temp").toString();
		String temperaturaD = jsn4.getJSONObject("main").get("temp").toString();
		
		//necesitamos utilizar las variables anteriores para sacar la siguiente direccion
		//https://free.currconv.com/api/v7/convert?q=monedaO_monedaD&compact=ultra&apiKey=fdb154ff7d15c7dbb7fe
		String currconvOrigDest = client.target("https://free.currconv.com/api/v7/convert")
				.queryParam("q", monedaO+"_"+monedaD)
				.queryParam("compact", "ultra")
			    .queryParam("apiKey", "fdb154ff7d15c7dbb7fe")
				.request()
				.get(String.class);
		
		JSONObject jsn5 = new JSONObject(currconvOrigDest);
		String conversionO = jsn5.get(monedaO+"_"+monedaD).toString();
		client.close();

		//Preparacion de respuestas antes de return
		String linea1 = "Congratulations!! Your next trip is from "+ciudadO+" to "+ciudadD;
		String linea2 = " ."+ciudadO+" is in "+paisO+". The temperature there is "+temperaturaO;
		String linea3 = ", and their currency is "+monedaO+". \n";
		String linea4 = ciudadD+" is in "+paisD+". The temperature there is "+temperaturaD;
		String linea5 = ", and their currency is "+monedaD+". \n";
		String linea6 = "The exchange rate is 1 "+monedaO+" = "+conversionO+" "+ monedaD+" .";
		return linea1+linea2+linea3+linea4+linea5+linea6;
	
	}
}
