package edu.escuelaing.arem.ASE.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.function.BiFunction;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;


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
    @Test
    public void testHtml() throws IOException {
        String path = "/index.html";
        testStaticFile(path, "text/html");
    }

    @Test
    public void testCss() throws IOException {
        String path = "/styles.css";
        testStaticFile(path, "text/css");
    }

    @Test
    public void testJs() throws IOException {
        String path = "/script.js";
        testStaticFile(path, "application/javascript");
    }

    @Test
    public void testImage() throws IOException {
        String path = "/img.jpg";
        testStaticFile(path, "image/jpeg");
    }

    private void testStaticFile(String path, String expectedContentType) throws IOException {
        // Simula el flujo de salida para capturar la respuesta HTTP
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        BufferedOutputStream dataOut = new BufferedOutputStream(outputStream);

        HttpServer.handleGetRequest(path, dataOut, out);
        String response = outputStream.toString();

        assertTrue(response.contains("HTTP/1.1 200 OK"), "El código de estado no es 200 OK");
        assertTrue(response.contains("Content-Type: " + expectedContentType), "El tipo de contenido no es " + expectedContentType);

        // Verifica que el archivo se envió correctamente
        File file = new File(App.getStaticFilesDirectory(), path);
        assertTrue(file.exists(), "El archivo " + path + " no existe");
    }


}