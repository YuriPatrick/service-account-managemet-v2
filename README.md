### Desafio Dock

Projeto do desafio da Dock Tech de seleção. 
Neste desafio foi desenvolvido um sistema de gestão de conta bancaria, com as seguintes tabelas e os seguintes requisitos.
  
  - Pré-requisitos:
    ```
    * Desenvolver os recursos em API Rest que realizam operações bancárias com a entidade conta a seguir:
    ```
    | Contas | Tipo |
    |-|-|
    | idConta | Numérico |
    | idPessoa | Numérico |
    | saldo | Monetário |
    | limiteSaqueDiario | Monetário |
    | flagAtivo | Condicional |
    | tipoConta | Numérido |
    | dataCriacao | Data |

    ```
    * Tabela de transações realizadas na conta
    ```
    | Transacoes | Tipo |
    |-|-|
    | idTransacao | Numérico |
    | idConta | Numérico |
    | valor | Monetário |
    | dataTransacao | Data |

    ```
    * P.S.: Não é necessário realizar operações com a tabela pessoa, mas é necessária a criação da tabela para mapeamento da relação com a conta e enviar script de criação de pelo menos uma pessoa.
    ```

    | Pessoas | Tipo |
    |-|-|
    | idPessoa | Numérico |
    | nome | Texto |
    | cpf | Texto |
    | dataNascimento | Data |    

  - O que esperamos como escopo mínimo:
    ```
    * Implementar path que realiza a criação de uma conta;
    * Implementar path que realiza operação de depósito em uma conta;
    * Implementar path que realiza operação de consulta de saldo em determinada conta;
    * Implementar path que realiza operação de saque em uma conta;
    * Implementar path que realiza o bloqueio de uma conta;
    * Implementar path que recupera o extrato de transações de uma conta;
    ```
	
## Observação:

Para a execução do projeto é necessário a configuração do lombok e executar os scripts do BD para a criação de uma pessoa, conforme proposto no requisito.

## Requisições para Testes:

# Criação conta:
curl --location --request POST 'localhost:8080/v2/api/contas/cria' \
--header 'Content-Type: application/json' \
--data-raw '{
    "conta_id": 2,
        "pessoa": {
            "id": 1,
            "nome": "carlos",
            "cpf": "09836583507",
            "dataNascimento": "1997-01-23"
        },
        "tipoConta": "CONTA_CORRENTE",
        "saldo": 1000,
        "limiteSaqueDiario": 1000.00
}'

# Deposito em uma conta:
curl --location --request PUT 'localhost:8080/v2/api/transacoes/deposito/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "conta": {
        "conta_id": 1,
        "pessoa": {
            "id": 1,
            "nome": "mario",
            "cpf": "09836583505",
            "dataNascimento": "1997-01-23"
        },
        "tipoConta": "CONTA_CORRENTE"
    },
    "valor": 1000,
    "tipoTransacao": "DEPOSITO"
}'

# Consulta daldo em uma conta:
curl --location --request GET 'localhost:8080/v2/api/contas/consultarSaldo/1'

# Saque conta:
curl --location --request PUT 'localhost:8080/v2/api/transacoes/saque/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "conta": {
        "conta_id": 1,
        "pessoa": {
            "id": 1,
            "nome": "mario",
            "cpf": "09836583505",
            "dataNascimento": "1997-01-23"
        },
        "tipoConta": "CONTA_CORRENTE"
    },
    "valor": 1000,
    "tipoTransacao": "SAQUE"
}'

# Bloqueio de uma conta:
curl --location --request PUT 'localhost:8080/v2/api/contas/blocked/1'

# Extrato de uma conta:
curl --location --request GET 'localhost:8080/v2/api/transacoes/extratoCompleto/1'

# Extrato de uma conta por periodo:
curl --location --request GET 'localhost:8080/v2/api/transacoes/extratoPeriodo/1/21-01-2022/24-01-2022'
