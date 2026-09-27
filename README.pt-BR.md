# Photo Gallery

**Resumo:** API simples para armazenar e consultar fotos no Amazon S3.

Leia em [English](README.md) ou [Español](README.es.md).

## Descrição

O Photo Gallery é uma API REST em Java e Spring Boot para enviar, listar, visualizar e excluir fotos. Os arquivos são armazenados no bucket S3 `gustavo-photo-gallery-2026`, na região `us-east-1`.

## Instalação

Você precisa do Java 21, de acesso ao bucket S3 e de credenciais AWS configuradas para o SDK (por exemplo, por meio de um perfil AWS ou de variáveis de ambiente). As credenciais devem permitir enviar, listar, consultar e excluir objetos do bucket.

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
