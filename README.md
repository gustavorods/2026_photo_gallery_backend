# Photo Gallery

**Tagline:** A simple API for storing and accessing photos in Amazon S3.

Read this in [Português (Brasil)](README.pt-BR.md) or [Español](README.es.md).

## Description

Photo Gallery is a Java and Spring Boot REST API for uploading, listing, viewing, and deleting photos. Files are stored in the `gustavo-photo-gallery-2026` S3 bucket in the `us-east-1` region.

## Installation

You need Java 21, access to the S3 bucket, and AWS credentials configured for the SDK (for example, through an AWS profile or environment variables). The credentials must allow uploading, listing, reading, and deleting objects in the bucket.

From the project directory, run:

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080` by default. On Windows, run `mvnw.cmd spring-boot:run`.

## Usage

Upload a photo using the multipart form field `file`:

```bash
curl -F "file=@photo.jpg" http://localhost:8080/photos
```

List photos:

```bash
curl http://localhost:8080/photos
```

Each list item contains `key`, `contentType`, `size`, and `url`. The `key` is the uploaded file's name.

## Routes

| Method | Route | Description | Successful response |
| --- | --- | --- | --- |
| `POST` | `/photos` | Uploads a file from the multipart field `file`. | `200 OK` with a confirmation message. |
| `GET` | `/photos` | Lists files in the bucket. | `200 OK` with a JSON array of photos. |
| `GET` | `/photos/{key}` | Returns the photo bytes and content type. | `200 OK` with the file. |
| `DELETE` | `/photos/{key}` | Deletes the photo identified by `key`. | `204 No Content`. |

To download or delete a photo, replace `{key}` with its file name:

```bash
curl http://localhost:8080/photos/photo.jpg --output photo.jpg
curl -X DELETE http://localhost:8080/photos/photo.jpg
```

## License

This project does not have a defined license yet.
