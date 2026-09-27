# Photo Gallery

**Resumen:** API sencilla para almacenar y consultar fotos en Amazon S3.

Lee esto en [English](README.md) o [Português (Brasil)](README.pt-BR.md).

## Descripción

Photo Gallery es una API REST en Java y Spring Boot para subir, listar, ver y eliminar fotos. Los archivos se almacenan en el bucket S3 `gustavo-photo-gallery-2026`, en la región `us-east-1`.

## Instalación

Necesitas Java 21, acceso al bucket S3 y credenciales de AWS configuradas para el SDK (por ejemplo, mediante un perfil de AWS o variables de entorno). Las credenciales deben permitir subir, listar, leer y eliminar objetos del bucket.

Desde el directorio del proyecto, ejecuta:

```bash
./mvnw spring-boot:run
```

La API está disponible en `http://localhost:8080` de forma predeterminada. En Windows, ejecuta `mvnw.cmd spring-boot:run`.

## Uso

Sube una foto con el campo `file` de un formulario multipart:

```bash
curl -F "file=@foto.jpg" http://localhost:8080/photos
```

Lista las fotos:

```bash
curl http://localhost:8080/photos
```

Cada elemento de la lista contiene `key`, `contentType`, `size` y `url`. La `key` corresponde al nombre del archivo subido.

## Rutas

| Método | Ruta | Descripción | Respuesta exitosa |
| --- | --- | --- | --- |
| `POST` | `/photos` | Sube un archivo desde el campo multipart `file`. | `200 OK` con un mensaje de confirmación. |
| `GET` | `/photos` | Lista los archivos del bucket. | `200 OK` con una lista JSON de fotos. |
| `GET` | `/photos/{key}` | Devuelve los bytes de la foto y su tipo de contenido. | `200 OK` con el archivo. |
| `DELETE` | `/photos/{key}` | Elimina la foto identificada por `key`. | `204 No Content`. |

Para descargar o eliminar una foto, sustituye `{key}` por el nombre del archivo:

```bash
curl http://localhost:8080/photos/foto.jpg --output foto.jpg
curl -X DELETE http://localhost:8080/photos/foto.jpg
```

## Licencia

Este proyecto aún no tiene una licencia definida.
