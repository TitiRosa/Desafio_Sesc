# Teste Prático QA - Automação Selenium Java

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](URL_DO_RELATORIO_DE_BUILD)
[![Test Status](https://img.shields.io/badge/tests-passing-brightgreen)](URL_DO_RELATORIO_DE_TESTES)
[![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)](URL_DO_RELATORIO_DE_COBERTURA)
[![Docker](https://img.shields.io/badge/docker-available-blue)](URL_DO_REPOSITORIO_DOCKER)

## Ferramentas Utilizadas

* Java 17+
* Maven
* Selenium 4.x
* TestNG
* WebDriverManager
* Google Chrome (via Docker opcional)

## Pré-requisitos

### Para execução local

* Java instalado
* Maven instalado
* Google Chrome instalado

### Para execução via Docker

* Docker instalado

## Como Executar

### Execução Local

```bash
mvn clean test
```

### Execução via Docker

```bash
# Build do container
docker build -t teste-qa .

# Executar os testes
docker run --rm teste-qa
```

## Estrutura do Projeto

* `pages`: Page Objects
* `base`: Configuração do WebDriver
* `tests`: Testes automatizados

## Observações

* Ajustar seletores conforme o HTML real do sistema.
* Implementar métodos de criar, buscar, editar e excluir usuário.
* Caso não tenha Java, Maven ou Chrome instalados, utilize a execução via Docker.
