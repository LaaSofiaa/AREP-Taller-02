
# Web Framework Development for REST Services and Static File Management


Este proyecto tiene como objetivo mejorar un servidor web existente, que actualmente admite archivos HTML, JavaScript, CSS e imágenes, convirtiéndolo en un marco web completamente funcional. Este marco permitirá el desarrollo de aplicaciones web con servicios REST en el backend. Las principales características son:

- Método GET: Definir rutas con funciones lambda para servicios REST.
- Extracción de Parámetros de Consulta: Obtener parámetros de las solicitudes para personalizar respuestas.
- Archivos Estáticos: Especificar la carpeta donde se encuentran los archivos estático

  
## Instalación

**1.**  Clonar el repositorio

```bash
  git clone https://github.com/tu_usuario/AREP-Taller-02.git

  cd AREP-Taller-02
```
**2.**  Construir el proyecto mediante maven, donde debes tener previamente instalado este https://maven.apache.org .
```bash
  mvn clean install
```  
**3.**  Ejecuta el proyecto con el siguiente comando:
```bash
  java -cp target/Taller2-1.0-SNAPSHOT.jar edu.escuelaing.arem.ASE.app.App
``` 
**4.**  Una vez este corriendo la aplicación prueba los siguiente:
* **Página Principal:**
```bash
  http://localhost:35000/index.html
```
* **REST Services** 
```bash
  http://localhost:35000/App/hello?name=YourName
  http://localhost:35000/App/pi
  http://localhost:35000/App/euler
  http://localhost:35000/App/mundo
```

* **StaicFiles**
```bash
  http://localhost:35000/img.jpg
  http://localhost:35000/styles.css
  http://localhost:35000/script.js
  http://localhost:35000/app.html
  http://localhost:35000/index.html
```

## Arquitectura del Proyecto 
**Estructura del directorio**

El directorio del proyecto esta organizado de la siguiente manera:

```plaintext
📦 src  
 ┣ 📂 main  
 ┃ ┣ 📂 java  
 ┃ ┃ ┗ 📂 edu.escuelaing.arem.ASE.app  
 ┃ ┃ ┃ ┣ 📜 App.java  
 ┃ ┃ ┃ ┣ 📜 HttpRequest.java  
 ┃ ┃ ┃ ┣ 📜 HttpResponse.java  
 ┃ ┃ ┃ ┗ 📜 HttpServer.java  
 ┃ ┣ 📂 resources  
 ┃ ┃ ┣ 📜 app.html  
 ┃ ┃ ┣ 📜 img.jpg  
 ┃ ┃ ┣ 📜 index.html  
 ┃ ┃ ┣ 📜 script.js  
 ┃ ┃ ┗ 📜 styles.css  
 ┗ 📂 test  
 ┃ ┗ 📂 java  
 ┃ ┃ ┗ 📂 edu.escuelaing.arem.ASE.app  
 ┃ ┃ ┃ ┗ 📜 AppTest.java  
```

**Diagrama de la Arquitectura**

![image](https://github.com/user-attachments/assets/655820bf-2900-4099-9fcd-db6131250603)
![image](https://github.com/user-attachments/assets/4b0251a3-24fa-4a54-9109-456630f684d7)

**Componentes del Proyecto**

 `App.java`
- Contiene el método principal `main` que inicia el servidor.
- Define rutas para manejar solicitudes como `/App/hello`, `/App/pi`, `/App/euler`, y `/App/mundo`.
- Permite servir archivos estáticos desde `src/main/java/resources`.

 `HttpServer.java`
- Implementa un servidor HTTP básico en el puerto `35000`.
- Maneja solicitudes GET para servir archivos y ejecutar rutas dinámicas.
- Maneja solicitudes POST para actualizar valores en memoria.
- Registra rutas con funciones lambda.

 `HttpRequest.java`
- Representa una solicitud HTTP.
- Analiza la URL y extrae los parámetros de consulta.

 `HttpResponse.java`
- Encapsula la respuesta HTTP y permite enviar datos al cliente.

## Pruebas de Usuario

- **GET REQUESTS:**
  
  - **Saludo Personalizado:** Accede a la URL /App/hello con y sin parámetros de consulta para verificar la funcionalidad del saludo.  Si no se le asigan parámetros deja por defecto el nombre usuario. Ejemplo: http://localhost:35000/App/hello?name=sofi.
  ![image](https://github.com/user-attachments/assets/65f5c856-447e-4e14-998e-f02f0c51b572)

  - **Saludo Hola Mundo:** Accede a la URL /App/mundo para comprobar que el servidor devuelve correctamente el Hola Mundo.
  ![image](https://github.com/user-attachments/assets/bb089175-4d22-4806-84ac-acbb7f8f1b4b)

  - **Valor de Pi:** Accede a la URL /App/pi para comprobar que el servidor devuelve correctamente el valor de pi.
  ![image](https://github.com/user-attachments/assets/c3e66c8e-8f15-4c9e-a03b-45484c5538d5)

   - **Valor de Euler:** Accede a la URL /App/euler para comprobar que el servidor devuelve correctamente el valor de euler.
   ![image](https://github.com/user-attachments/assets/bacd8128-5c44-4575-9293-cd22a1708ab9)
  
- **STATIC FILES:**
  - **Index.html:**  Comprueba que el archivo index.html se cargue correctamente desde el directorio de recursos.
     ![image](https://github.com/user-attachments/assets/ee84c1ae-1975-4c06-9599-4309ebcb2b47)
     ![image](https://github.com/user-attachments/assets/fc612b44-62cb-4d7d-879d-c49d3dbf9a9f)
     ![image](https://github.com/user-attachments/assets/9efc6121-bc5d-4f98-b563-64cc8f670ee2)


  - **img.jpg**  Comprueba que el archivo img.jpg se cargue correctamente desde el directorio de recursos.
    ![image](https://github.com/user-attachments/assets/48af89df-9c5f-4336-80f5-349ff759d62a)

  - **script.js**  Comprueba que el archivo script.js se cargue correctamente desde el directorio de recursos.  
    ![image](https://github.com/user-attachments/assets/0c248da3-b9fc-4b9d-b6d2-5122b0b497bf)

  - **styles.css**  Comprueba que el archivo styles.css se cargue correctamente desde el directorio de recursos.
    ![image](https://github.com/user-attachments/assets/bd6ede8e-79c2-4fa6-9b4a-b4164518810b)


## Pruebas Automatizadas

Las pruebas unitarias están implementadas en `AppTest.java` y cubren los siguientes casos:

- *Prueba de endpoint `/App/hello`:* Verifica que la respuesta incluye el nombre pasado como parámetro.

- *Prueba de constantes matemáticas `(/App/euler y /App/pi)`:* Comprueba que los valores retornados coinciden con los valores esperados de Math.E y Math.PI.

- *Pruebas de `HttpRequest`:* Evalúan la correcta extracción de la ruta y parámetros de consulta.

- *Prueba de `HttpResponse`:* Verifica que la respuesta se envía correctamente.

- *Pruebas de archivos estáticos:* Se asegura de que los archivos HTML, CSS, JS e imágenes se sirvan con el tipo de contenido adecuado.

Para correr las pruebas usamos el siguiente comando

```bash
  mvn test

```
![image](https://github.com/user-attachments/assets/ad2433af-0b0f-445e-b310-b56a5f9b3faa)
![image](https://github.com/user-attachments/assets/29e18e9b-c0d6-48e5-becf-292c2b664ae1)


## Autor

**Laura Gil** - Desarrolladora y autora del proyecto. 

