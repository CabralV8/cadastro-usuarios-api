
# Projeto Usuário - Microsserviço de Gerenciamento de Usuários

Esta aplicação é um microsserviço para gerenciamento de usuários, endereços e telefones, desenvolvido em Java com Spring Boot. Possui autenticação via JWT, integração com API ViaCep para consulta de endereços pelo CEP, e endpoints REST para CRUD de usuários.


## Documentação da API

#### Retorna o usuário cadastrado

```http
  POST /usuario
```

| Parâmetro           | Tipo    | Descrição                                                  |
|:--------------------|:--------|:-----------------------------------------------------------|
| `UsuarioDTORecord	` | `JSON	` | **Obrigatório**: Dados do usuário (nome, email, senha etc) |

#### Login de usuário

```http
  POST /usuario/login
```

| Parâmetro          | Tipo   | Descrição                                 |
|:-------------------|:-------|:------------------------------------------|
| `UsuarioDTORecord` | `JSON` | **Obrigatório**: Email e senha do usuário |

#### Buscar usuário por e-mail

```http
  GET /usuario?email={email}
```

| Parâmetro | Tipo     | Descrição                         |
|:----------|:---------|:----------------------------------|
| `email`   | `string` | **Obrigatório**: Email do usuário |

#### Deletar usuário por e-mail

```http
  DELETE /usuario/{email}
```

| Parâmetro | Tipo     | Descrição                                        |
|:----------|:---------|:-------------------------------------------------|
| `email`   | `string` | **Obrigatório**: Email do usuário a ser deletado |

#### Atualizar dados do usuário

```http
  PUT /usuario
```

| Parâmetro          | Tipo     | Descrição                                     |
|:-------------------|:---------|:----------------------------------------------|
| `UsuarioDTORecord` | `JSON`   | **Obrigatório**: Dados do usuário a atualizar |
| `Authorization`    | `string` | **Obrigatório**: Token JWT do usuário         |

#### Atualizar endereço

```http
  PUT /usuario/endereco?id={id}
```

| Parâmetro           | Tipo   | Descrição                                      |
|:--------------------|:-------|:-----------------------------------------------|
| `id`                | `long` | **Obrigatório**. ID do endereço                |
| `EnderecoDTORecord` | `JSON` | **Obrigatório**: Dados do endereço a atualizar |

#### Atualizar telefone

```http
  PUT /usuario/telefone?id={id}
```

| Parâmetro           | Tipo   | Descrição                                      |
|:--------------------|:-------|:-----------------------------------------------|
| `id`                | `long` | **Obrigatório**. ID do telefone                |
| `TeleFoneDTORecord` | `JSON` | **Obrigatório**: Dados do telefone a atualizar |

#### Cadastrar endereço

```http
  POST /usuario/endereco
```

| Parâmetro           | Tipo     | Descrição                               |
|:--------------------|:---------|:----------------------------------------|
| `EnderecoDTORecord` | `JSON`   | **Obrigatório**: Dados do novo endereço |
| `Authorization`     | `string` | **Obrigatório**: Token JWT do usuário   |

#### Cadastrar telefone

```http
  POST /usuario/telefone
```

| Parâmetro           | Tipo     | Descrição                                |
|:--------------------|:---------|:-----------------------------------------|
| `TeleFoneDTORecord` | `JSON`   | **Obrigatório**:  Dados do novo telefone |
| `Authorization`     | `string` | **Obrigatório**: Token JWT do usuário    |

#### Buscar endereço por CEP

```http
  GET /usuario/endereco/{cep}
```

| Parâmetro | Tipo     | Descrição                              |
|:----------|:---------|:---------------------------------------|
| `cep`     | `string` | **Obrigatório**:  CEP a ser consultado |

## Variáveis de Ambiente

Para rodar este projeto, você precisará adicionar as seguintes variáveis de ambiente no seu application properties:

| Variável     | Descrição                                | Valor Padrão                           |
|:-------------|:-----------------------------------------|:---------------------------------------|
| `DB_URL`     | URL do banco de dados MySQL              | jdbc:mysql://localhost:3306/db_usuario |
| `DB_USER`    | Usuário do banco de dados                | root                                   |
| `DB_PASS`    | Senha do banco de dados                  | **Obrigatório**                        |
| `VIACEP_URL` | URL base para consulta de CEP via ViaCEP | https://viacep.com.br                  |



## Feedback

Se você tiver algum feedback, por favor deixe por meio de

**Email**: valberton77@gmail.com

**GitHub**: https://github.com/CabralV8

**LinkedIn**: www.linkedin.com/in/valbertoncabral



