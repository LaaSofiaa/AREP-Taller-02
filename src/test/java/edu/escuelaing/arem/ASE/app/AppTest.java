package edu.escuelaing.arem.ASE.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.util.Map;
import java.util.function.BiFunction;

public class AppTest {

    @BeforeEach
    public void setUp() {
        HttpServer.getDataStore().clear();
        HttpServer.getRoutes().clear();

        HttpServer.get("/App/hello", (req, res) -> {
            String name = req.getValues("name");
            if (name == null || name.isEmpty()) {
                name = "usuario";
            }
            return "{\"name\": \"" + name + "\"}";
        });

        HttpServer.get("/App/updateName", (req, res) -> {
            String name = req.getValues("name");
            if (name != null && !name.isEmpty()) {
                HttpServer.getDataStore().put("name", name);
                return "Nombre actualizado a: " + name;
            }
            return "Nombre no proporcionado";
        });

        HttpServer.get("/App/pi", (req, res) -> String.valueOf(Math.PI));
    }

    @Test
    public void testHelloEndpoint() {

        HttpRequest httpRequest = new HttpRequest("/App/hello?name=John");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));

        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getRoutes().get("/App/hello");

        // Verificar que el manejador existe
        assertNotNull(handler, "El manejador para /App/hello no está registrado.");

        // Ejecutar el manejador de la ruta
        String result = handler.apply(httpRequest, httpResponse);

        // Verificar el resultado
        assertEquals("{\"name\": \"John\"}", result);
    }

    @Test
    public void testUpdateNameEndpoint() {

        HttpRequest httpRequest = new HttpRequest("/App/updateName");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));

        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getRoutes().get("/App/updateName");

        // Verificar que el manejador existe
        assertNotNull(handler, "El manejador para /App/updateName no está registrado.");

        // Ejecutar el manejador de la ruta
        String result = handler.apply(httpRequest, httpResponse);

        // Verificar el resultado cuando no se proporciona un nombre
        assertEquals("Nombre no proporcionado", result);


        httpRequest = new HttpRequest("/App/updateName?name=JungKook");
        result = handler.apply(httpRequest, httpResponse);


        assertEquals("Nombre actualizado a: JungKook", result);
        Map<String, String> dataStore = HttpServer.getDataStore();
        assertEquals("JungKook", dataStore.get("name"));
    }

    @Test
    public void testPiEndpoint() {
        HttpRequest httpRequest = new HttpRequest("/App/pi");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));

        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getRoutes().get("/App/pi");

        // Verificar que el manejador existe
        assertNotNull(handler, "El manejador para /App/pi no está registrado.");

        // Ejecutar el manejador de la ruta
        String result = handler.apply(httpRequest, httpResponse);

        // Verificar el resultado
        assertEquals(String.valueOf(Math.PI), result);
    }
}