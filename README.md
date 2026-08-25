# Endpoints da Aplicação

* **POST /agendar**: Agenda uma nova comunicação para um destinatário. O e-mail informado é validado e não pode existir outra comunicação cadastrada para o mesmo endereço. A data e hora do agendamento são geradas automaticamente pelo sistema.

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

## Integração com Notificação

A aplicação foi integrada ao serviço de **notificação** utilizando **Feign Client**, permitindo a comunicação entre os serviços através de chamadas HTTP.

A implementação dessa integração pode ser encontrada na seguinte feature:

* **Feature:** `feature/integracao-comunicacao-api`

