# Photo Gallery

**Resumo:** API simples para armazenar e consultar fotos no Amazon S3.

Leia em [English](README.md) ou [Español](README.es.md).

## Descrição

O Photo Gallery é uma API REST em Java e Spring Boot para enviar, listar, visualizar e excluir fotos. Os arquivos são armazenados no bucket S3 configurado por `APP_AWS_S3_BUCKET_NAME`, na região configurada por `AWS_REGION`.

## Instalação

Você precisa do Java 21 e de uma conta AWS com acesso ao S3.

### Configuração do S3

1. Crie seu próprio bucket S3 com um nome disponível, na região AWS de sua escolha. Por exemplo, `cedar-vault-k7m2` em `eu-west-2` (valores ilustrativos; escolha os seus). Defina o nome do bucket em `APP_AWS_S3_BUCKET_NAME` e a região dele em `AWS_REGION`.
2. Conceda ao usuário IAM cujas chaves de acesso serão usadas pela aplicação a seguinte política (troque `cedar-vault-k7m2` na política para corresponder a `APP_AWS_S3_BUCKET_NAME`):

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

3. Configure as credenciais e demais variáveis no ambiente onde o backend será executado:

   As cinco variáveis são obrigatórias. Substitua o bucket, a região, as credenciais e a URL do frontend do exemplo abaixo pelos seus próprios valores.

   ```bash
   export APP_AWS_ACCESS_KEY_ID="sua-chave-de-acesso"
   export APP_AWS_SECRET_ACCESS_KEY="sua-chave-secreta"
   export AWS_REGION="eu-west-2"
   export APP_AWS_S3_BUCKET_NAME="cedar-vault-k7m2"
   export APP_CORS_ALLOWED_ORIGINS="https://app.example.com"
   ```

   `APP_CORS_ALLOWED_ORIGINS` aceita múltiplas origens separadas por vírgula.

   No deploy, configure as cinco variáveis de ambiente na plataforma utilizada. Não salve a chave secreta no repositório. A aplicação lê diretamente as variáveis com prefixo `APP_`; `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY` e perfis AWS não configuram este cliente S3. `AWS_REGION` é obrigatória e deve corresponder à região do bucket.

Na pasta do projeto, execute:

```bash
./mvnw spring-boot:run
```

A API fica disponível em `http://localhost:8080` por padrão. No Windows, use `mvnw.cmd spring-boot:run`.

## Uso

Envie uma foto usando o campo `file` de um formulário multipart:

```bash
curl -F "file=@foto.jpg" http://localhost:8080/photos
```

Liste as fotos:

```bash
curl http://localhost:8080/photos
```

Cada item da lista contém `key`, `contentType`, `size` e `url`. A `key` corresponde ao nome do arquivo enviado.

## Rotas

| Método | Rota | Descrição | Resposta de sucesso |
| --- | --- | --- | --- |
| `POST` | `/photos` | Envia um arquivo no campo multipart `file`. | `200 OK` com mensagem de confirmação. |
| `GET` | `/photos` | Lista os arquivos do bucket. | `200 OK` com uma lista JSON de fotos. |
| `GET` | `/photos/{key}` | Retorna os bytes da foto e seu tipo de conteúdo. | `200 OK` com o arquivo. |
| `DELETE` | `/photos/{key}` | Exclui a foto identificada pela `key`. | `204 No Content`. |

Para consultar ou excluir uma foto, substitua `{key}` pelo nome do arquivo:

```bash
curl http://localhost:8080/photos/foto.jpg --output foto.jpg
curl -X DELETE http://localhost:8080/photos/foto.jpg
```

## Licença

Este projeto ainda não possui uma licença definida.
