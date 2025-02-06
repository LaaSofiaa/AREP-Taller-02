
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

* **REST Services:** 
```bash
  http://localhost:35000/App/hello?name=YourName
```

* **StaicFiles:**
```bash
  http://localhost:35000/index.html
```

## Arquitectura del Proyecto 
**Estructura del directorio**

El directorio del proyecto esta organizado de la siguiente manera:



**Diagrama de la Arquitectura**




**Componente Principal**
- 




## Pruebas de Usuario



## Pruebas Automatizadas


Para correr las pruebas usamos el siguiente comando

```bash
  mvn test

```




## Autor

**Laura Gil** - Desarrolladora y autora del proyecto. 

