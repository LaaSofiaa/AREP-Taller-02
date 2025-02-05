package edu.escuelaing.arem.ASE.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
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
    public void testHello() {

        HttpRequest httpRequest = new HttpRequest("/App/hello?name=Sofia");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));

        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getRoutes().get("/App/hello");

        // Verificar que el manejador existe
        assertNotNull(handler, "El manejador para /App/hello no está registrado.");
        // Ejecutar el manejador de la ruta
        String result = handler.apply(httpRequest, httpResponse);
        assertEquals("{\"name\": \"Sofia\"}", result);
    }

    @Test
    public void testUpdateName() {

        HttpRequest httpRequest = new HttpRequest("/App/updateName");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));

        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getRoutes().get("/App/updateName");

        // Verificar que el manejador existe
        assertNotNull(handler, "El manejador para /App/updateName no está registrado.");
        // Ejecutar el manejador de la ruta
        String result = handler.apply(httpRequest, httpResponse);

        assertEquals("Nombre no proporcionado", result);


        httpRequest = new HttpRequest("/App/updateName?name=JungKook");
        result = handler.apply(httpRequest, httpResponse);


        assertEquals("Nombre actualizado a: JungKook", result);
        Map<String, String> dataStore = HttpServer.getDataStore();
        assertEquals("JungKook", dataStore.get("name"));
    }

    @Test
    public void testPi() {
        HttpRequest httpRequest = new HttpRequest("/App/pi");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));

        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getRoutes().get("/App/pi");

        // Verificar que el manejador existe
        assertNotNull(handler, "El manejador para /App/pi no está registrado.");
        // Ejecutar el manejador de la ruta
        String result = handler.apply(httpRequest, httpResponse);
        assertEquals(String.valueOf(Math.PI), result);
    }

    @Test
    void testHttpRequest01() {
        String fullPath = "/path/to/resource?name=Sofía";
        HttpRequest request = new HttpRequest(fullPath);
        assertEquals("/path/to/resource", request.getPath());
        assertEquals("Sofía", request.getValues("name"));
    }

    @Test
    void testHttpRequest02() {
        String fullPath = "/path/to/resource?name=Laura";
        HttpRequest request = new HttpRequest(fullPath);
        assertEquals("/path/to/resource", request.getPath());
        assertEquals("Laura", request.getValues("name"));
    }

    @Test
    void testHttpRequestWithMultipleParameters() {
        String fullPath = "/path/to/resource?name=Sofía&age=22&city=Bogota";
        HttpRequest request = new HttpRequest(fullPath);
        assertEquals("/path/to/resource", request.getPath());
        assertEquals("Sofía", request.getValues("name"));
        assertEquals("22", request.getValues("age"));
        assertEquals("Bogota", request.getValues("city"));
    }

    @Test
    void testHttpRequestWithoutParameters() {
        String fullPath = "/path/to/resource";
        HttpRequest request = new HttpRequest(fullPath);
        assertEquals("/path/to/resource", request.getPath());
        assertNull(request.getValues("name"));
    }

    @Test
    void testSendResponse01() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        HttpResponse response = new HttpResponse(out);

        response.send("Hola, Sofía");
        out.flush();
        String output = outputStream.toString();
        assertEquals("Hola, Sofía\r\n", output);
    }

    @Test
    void testSendResponse02() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        HttpResponse response = new HttpResponse(out);
        response.send("Hola, Laura");
        out.flush();
        String output = outputStream.toString();
        assertEquals("Hola, Laura\r\n", output);
    }

    @Test
    void testSendMultipleResponses() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        HttpResponse response = new HttpResponse(out);
        response.send("Hola, Sofía");
        response.send("Hola, Laura");
        out.flush();
        String output = outputStream.toString();
        assertEquals("Hola, Sofía\r\nHola, Laura\r\n", output);
    }
}