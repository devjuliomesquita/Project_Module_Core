# Project_Core

## Introdução

O **Project_Core** é um alicerce **plug-and-play** para aplicações **Java com Spring**, projetado para fornecer um setup
inicial completo e robusto. Seu objetivo é permitir que os desenvolvedores foquem exclusivamente nas regras de negócio,
sem precisar lidar com configurações complexas e repetitivas.

Com ele, você tem uma base de projeto já estruturada, seguindo boas práticas de desenvolvimento, segurança e
escalabilidade.

Além disso, um **Docker Compose** já está pronto para subir todas as dependências do projeto, incluindo **PostgreSQL,
Keycloak, Grafana** e demais serviços necessários.

---

## Principais Recursos

### 🌐 Banco de Dados e Auditoria

- Integração com **PostgreSQL**, garantindo robustez e confiabilidade.
- Gerenciamento de migrations utilizando **Flyway**, permitindo versões controladas do banco de dados.
- **Auditoria de dados** via **Hibernate Envers**, possibilitando o rastreamento de alterações nas entidades.

### ⛓️ Autenticação e Autorização

- Implementação baseada em **Keycloak**, proporcionando um fluxo seguro de autenticação e permissões via OAuth2 e OpenID
  Connect.

### 📘 Documentação Automatizada

- **Swagger** configurado para documentar a API dinamicamente, facilitando a exploração dos endpoints.

### ⚡ Tratamento de Erros e Notificações

- Utiliza o **Notification Pattern** para padronizar e organizar o tratamento de exceções e mensagens na aplicação.

### 📢 Integração com Serviços Externos

- **Serviço de e-mail** configurado para envio de mensagens transacionais e notificativas.
- Integração com **buckets de armazenamento** (exemplo: **Amazon S3**), permitindo o upload e a gestão de arquivos na
  nuvem.

### 🗃️ Modelagem de Dados e Arquitetura DDD

- Exemplos prontos de relacionamentos entre entidades:
    - **One-to-Many**
    - **One-to-One**
    - **Many-to-Many**
    - **Lista de IDs**
- Estrutura baseada em **Domain-Driven Design (DDD)**, garantindo modularidade e escalabilidade do projeto.

### 📊 Observabilidade e Monitoramento

- **Centralização de logs** com **Elasticsearch** via **Loki**, garantindo rastreabilidade e análise de eventos.
- **Métricas com Prometheus**, permitindo a coleta de informações de desempenho e saúde da aplicação.
- **Tracing de requisições com Tempo**, possibilitando a análise detalhada do fluxo das chamadas dentro do ecossistema **Grafana**.

### 🛠️ Setup de Testes e Perfis de Ambiente

- Configuração de testes para facilitar **Testes End-to-End**, **Test Containers** e **Testes de Integração**.
- Suporte a múltiplos perfis de execução:
    - **test** (para execução de testes automatizados)
    - **development** (ambiente de desenvolvimento)
    - **production** (ambiente de produção otimizado)

### 🚀 Servidor Web

- Em vez do tradicional **Tomcat**, a aplicação utiliza **Undertow**, garantindo maior desempenho e menor consumo de
  recursos.

---

## ✅ Checklist de Recursos Implementados

- [x] Integração com **PostgreSQL**
- [x] Gerenciamento de migrations com **Flyway**
- [x] Auditoria de dados com **Hibernate Envers**
- [x] Autenticação e autorização com **Keycloak**
- [x] Documentação da API com **Swagger**
- [x] Tratamento de exceções com **Notification Pattern**
- [x] Serviço de e-mail configurado
- [x] Integração com **Amazon S3** (ou equivalente)
- [x] Modelagem baseada em **DDD**
- [x] Exemplos de relacionamentos entre entidades
- [x] Observabilidade: Logs com **Loki**, Métricas com **Prometheus**, Tracing com **Tempo**
- [x] Setup de testes com **Test Containers e Integração**
- [x] Suporte a múltiplos perfis (**test, development, production**)
- [x] Substituição do **Tomcat** pelo **Undertow**
- [x] **Docker Compose** para facilitar a inicialização das dependências do projeto

---

## ⚙️ Tecnologias Utilizadas

- **Java** + **Spring Boot**
- **PostgreSQL** + **Flyway**
- **Hibernate Envers** (Auditoria de Dados)
- **Keycloak** (OAuth2 / OpenID Connect)
- **Swagger** (API Docs)
- **Amazon S3** (ou equivalente)
- **Elasticsearch + Loki** (Log centralizado)
- **Prometheus** (Métricas)
- **Tempo (Tracing)**
- **Grafana** (Dashboard de monitoramento)
- **Undertow** (Servidor Web)

---

## 🚀 Como Utilizar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/Projec_Core.git
   ```
2. Configure as variáveis de ambiente (Exemplo no arquivo `.env.example`).
3. Suba os containers necessários via **Docker Compose**:
   ```bash
   docker-compose up -d
   ```
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

---

## 📚 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir **issues** e **pull requests**.

---

## 📝 Licença

Este projeto está sob a **licença MIT** - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

