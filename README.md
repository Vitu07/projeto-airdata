# Projeto AIRDATA - Monitoramento da Qualidade do Ar

AIRDATA é uma plataforma web desenvolvida para monitorar a qualidade do ar em São Paulo, focando nos riscos à saúde. Fornece IQAr(Índice de qualidade de ar) em tempo real, níveis de risco e recomendações. Utiliza Java/Spring Boot, Angular, PostgreSQL.

## Tecnologias Utilizadas
* **Back-end:** Java 17, Spring Boot 3
* **Front-end:** Angular 16
* **Banco de Dados:** PostgreSQL 15
* **Build:** Maven

## Pré-requisitos (Software Mínimo)
Para rodar este projeto localmente, você precisará ter instalado:
* Java JDK 17 ou superior
* PostgreSQL 15 ou superior (e o PgAdmin 4)
* Node.js 18 ou superior (com NPM)
* Angular CLI 16 ou superior (`npm install -g @angular/cli`)
* Maven
* Uma IDE (ex: IntelliJ IDEA ou VS Code)

## Passos para Instalação (Back-end)

1.  **Clone o Repositório:**
    ```bash
    git clone https://github.com/Vitu07/projeto-airdata
    ```
2.  **Configure o Banco de Dados:**
    * Abra o PgAdmin e crie um novo banco de dados com o nome: `airdata`
3.  **Execute os Scripts SQL:**
    * Abra uma "Query Tool" no PgAdmin para o banco `airdata`.
    * Execute o script `schema.sql` (disponível no projeto) para criar todas as tabelas.
    * Execute o script `carga_dados.sql` (disponível no projeto) para popular o banco com dados de teste para todas as Sprints.
4.  **Configure a API Externa:**
    * Obtenha uma chave de API gratuita do site [AQICN (World Air Quality Index)](https://aqicn.org/data-platform/token/).
5.  **Configure o Projeto Java:**
    * Abra a pasta do back-end na sua IDE.
    * Navegue até `src/main/resources/application.properties`.
    * Altere as seguintes linhas com suas credenciais locais:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/airdata
        spring.datasource.username=postgres
        spring.datasource.password=[SUA_SENHA_DO_POSTGRES]

        # Adicione a chave da API obtida no passo 4
        api.aqicn.key=[SUA_CHAVE_DA_API_AQICN]
        ```
6.  **Execute o Back-end:**
    * Na raiz do projeto back-end, execute:
        ```bash
        mvn spring-boot:run
        ```
    * O servidor deve iniciar na porta `8080`.

## Passos para Instalação (Front-end)

1.  **Navegue até a pasta do Front-end:**
    ```bash
    cd [pasta-do-seu-frontend]
    ```
2.  **Instale as Dependências:**
    ```bash
    npm install
    ```
3.  **Execute o Front-end:**
    * Inicie o servidor de desenvolvimento do Angular:
        ```bash
        ng serve
        ```
4.  **Acesse a Aplicação:**
    * Abra seu navegador e acesse `http://localhost:4200/`.
    * A aplicação front-end irá se conectar automaticamente ao seu back-end local na porta `8080`.
