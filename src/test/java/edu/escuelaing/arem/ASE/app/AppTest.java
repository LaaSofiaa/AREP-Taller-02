package edu.escuelaing.arem.ASE.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.function.BiFunction;

public class AppTest {

    @BeforeEach
    public void setUp() {
        HttpServer.getDataStore().clear();
        HttpServer.getServices().clear();

        App.staticfiles("src/main/java/resources");

        HttpServer.get("/App/hello", (req, res) -> {
            String name = req.getValues("name");
            if (name == null || name.isEmpty()) {
                name = "usuario";
            }
            return "{\"name\": \"" + name + "\"}";
        });

        HttpServer.get("/App/euler", (req, resp) -> String.valueOf(Math.E));
        HttpServer.get("/App/pi", (req, res) -> String.valueOf(Math.PI));
    }

    @Test
    public void testHello() {

        HttpRequest httpRequest = new HttpRequest("/App/hello?name=Sofia");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));
        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getServices().get("/App/hello");
        assertNotNull(handler, "El manejador para /App/hello no está registrado.");
        String result = handler.apply(httpRequest, httpResponse);
        assertEquals("{\"name\": \"Sofia\"}", result);
    }

    @Test
    public void testPi() {
        HttpRequest httpRequest = new HttpRequest("/App/pi");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));
        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getServices().get("/App/pi");
        assertNotNull(handler, "El manejador para /App/pi no está registrado.");
        String result = handler.apply(httpRequest, httpResponse);
        assertEquals(String.valueOf(Math.PI), result);
    }
    @Test
    public void testEuler() {
        HttpRequest httpRequest = new HttpRequest("/App/euler");
        HttpResponse httpResponse = new HttpResponse(new PrintWriter(System.out));
        BiFunction<HttpRequest, HttpResponse, String> handler = HttpServer.getServices().get("/App/euler");
        assertNotNull(handler, "El manejador para /App/euler no está registrado.");
        String result = handler.apply(httpRequest, httpResponse);
        assertEquals(String.valueOf(Math.E), result);
    }

    @Test
    void testHttpRequest01() {
        String fullPath = "/path/to/resource?name=Sofía";
        HttpRequest request = new HttpRequest(fullPath);
        assertEquals("/path/to/resource", request.getPath());
        assertEquals("Sofía", request.getValues("name"));
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


}