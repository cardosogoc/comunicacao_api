# Endpoints da Aplicação

* **POST /agendar**: Agenda uma nova comunicação para um destinatário. O e-mail informado é obrigatório e validado, e não pode existir outra comunicação cadastrada para o mesmo endereço. A data e hora da comunicação são geradas automaticamente pelo sistema.

  **Body:**

  ```json
  {
    "nomeDestinatario": "Gabriel Cardoso",
    "emailDestinatario": "gabriel@email.com",
    "telefoneDestinatario": "5599999999999",
    "mensagem": "Sua comunicação foi agendada com sucesso.",
    "modoDeEnvio": "EMAIL"
  }
  ```

* **GET /?emailDestinatario={email}**: Consulta uma comunicação cadastrada através do e-mail do destinatário e retorna seus dados.

* **PATCH /cancelar?emailDestinatario={email}**: Localiza uma comunicação pelo e-mail do destinatário e altera seu status para cancelado.

## Tratamento de Exceções

A aplicação possui um **GlobalExceptionHandler**, utilizando `@RestControllerAdvice`, responsável por centralizar o tratamento das exceções e retornar respostas padronizadas para os diferentes tipos de erros da aplicação.

## Mapeamento de Objetos

O conversor de objetos da aplicação foi substituído pelo **MapStruct**, realizando o mapeamento entre `Record` e `Entity` de forma automática, reduzindo código repetitivo e facilitando a manutenção da aplicação.

## Envio de E-mails

A aplicação realiza o envio de e-mails diretamente através do **Spring Mail**, utilizando **Thymeleaf** para a criação dos templates das mensagens.

O sistema possui templates específicos para diferentes situações:

* **notificacao.html**: utilizado para o envio da comunicação inicial.
* **notificacao-atraso.html**: utilizado para notificar o destinatário quando uma comunicação permanece pendente por mais de 48 horas.

O envio das comunicações pendentes é verificado automaticamente através de um **Scheduled Task (Cron)**, responsável por identificar comunicações pendentes e realizar o envio da notificação de atraso.

## Tecnologias e Recursos

* Java 25
* Spring Boot
* Spring Data JPA
* MySQL
* Spring Mail
* Thymeleaf
* MapStruct
* Lombok
* Springdoc OpenAPI / Swagger
* Docker
* Scheduled Tasks

## Integração com Outros Serviços

A integração anterior com o microserviço de **notificação** foi removida. Atualmente, o `comunicacao_api` é responsável pelo próprio envio de e-mails, não dependendo de outro microserviço para realizar essa operação.

