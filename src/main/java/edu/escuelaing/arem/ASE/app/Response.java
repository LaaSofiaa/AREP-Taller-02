package edu.escuelaing.arem.ASE.app;


import java.io.PrintWriter;

/**
 * Clase que representa una respuesta HTTP.
 */
class Response {
    private final PrintWriter out;

    public Response(PrintWriter out) {
        this.out = out;
    }

    /**
     * Envía una respuesta al cliente.
     * @param response La respuesta a enviar.
     */
    public void send(String response) {
        out.println(response);
    }
}

