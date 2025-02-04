package edu.escuelaing.arem.ASE.app;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa una solicitud HTTP.
 */
class Request {
    private String path;
    private final Map<String, String> queryParams;

    public Request(String fullPath) {
        this.queryParams = new HashMap<>();
        if (fullPath.contains("?")) {
            // Extrae la parte de la URL antes de los parámetros de consulta
            String[] parts = fullPath.split("\\?", 2);
            this.path = parts[0]; // Guarda solo la ruta base

            if (parts.length > 1) {
                String[] params = parts[1].split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=", 2); // Usar "2" para asegurar que solo se divide en 2 partes
                    if (keyValue.length > 1) {
                        String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                        String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                        queryParams.put(key, value);
                    } else {
                        // Si no hay un valor después del "=", se guarda un valor vacío
                        String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                        queryParams.put(key, "");
                    }
                }
            }
        } else {
            this.path = fullPath;
        }
    }

    /**
     * Obtiene el valor de un parámetro de consulta.
     * @param name El nombre del parámetro.
     * @return El valor del parámetro, o null si no existe.
     */
    public String getValues(String name) {
        return queryParams.getOrDefault(name, null);
    }

    /**
     * Obtiene la ruta base sin parámetros de consulta.
     * @return La ruta base.
     */
    public String getPath() {
        return path;
    }
}
