# Iniciar Sistemas

Debes clonar este repositorio a tu computadora.

## Sistema de fábrica

Entra en la carpeta `sistemaFabrica`

### Requisitos

- Conexión a MongoDB, Attlas o local
- Node version 10.19.0
- Necesitas nodemon, para instalarlo puedes usar `npm install -g nodemon`

### Archivo configuracion.js

En este archivo puedes declarar las variables que van a servir para poder correr y personalizar el sistema de fábrica.

| Variable | Tipo | Uso |
| :------- | :------: | :------: |
| fabricaActual  | `string` | Es el nombre de la fábrica, es también el nombre con que se  registra la marca de teléfonos y es con el nombre que pide reportes a sistemas de ventas |
| correo | `string` | Correo del que se va a enviar el archvio de excel |
| passwordCorreo | `string` | Contraseña para el correo |
| ipServer | `string` | Es la ip en la que se encuentra el back-end |
| puertoServer | `Number` | Puerto en el que se encuentra el back-end |

### Carpeta publicElements

Esta carepta se encuentra en `sistemaFabrica/cliente/src/components/publicElements/Navigation.js` en esta carpeta está el archivo llamado `Url.js` aquí debes cambiar la variable `ipServer` y `port` para que tengan los mismos valores que el archivo configuración.
En esta misma carpeta se encuentra el archivo `Navigation.js` en la línea 16 puedes escribir la etiqueta de la fábrica.

### Carpeta Servidor

Esta carpeta contiene las funciones del back-end, en esta carpeta solo necesitas la conexión a la base de datos, la cual puedes cambiar en la lĺinea 41, esto debes cambiarlo por tu conexión a tu base de datos.

```javascript
/// DATABASE CONNECTION
mongoose.connect(
  "Aquí va tu conexión a mongo",
  { useNewUrlParser: true, useFindAndModify: false, useUnifiedTopology: true }
);
```

Luego debes correr el siguiente comando dentro de esta carpeta, esto instalará todas las dependencias

#### `npm install`

Para correr el servidor, si tienes ya instalado nodemon, puedes usar:

#### `nodemon server.js`

### Carpeta Cliente

Solo debes correr dos comandos, el primero instala las dependencias

#### `npm install`

El segundo inicia la aplicación, se abrirá en el puerto 3000 por defecto, se abre en el navegador por defecto.

#### `npm start`

## Sistema de Ventas

Entra en la carpeta `sistemaVentas`

### Requisitos Ventas

- Necesitas jdk 8 obligatorio
- Puedes usar jdk 15 junto a jdk 8
- Necesitas Eclipse o Visual Code para compilar el proyecto, en nuestro caso, utilizamos Visual Code, puedes seguir la guía de [instalación de Java para visual Code](https://code.visualstudio.com/docs/languages/java).
- Necesitas Java, Spring Boot y Tomcat.
- Base de datos Oracle XE 18c

### Archivo configuracion.Java

Este archivo se encuentra en la ruta `ventasClientes/src/main/java/com/ventas/ventas/configuracion.Java`. En este archivo puedes configurar las siguentes variables:

| Variable | Tipo | Uso |
| :------- | :------: | :------: |
| db | `String` | Nombre del usuario dentro de la base de datos, puedes escribir `SYSTEM` si no tienes usuarios creados. |
| tienda | `String` | Es el nombre de tu tienda, es también como el sistema de fábrica debe guardarte como usuario |
| passwordTienda | `String` | Es la contraseña que debe guardar el sistema de ventas para poder usar los servicios REST |

### Archivo application.properties

Este archivo se encuentra en la ruta `sistemaVentas/src/main/resources`. Este archivo es la configuración general de algunas variables del proyecto, las que se aconsejan modificar son:

| Variable | Ejemplo | Uso |
| :------- | :------: | :------: |
| server.port | `8080` | Indica el puerto en el que corre la aplicación |
| spring.datasource.url | `jdbc:oracle:thin:@localhost:1521:xe` | Es la conexión a tu base de datos, puedes cambiar localhost con una ip, al igual que puedes cambiar 1521 por el puerto en el que está la base de datos |
| spring.datasource.username | `system` | Usuario SYSTEM de la base de datos oracle, es preferible no cambiarlo |
| spring.datasource.password | `contrasena` | Contraseña del usuario SYSTEM |
| spring.mail.host | `smtp.gmail.com` | Servicio del correo electornico para enviar los reportes de compras. |
| spring.mail.username | `micorreo@gmail.com` | Correo del que se enviará los reportes de compras.  Recuerda que hay que darle permiso en la página respectiva del correo electrónico.|
| spring.mail.password | `passwordcorreo` | Contraseña del correo electronico |

### Archivo template.html

Este archivo se encuentra en la ruta `sistemaVentas/src/main/resources/templates/fragments` En este puedes cambiar el nombre del sistema de ventas que aparece en el header.
Puedes cambiarlo en la línea 19. 

```html
<a class="navbar-brand" href="/"><i class="bi bi-house-fill"></i> NOMBRE</a> 
```

### Iniciar proyecto

Si estás en Eclipse puedes correrlo de manera normal, si estás en Visual Code puedes ver en el panel lateral un apartado llamado `SPRING BOOT DASHBOARD`, puedes esperar a que cargue el proyecto con el nombre `ventas` y darle al botón play.
Si no apareciera este panel y estás en Visual Code, puedes irte al archivo que está en la ruta `sistemaVentas/src/main/java/com/ventas/ventas`, si tienes instalado correctamente las extenciones de Spring Boot, podrás ver un botón que dice `Run`, esto iniciará la aplicación.