# 🧪 Teste Prático QA - Automação Selenium Java

---

## 🛠 Ferramentas Utilizadas

- ☕ Java 17+
- 📦 Maven
- 🌐 Selenium 4.x
- 🧪 TestNG
- 🖥 WebDriverManager
- 🌟 Google Chrome (via Docker opcional)

---

## 📋 Pré-requisitos

### 💻 Para execução local

- Java instalado
- Maven instalado
- Google Chrome instalado

### 🐳 Para execução via Docker

- Docker instalado
- O container Docker já inclui Maven com JDK 17 e Google Chrome instalado

---

## ▶️ Como Executar

### 🔹 Execução Local

```bash
mvn clean test
```

### 🔹 Execução via Docker

```bash
# Build do container
docker build -t teste-qa .

# Executar os testes
docker run --rm teste-qa
```

---

## 📦 Conteúdo do Dockerfile

```dockerfile
# Use Maven com JDK 17
FROM maven:3.9.0-openjdk-17

# Instala dependências do Chrome
RUN apt-get update && apt-get install -y wget unzip xvfb libxi6 libgconf-2-4

# Baixa e instala o Google Chrome
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb || apt-get -f install -y

# Define diretório de trabalho
WORKDIR /app

# Copia todos os arquivos do projeto para o container
COPY . .

# Comando padrão para rodar os testes
CMD ["mvn", "clean", "test"]
```

---

## 📂 Estrutura do Projeto

- **pages:** Page Objects — centraliza todos os elementos e métodos de cada página, facilitando manutenção e evitando duplicação de código.
- **base:** Configuração do WebDriver — gerencia inicialização e finalização do navegador, garantindo consistência nos testes.
- **tests:** Testes automatizados — contém os cenários de teste que utilizam os objetos de página e a configuração base, separando lógica de teste da interface.

**Vantagens desta estrutura:**

- ✅ Organização e clareza entre páginas, configuração e testes.
- 🛠 Facilita manutenção e atualização de seletores ou métodos.
- ♻️ Permite reuso de código, tornando os testes mais robustos e menos frágeis.

---

## ⚠️ Fragilidades de Segurança e Usabilidade

**Segurança:**

- Permite múltiplas tentativas de login com senha incorreta sem bloqueio ou limitação.
- Representa risco de ataques de força bruta.

**Usabilidade:**

- Mensagem de erro genérica ao inserir login ou senha incorretos, sem indicar o campo incorreto.
- Em alguns casos de teste, ao criar um usuário e selecionar a UF, o sistema já emite mensagem de que deve conter perfil antes do usuário chegar a essa etapa.
- Pode gerar confusão e retrabalho para o usuário.

**Como reportar:**

- Registrar no sistema de bugs ou documentação interna, sugerindo:
  - Implementar bloqueio temporário ou CAPTCHA após múltiplas tentativas de login.
  - Exibir mensagens de erro claras, indicando se o problema está no login ou na senha.
  - Ajustar a validação do cadastro de usuários para que a etapa de seleção de perfil ocorra no momento correto.

---

## 📝 Relatório de Testes Manuais

O relatório completo, incluindo evidências, está disponível em:

[📄 Relatório de Testes Manuais (PDF)](Relatorio_de_Teste_Manuais_e_Evidencias.pdf)

> Clique no link acima para abrir o PDF diretamente no GitHub.

---

## 🔍 Observações

- Ajustar seletores conforme o HTML real do sistema.
- Implementar métodos de criar, buscar, editar e excluir usuário.
- Caso não tenha Java, Maven ou Chrome instalados, utilize a execução via Docker.

---

## ✅ Considerações Finais

O projeto cobre cenários positivos e de exceção, seguindo boas práticas de organização, permitindo fácil manutenção e escalabilidade.

