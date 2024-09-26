# Contas API

## OBSERVAÇÃO
 - O projeto foi desenvolvido utilizando Java 17.

## ANOTAÇÕES
 - A coleção postman está localizada dentro da pasta raiz
 - O CSV de exemplo para importação das contas está dentro da pasta resources
 - Para subir o projeto é necessário digitar docker-compose up
 - A autenticação é feita através do postman, via "Basic Auth".
     Username: desafio
     Password: contas
	


## ENDPOINTS
 - Cadastrar conta - POST
   - Cadastra uma conta, seguir o body na coleção

 - Atualizar conta - PUT
   - Atualiza qualquer dado da conta

 - Atualizar a situação de uma conta - PATCH
   - Atualiza apenas a situação da conta, as seguintes opções são:
     - ABERTA,
       VENCIDA,
       CANCELADA,
       PAGA

 - Carregar conta por ID - GET
   - Busca uma conta passando o ID como PATH PARAM

 - Carregar valor pago - GET
   - Carrega todo o valor pago no periodo escolhido

 - Carregar contas - GET
   - Carrega uma lista de contas com as seguintes opções de filtro:
     - data-vencimento-inicial - Data inicial
     - data-vencimento-final - Data final
     - descricao - A descricao da conta
     - pagina - A pagina que vai carregar
     - maximo-por-pagina - A quantidade por pagina

## LinkedIn
 - https://www.linkedin.com/in/luan-barbosa-meneghelli-773b5920a/
 
