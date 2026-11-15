# Spring Contact Backend

This service receives contact-form submissions from `/contact.html` and sends them to `info@aricsso.com`.

## Endpoint

- `POST /api/contact` (form-urlencoded)

On success/failure it redirects back to:

- `/contact.html?status=ok`
- `/contact.html?status=error`

## Required environment variables

- `SMTP_HOST`
- `SMTP_PORT` (optional, default `587`)
- `SMTP_USERNAME`
- `SMTP_PASSWORD`
- `CONTACT_TO` (optional, default `info@aricsso.com`)
- `CONTACT_FROM` (optional, default `no-reply@aricsso.com`)
- `PORT` (optional, default `8080`)

## Run locally

```bash
mvn spring-boot:run
```

## Build

```bash
mvn clean package
```
