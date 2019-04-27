# Simple Mashup-web
Show a message using different APIs, implemented in Java and executed in Eclipse with TomcatApache9. 

![image](https://user-images.githubusercontent.com/36509669/56841382-b18d0f80-688d-11e9-8753-118bd27ae409.png)


It shows the temperature of the origin and destination (cities), the country they belong to, the currency code they use and the equivalent amount. All this depends on the time of day and, of course, on the correct functioning of the APIs.
## About the code
- It is perfectly commented in es.upm.dit.isst.soa.Test.java
## APIs used
- https://restcountries.eu    
no apiKey is neccesary
- https://openweathermap.org/api   
maybe you need a new apiKey (is neccesary)
- https://free.currencyconverterapi.com/
maybe you need a new apiKey (is neccesary). My apiKey (created in 26/04/2019) expires in 60 days (I've used a free version).
## How does it works
- Download repository .zip
- Import in eclipse.
- Update Maven: Rigth click on project > select Maven > Update Project. This is to eliminate "errors" in project.
- Include Tomcat (Maybe you download newer version).
- Run on server.
- Open this in your browser: http://localhost:8080/ISST-19-rest/rest/getTrip?origen=Madrid&destino=London .You are free to change the 'origen' (origin) and 'destino' (destination) parameters to other cities (must be put both in english).

