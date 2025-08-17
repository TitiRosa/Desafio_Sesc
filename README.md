# Teste Prático QA - Automação Selenium Java

## Ferramentas Utilizadas
- Java 17+
- Maven
- Selenium 4.x
- TestNG
- WebDriverManager
- Google Chrome (via Docker)

## Pré-requisitos
- Docker instalado

## Como Executar via Docker
```bash
# Build do container
docker build -t teste-qa .

# Executar os testes
docker run --rm teste-qa
