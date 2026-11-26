# Photo Gallery

**Tagline:** A simple API for storing and accessing photos in Amazon S3.

Read this in [Português (Brasil)](README.pt-BR.md) or [Español](README.es.md).

## Description

Photo Gallery is a Java and Spring Boot REST API for uploading, listing, viewing, and deleting photos. Files are stored in the S3 bucket configured by `APP_AWS_S3_BUCKET_NAME` in the region configured by `AWS_REGION`.

## Installation

You need Java 21 and an AWS account with access to S3.

### S3 setup

1. Create your own S3 bucket with an available name in the AWS region of your choice. For example, `cedar-vault-k7m2` in `eu-west-2` (illustrative values; choose your own). Set the bucket name in `APP_AWS_S3_BUCKET_NAME` and its region in `AWS_REGION`.
2. Give the IAM user whose access keys the application will use this policy (replace `cedar-vault-k7m2` in the policy to match `APP_AWS_S3_BUCKET_NAME`):

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

3. Set the credentials and configuration in the environment where the application runs:

   All five variables are required. Replace the example bucket, region, credentials, and frontend URL below with your own values.

   ```bash
   export APP_AWS_ACCESS_KEY_ID="your-access-key-id"
   export APP_AWS_SECRET_ACCESS_KEY="your-secret-access-key"
   export AWS_REGION="eu-west-2"
   export APP_AWS_S3_BUCKET_NAME="cedar-vault-k7m2"
   export APP_CORS_ALLOWED_ORIGINS="https://app.example.com"
   ```

   `APP_CORS_ALLOWED_ORIGINS` accepts multiple origins separated by commas.

   Set all five environment variables in your deployment platform when deploying. Keep the secret out of the repository. The application uses these `APP_` variables directly; `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, and AWS profiles do not configure this S3 client. `AWS_REGION` is required and must match the bucket region.

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
