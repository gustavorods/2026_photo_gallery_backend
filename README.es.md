# Photo Gallery

**Resumen:** API sencilla para almacenar y consultar fotos en Amazon S3.

Lee esto en [English](README.md) o [Português (Brasil)](README.pt-BR.md).

## Descripción

Photo Gallery es una API REST en Java y Spring Boot para subir, listar, ver y eliminar fotos. Los archivos se almacenan en el bucket S3 configurado por `APP_AWS_S3_BUCKET_NAME`, en la región configurada por `AWS_REGION`.

## Instalación

Necesitas Java 21 y una cuenta AWS con acceso a S3.

### Configuración de S3

1. Crea tu propio bucket S3 con un nombre disponible, en la región AWS que prefieras. Por ejemplo, `cedar-vault-k7m2` en `eu-west-2` (valores ilustrativos; elige los tuyos). Define el nombre del bucket en `APP_AWS_S3_BUCKET_NAME` y su región en `AWS_REGION`.
2. Asigna al usuario IAM cuyas claves de acceso utilizará la aplicación la siguiente política (cambia `cedar-vault-k7m2` en la política para que coincida con `APP_AWS_S3_BUCKET_NAME`):

   ```json
   {
     "Version": "2012-10-17",
     "Statement": [
       {
         "Effect": "Allow",
         "Action": "s3:ListBucket",
         "Resource": "arn:aws:s3:::cedar-vault-k7m2"
       },
       {
         "Effect": "Allow",
         "Action": ["s3:PutObject", "s3:GetObject", "s3:DeleteObject"],
         "Resource": "arn:aws:s3:::cedar-vault-k7m2/*"
       }
     ]
   }
   ```

3. Configura las credenciales y las demás variables en el entorno donde se ejecuta el backend:

   Las cinco variables son obligatorias. Sustituye el bucket, la región, las credenciales y la URL del frontend del ejemplo siguiente por tus propios valores.

   ```bash
   export APP_AWS_ACCESS_KEY_ID="tu-clave-de-acceso"
   export APP_AWS_SECRET_ACCESS_KEY="tu-clave-secreta"
   export AWS_REGION="eu-west-2"
   export APP_AWS_S3_BUCKET_NAME="cedar-vault-k7m2"
   export APP_CORS_ALLOWED_ORIGINS="https://app.example.com"
   ```

   `APP_CORS_ALLOWED_ORIGINS` acepta varios orígenes separados por comas.

   Para el despliegue, configura las cinco variables de entorno en la plataforma utilizada. No guardes la clave secreta en el repositorio. La aplicación lee directamente las variables con prefijo `APP_`; `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY` y los perfiles AWS no configuran este cliente S3. `AWS_REGION` es obligatoria y debe coincidir con la región del bucket.

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
