![Logo Compasso](https://compasso.com.br/wp-content/uploads/2020/07/LogoCompasso-Negativo.png)

# CatÃ¡logo de produtos

Sua tarefa Ã© implementar um catÃ¡logo de produtos com Java e Spring Boot.

## product-ms

Neste microserviÃ§o deve ser possÃ­vel criar, alterar, visualizar e excluir um determinado produto, alÃ©m de visualizar a lista de produtos atuais disponÃ­veis. TambÃ©m deve ser possÃ­vel realizar a busca de produtos filtrando por *name, description e price*.

### Formato

O formato esperado de um produto Ã© o seguinte:

```javascript
  {
    "id": "string",
    "name": "string",
    "description": "string",
    "price": 59.99
  }
```
Durante a criaÃ§Ã£o e alteraÃ§Ã£o, os campos *name, description e price* sÃ£o obrigatÃ³rios. Em relaÃ§Ã£o ao campo *price* o valor deve ser positivo.

### Endpoints

Devem ser disponibilizados os seguintes endpoints para operaÃ§Ã£o do catÃ¡logo de produtos:


| Verbo HTTP  |  Resource path    |           DescriÃ§Ã£o           |
|-------------|:-----------------:|------------------------------:|
| POST        |  /products        |   CriaÃ§Ã£o de um produto       |
| PUT         |  /products/{id}   |   AtualizaÃ§Ã£o de um produto   |
| GET         |  /products/{id}   |   Busca de um produto por ID  |
| GET         |  /products        |   Lista de produtos           |
| GET         |  /products/search |   Lista de produtos filtrados |
| DELETE      |  /products/{id}   |   DeleÃ§Ã£o de um produto       |

#### POST /products

Esse endpoint deve criar um novo produto na base de dados, ao receber o JSON do produto o mesmo deverÃ¡ ser validado em acordo com as regras da seÃ§Ã£o **Formato**, e, caso esteja vÃ¡lido, persistido na base de dados e retornado com o *id* gerado e HTTP 201.

Entrada:
```javascript
  {
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  }
```

Retorno:
```javascript
  {
    "id": "id gerado",
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  }
```

Em caso de algum erro de validaÃ§Ã£o, a API deve retornar um HTTP 400 indicando uma requisiÃ§Ã£o invÃ¡lida. O JSON retornado nesse caso deve seguir o seguinte formato:

```javascript
  {
    "status_code": integer,
    "message": "string"
  }
```
No campo *status_code* deve vir o cÃ³digo HTTP do erro de validaÃ§Ã£o (400 Bad Request). No campo *message* deverÃ¡ vir uma mensagem genÃ©rica indicando o erro ocorrido.

#### PUT /products/\{id\}

Esse endpoint deve atualizar um produto baseado no {id} passado via path param. Antes de alterar, deve ser consultada a base de dados pelo *id*, se o produto NÃƒO for localizado entÃ£o devolver um HTTP 404 ao cliente. Se localizar o produto, entÃ£o os campos *name, description e price* devem ser atualizados conforme recebidos no body da requisiÃ§Ã£o.

Entrada:
```javascript
  {
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  }
```

Retorno:
```javascript
  {
    "id": "id atualizado",
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  }
```

Importante que antes da atualizaÃ§Ã£o as mesmas regras de validaÃ§Ã£o do POST /products devem ser executadas para garantir consistÃªncia, e, se ocorrer erro retornar no mesmo formato:

```javascript
  {
    "status_code": integer,
    "message": "string"
  }
```

#### GET /products/\{id\}

Esse endpoint deve retornar o product localizado na base de dados com um HTTP 200. Em caso de nÃ£o localizaÃ§Ã£o do produto, a API deve retornar um HTTP 404 indicando que o recurso nÃ£o foi localizado, nÃ£o hÃ¡ necessidade de retornar um JSON (response body) nesse caso.

Retorno:
```javascript
  {
    "id": "id buscado",
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  }
```

#### GET /products

Nesse endpoint a API deve retornar a lista atual de todos os produtos com HTTP 200. Se nÃ£o existir produtos, retornar uma lista vazia.

Retorno com produtos:
```javascript
[
  {
    "id": "id produto 1",
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  },
  {
    "id": "id produto 2",
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  }
]
```

Retorno vazio:
```javascript
[]
```

#### GET /products/search

Nesse endpoint a API deve retornar a lista atual de todos os produtos filtrados de acordo com query parameters passados na URL.

Os query parameters aceitos serÃ£o: q, min_price e max_price.

**Importante: nenhum deles deverÃ¡ ser obrigatÃ³rio na requisiÃ§Ã£o**

Onde:

| Query param |  AÃ§Ã£o de filtro     
|-------------|:---------------------------------------------------------------:|
| q           |  deverÃ¡ bater o valor contra os campos *name* e *description*   |
| min_price   | deverÃ¡ bater o valor ">=" contra o campo *price*                |
| max_price   | deverÃ¡ bater o valor "<=" contra o campo *price*                |

**Exemplo: /products/search?min_price=10.5&max_price=50&q=superget**

Retorno com produtos filtrados/buscados:
```javascript
[
  {
    "id": "id produto 1",
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  },
  {
    "id": "id produto 2",
    "name": "nome",
    "description": "descriÃ§Ã£o",
    "price": <preco>
  }
]
```

Retorno vazio:
```javascript
[]
```

#### DELETE /products/\{id\}

Esse endpoint deve deletar um registro de produto na base de dados. Caso encontre o produto filtrando pelo *id* entÃ£o deve deletar e retornar um HTTP 200. Se o *id* passado nÃ£o foi localizado deve retornar um HTTP 404

## ValidaÃ§Ã£o

A validaÃ§Ã£o dos endpoints e sua correta funcionalidade serÃ¡ atravÃ©s de script automatizado. Logo, Ã© importante que vocÃª defina a porta do serviÃ§o como sendo 9999, ficando a base url entÃ£o: http://localhost:9999

TambÃ©m ocorrerÃ¡ avaliaÃ§Ã£o tÃ©cnica do cÃ³digo-fonte produzido, bem como eventual anÃ¡lise estÃ¡tica do mesmo.

## Entrega do cÃ³digo

VocÃª Ã© responsÃ¡vel por entregar o cÃ³digo da forma como achar mais adequado, bem como eventuais documentaÃ§Ãµes necessÃ¡rias para a execuÃ§Ã£o do seu microserviÃ§o.

### Bom desafio \m/