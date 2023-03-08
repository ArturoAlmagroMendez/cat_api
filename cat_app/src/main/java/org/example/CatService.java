package org.example;
import com.google.gson.*;
import com.squareup.okhttp.*;
import org.example.model.dto.Favorite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CatService {
    public static void showCats() throws IOException {
        //1. Vamos a traer los datos de la api
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").method("GET", null).build();
        Response response = client.newCall(request).execute();

        String document_Json = response.body().string();


        //Cortar los corchetes

        /*try {
            document_Json = document_Json.substring(1, document_Json.length());
           document_Json = document_Json.substring(0, document_Json.length() - 1);

        }catch(JsonSyntaxException e){
            System.out.println(e);
        }
        */

        //Crear un objeto de la clase Gson
        Gson gson = new Gson();

        //de esta forma nos ahorramos cortar los corchetes ya que la libreria Gson esperará un array de esa clase y no solo un objeto.
        Cat cat = gson.fromJson(document_Json, Cat[].class)[0];

        //Redimensionar la imagen
        Image image = null;
        try{
            URL url = new URL(cat.getUrl());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "");
            BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
            ImageIcon catBackground = new ImageIcon(bufferedImage);
            if(catBackground.getIconWidth() > 800){
                //Redimensionamos
                Image background = catBackground.getImage();
                Image modificated = background.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                catBackground = new ImageIcon(modificated);
            }
            String menu = "Opciones: \n"
                    + "1.Ver otra imagen \n"
                    + "2.Favorito \n"
                    + "3.Ver Favoritos \n"
                    + "4.Volver \n";

            String[] buttons = { "Ver otra imagen", "Favorito" ,"Ver Favoritos", "volver" };
            String id_cat = cat.getId();
            String option = (String) JOptionPane.showInputDialog(null, menu, id_cat , JOptionPane.INFORMATION_MESSAGE, catBackground,buttons ,buttons[0]);

            //Validamos que opcion coje el usuario

            int option_select = -1;
            for( int i=0 ; i < buttons.length ;i++){
                if (option.equals(buttons[i])){
                    option_select = i;
                }
            }
            switch(option_select){
                case 0:
                    showCats();
                    break;
                case 1:
                    makeFavourite(cat);
                    break;
                case 2:
                    showFavourites();;
                default:
                    break;
            }


        }catch(IOException e){
            System.out.println(e);

        }
        

    }

    private static void makeFavourite(Cat cat) {
        try{
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\" : \""+ cat.getId() +"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites?=&=")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", cat.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("Se ha guardado como favorito " + cat.toString());
        }catch(IOException e) {
            System.out.println(e);
        }
    }
    private static void showFavourites() {
        try {
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("GET", null)
                    .addHeader("x-api-key", "live_nnip2f7abrQhqPChOAM7KUXrZ2Ldbf3mRLQb5gJHRiyj0Dd2510Mo4Iph4uq6tRS")
                    .build();
            Response response = client.newCall(request).execute();
            //Extraemos el Json del response para ver que gatos son los favoritos.
            System.out.println(response.body().string());
            //String stringJson = response.body().string();
            //JsonObject json = new JsonObject(stringJson);

            //JsonParser is deprecated
            //JsonParser parser = new JsonParser();
            //JsonArray jsonArray = (JsonArray) parser.parse(stringJson);

        }catch(IOException e){
            System.out.println(e);
        }
        //return stringJson;
    }
    public static void getFavouritesFromJson(JsonArray jsonArray){
        Map<String, String> favouriteCats = new HashMap<>();
        for( int i = 0; i < jsonArray.size() ; i++){
            try {
                JsonObject json = (JsonObject) jsonArray.get(i);
                for ( int y = 0 ; y < json.size() ; y++) {
                    JsonArray images = json.getAsJsonArray("image");
                    JsonObject imageArray = images.getAsJsonObject();
                    String image_url = imageArray.getAsString();
                    System.out.println("Image id : " + json.get("image_id"));
                }

            }catch(JsonIOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void showFavouriteCats(String apikey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .method("GET", null)
                .addHeader("x-api-key", apikey)
                .build();
        Response response = client.newCall(request).execute();
        //Guardamos el string con la respuesta
        String myJson = response.body().string();
        //Creamos el objeto gson
        Gson gson = new Gson();
        Favorite[] favArray = gson.fromJson(myJson, Favorite[].class);
        int index = 0;
        if (favArray.length > 0) {
            //Mostrar 1 favorito aleatorio
            int min = 1;
            int max = favArray.length;
            int random = (int) (Math.random() * ((max - min) + 1)) + min;
            index = random - 1;
            Favorite favorite = favArray[index];
            Image image = null;
            try{
                URL url = new URL(favorite.image.getUrl());
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.addRequestProperty("User-Agent", "");
                BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
                ImageIcon catBackground = new ImageIcon(bufferedImage);
                if(catBackground.getIconWidth() > 800){
                    //Redimensionamos
                    Image background = catBackground.getImage();
                    Image modificated = background.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                    catBackground = new ImageIcon(modificated);
                }
                String menu = "Opciones: \n"
                        + "1.Ver otra imagen \n"
                        + "2.Eliminar Favorito \n"
                        + "3.Volver \n";

                String[] buttons = { "Ver otra imagen", "Eliminar Favorito" , "volver" };
                String id_cat = favorite.getId();
                String option = (String) JOptionPane.showInputDialog(null, menu, id_cat , JOptionPane.INFORMATION_MESSAGE, catBackground,buttons ,buttons[0]);

                //Validamos que opcion coje el usuario

                int option_select = -1;
                for( int i=0 ; i < buttons.length ;i++){
                    if (option.equals(buttons[i])){
                        option_select = i;
                    }
                }
                switch(option_select){
                    case 0:
                        showFavouriteCats(apikey);
                        break;
                    case 1:
                        deleteFavourite(favorite);
                        break;
                    case 2:
                        showFavourites();;
                    default:
                        break;
                }


            }catch(IOException e){
                System.out.println(e);

            }
        }



    }
    public static void deleteFavourite(Favorite favourite){
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+favourite.getId()+"")
                    .delete(null)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "live_nnip2f7abrQhqPChOAM7KUXrZ2Ldbf3mRLQb5gJHRiyj0Dd2510Mo4Iph4uq6tRS")
                    .build();
            Response response = client.newCall(request).execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
