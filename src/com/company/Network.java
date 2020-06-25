package com.company;

import com.company.DTO.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.lang.reflect.Type;

public class Network {

    public static boolean sendMissle(int x, int y) throws Exception {

        try {
            final CloseableHttpClient client = HttpClients.createDefault();
            final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/ship/user");

            Gson gson = new Gson();

            // Serializacja obiektu do JSONa
            final String json = gson.toJson(new Single2Int(x, y));

            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPost);

            System.out.println("Kod odpowiedzi serwera sendMissle : " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200) {

                final HttpEntity httpEntity = response.getEntity();

                // Na tym etapie odczytujemy JSON'a, ale jako String.
                final String text = EntityUtils.toString(httpEntity);

                final Type type = new TypeToken<SingleBoolean>() {
                }.getType();
                final SingleBoolean singleBoolean = gson.fromJson(text, type);

                client.close();
                return singleBoolean.isValue();
            }
            else
            {
                throw new Exception("Bad reposnse in sendMissle");
            }

        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static void generateMap() throws Exception {

        try
        {
            final CloseableHttpClient client = HttpClients.createDefault();
            final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/ship/reset");
            final CloseableHttpResponse response = client.execute(httpPost);
            System.out.println("Kod odpowiedzi serwera generateMap: " + response.getStatusLine().getStatusCode());
            client.close();
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static Single2Int getMissle() throws Exception {

        try {
            final HttpClient client = HttpClientBuilder.create().build();
            final HttpGet request = new HttpGet("http://127.0.0.1:8080/api/ship/bot");
            final Gson gson = new Gson();

            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            final String json = EntityUtils.toString(entity);
            final Type type = new TypeToken<Single2Int>() { }.getType();
            final Single2Int files = gson.fromJson(json, type);

            System.out.println("Kod odpowiedzi serwera getMissle : " + response.getStatusLine().getStatusCode());

            if(response.getStatusLine().getStatusCode() != 200)
            {
                throw new Exception("Bad reposnse in getMissle");
            }

            return files;

        } catch (IOException e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static void sendMap(Array2Int array2Int) throws Exception {

        try {
            final CloseableHttpClient client = HttpClients.createDefault();
            final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/api/ship/array");
            Gson gson = new Gson();
            final String json = gson.toJson(array2Int);

            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            final CloseableHttpResponse response = client.execute(httpPost);

            System.out.println("Kod odpowiedzi serwera sendMap : " + response.getStatusLine().getStatusCode());

            if(response.getStatusLine().getStatusCode() != 200)
            {
                throw new Exception("Bad reposnse in sendMap");
            }

            client.close();
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static Array2Int result() throws Exception {

        //budujemy klineta jednorzaowego
        final HttpClient client = HttpClientBuilder.create().build();

        //podajemy link
        final HttpGet request = new HttpGet("http://127.0.0.1:8080/api/ttt/result");

        //obiekt do konwersacji json
        final Gson gson = new Gson();

        try
        {
            // Otrzymujemy odpowiedz od serwera.
            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();

            // Na tym etapie odczytujemy JSON'a, ale jako String.
            final String json = EntityUtils.toString(entity);

            final Type type = new TypeToken<Array2Int>() {}.getType();

            final Array2Int files = gson.fromJson(json, type);

            System.out.println("Result - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200)
            {
                System.out.println("Aktualizacja planszy");
            }

            return files;

        }
        catch (IOException e)
        {
            throw new Exception("Problem z zwróceniem JSONA");
        }
    }

    public static void reset() throws Exception{

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/api/ttt/reset");

        try {

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("Reset - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Gra zresetowana");
            }
            client.close();
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void bot(int symbol) throws Exception{

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/api/ttt/bot");

        Gson gson = new Gson();

        // Tworzymy obiekt uzytkownika
        final Single1Int userData = new Single1Int(symbol);

        // Serializacja obiektu do JSONa
        final String json = gson.toJson(userData);

        try {

            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("Bot - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200)
            {
                System.out.println("Ruch bota");
            }

            client.close();
        }
        catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    public static void user(final Single3Int single3IntData) throws Exception{

        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/api/ttt/user");

        Gson gson = new Gson();

        // Serializacja obiektu do JSONa
        final String json = gson.toJson(single3IntData);

        try {

            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("User - kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200)
            {
                System.out.println("Ruch usera");
            }

            client.close();
        } catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }

    public static void gameResult(String gameName, String login, int value) throws Exception {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/api/status");

        Gson gson = new Gson();

        // Tworzymy obiekt uzytkownika
        final StringsAndInt userData = new StringsAndInt(gameName, login, value);

        // Serializacja obiektu do JSONa
        final String json = gson.toJson(userData);

        try {
            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");

            final CloseableHttpResponse response = client.execute(httpPut);

            System.out.println("Game result - kot odpowiedzi serwera: " + response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Dodano resul do servera");
            }

            client.close();
        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }
}
