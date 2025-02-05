package edu.escuelaing.arem.ASE.app;


import java.util.HashMap;
import java.util.Map;

public class App {
    private static String staticFilesDirectory = "src/main/java/resources";
    private static final Map<String, String> dataStore = new HashMap<>();

    /**
     * Método principal que inicia el servidor y configura las rutas.
     */
    public static void main(String[] args) {

        staticfiles("src/main/java/resources");

        // Definición de rutas y sus manejadores
        HttpServer.get("/App/hello", (request, respond) -> {
            String name = request.getValues("name");
            if (name == null || name.isEmpty()) {
                name = "usuario";
            }
            return "{\"name\": \"" + name + "\"}";
            // return "Hola " + name + " ,este es tu archivo JSON {\"name\": \"" + name + "\"}";
        });

        HttpServer.get("/App/updateName", (request, respond) -> {
            String name = request.getValues("name");
            if (name != null && !name.isEmpty()) {
                dataStore.put("name", name);
                return "Nombre actualizado a: " + name;
            }
            return "Nombre no proporcionado";
        });

        HttpServer.get("/App/pi", (req, resp) -> String.valueOf(Math.PI));

        HttpServer.startServer();
    }


    /**
     * Define el directorio donde se encuentran los archivos estáticos.
     * @param directory El directorio de archivos estáticos.
     */
    public static void staticfiles(String directory) {
        staticFilesDirectory = directory;
    }
}
