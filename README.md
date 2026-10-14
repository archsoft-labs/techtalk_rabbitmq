# Tech Talk — RabbitMQ (AMQP)

Material e código de apoio do tech talk sobre **RabbitMQ e mensageria AMQP**, abordando desde os conceitos fundamentais até um laboratório prático com **Spring Boot + Kotlin**.

## 📚 Conteúdo do Tech Talk

A apresentação está dividida em 4 módulos (PDFs na raiz do repositório):

| # | Arquivo | Tema |
|---|---------|------|
| 001 | [techtalk_rabbitmq-001_conceitos.pdf](techtalk_rabbitmq-001_conceitos.pdf) | Conceitos de mensageria, AMQP, exchanges, queues, bindings e routing keys |
| 002 | [techtalk_rabbitmq-002_docker.pdf](techtalk_rabbitmq-002_docker.pdf) | Subindo o RabbitMQ com Docker |
| 003 | [techtalk_rabbitmq-003_spring-kotlin.pdf](techtalk_rabbitmq-003_spring-kotlin.pdf) | Integração com Spring Boot e Kotlin |
| 004 | [techtalk_rabbitmq-004_lab.pdf](techtalk_rabbitmq-004_lab.pdf) | Laboratório prático guiado |

## 🎯 O que o projeto demonstra

Uma aplicação Spring Boot (Kotlin) que implementa um fluxo completo de mensageria:

- **Producer**: endpoint REST `POST /message/publish` que publica mensagens (`Letter`) em uma exchange direct
- **Consumer**: listener que consome as mensagens da fila
- **Dead Letter Queue (DLQ)**: fila de dead-letter configurada para mensagens rejeitadas/expiradas
- **Retry**: política de retry com backoff exponencial no listener (3 tentativas, intervalo inicial de 3s)
- **Provisionamento automático**: exchange, queues e bindings são criados na inicialização da aplicação (`RabbitMQMigration`)
- **Arquitetura em camadas**: `domain` (interfaces e modelos), `application` (serviços), `infrastructure` (RabbitMQ) e `presentation` (controllers)

## ✅ Pré-requisitos

| Ferramenta | Versão mínima | Observação |
|------------|---------------|------------|
| **Docker** + **Docker Compose** | qualquer recente | Para subir o broker RabbitMQ |
| **JDK** | 11 | Necessário apenas se for rodar a aplicação fora do Gradle wrapper em modo dev |
| **Gradle** | — | Não precisa instalar: o projeto inclui o **Gradle Wrapper** (`gradlew` / `gradlew.bat`) |

> O projeto usa Spring Boot **2.6.2** e Kotlin **1.6.10** (alvo JVM 11).

## 🚀 Como rodar

### 1. Suba o broker RabbitMQ

Na raiz do repositório:

```bash
docker compose up -d
```

Isso sobe um container `rabbitmq:3.9.12-management` expondo:

- **5672** — porta AMQP (usada pela aplicação)
- **15672** — Management UI (acesse em [http://localhost:15672](http://localhost:15672), login `guest` / `guest`)

> ⚠️ **Observação**: o `docker-compose.yml` referencia um volume `./config/rabbitmq.conf` que **não existe** neste repositório. Se o Docker reclamar, remova (ou comente) as linhas do volume — o broker sobe normalmente com a configuração padrão.

### 2. Rode a aplicação

```bash
cd src/kotlin/app

# Linux / macOS / Git Bash
./gradlew bootRun

# Windows (PowerShell / CMD)
.\gradlew.bat bootRun
```

A aplicação sobe em **[http://localhost:8087](http://localhost:8087)** e, na inicialização, cria automaticamente no broker:

- Exchange: `letter.exchange.direct`
- Queue: `letter.queue` (com DLQ configurada)
- Dead Letter Queue: `letter.dead.letter.queue`

### 3. Publique uma mensagem

```bash
curl -X POST http://localhost:8087/message/publish \
  -H "Content-Type: application/json" \
  -d '{"text": "Hello, RabbitMQ!"}'
```

Resposta esperada: **HTTP 202 Accepted**. O consumer logará a mensagem recebida no console da aplicação.

Você também pode inspecionar as filas pela Management UI em [http://localhost:15672](http://localhost:15672).

## 🧪 Testes

```bash
cd src/kotlin/app
./gradlew test
```

## 📁 Estrutura do repositório

```
techtalk_rabbitmq/
├── docker-compose.yml          # Broker RabbitMQ (Docker)
├── techtalk_rabbitmq-00*.pdf   # Slides do tech talk
└── src/kotlin/app/             # Aplicação Spring Boot + Kotlin
    ├── build.gradle.kts
    └── src/main/kotlin/com/engsoft/rabbitmq/
        ├── domain/             # Modelos e interfaces (portas)
        ├── application/        # Serviços de aplicação (producer/consumer)
        ├── Infrastructure/     # Implementações RabbitMQ, mappers e config
        └── presentation/       # Controllers REST
```

## ⚙️ Configuração

As propriedades da aplicação estão em [src/kotlin/app/src/main/resources/application.yml](src/kotlin/app/src/main/resources/application.yml) — host/porta do broker, credenciais, política de retry e nomes de exchange/queues.
