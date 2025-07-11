# Sistema de Gerenciamento de Biblioteca EvoluLibrary

Este mini-projeto consiste no desenvolvimento de um sistema para gerenciamento de uma biblioteca, com o objetivo de facilitar o controle de livros, autores e empréstimos realizados aos usuários. O sistema deve permitir o cadastro de novos livros, usuários e autores, bem como gerenciar o processo de empréstimo e devolução de livros, garantindo que as regras de disponibilidade sejam respeitadas.

Para o desenvolvimento, está sendo utilizado a linguagem **Java** para a implementação da lógica do sistema e o banco de dados **SQLite** para o armazenamento das informações.

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

```
EvoluLibrary/
├── bin/ 
├── doc/ 
├── lib/ 
├── src/
│   └── librarybooks/
│       ├── Main.java
│       ├── dao/
│       │   ├── AuthorDAO.java
│       │   ├── BookDAO.java
│       │   ├── LoanDAO.java
│       │   └── UserDAO.java
│       ├── database/
│       │   └── Database.java
│       ├── model/
│       │   ├── Author.java
│       │   ├── Book.java
│       │   ├── Loan.java
│       │   └── User.java
│       └── service/ 
│           └── MenuService.java
└── library.db
```

- `src/librarybooks/`: Contém todo o código-fonte Java da aplicação.
- `Main.java`: Ponto de entrada da aplicação. Inicializa o banco de dados e o menu principal.
- `model/`: Contém as classes que representam as entidades do sistema.
- `dao/`: Contém as classes de acesso a dados que interagem com o banco de dados para cada entidade.
- `database/`: Contém a classe `Database` responsável pela inicialização e conexão com o banco de dados SQLite.
- `service/`: Contém o `MenuService` que gerencia as operações da biblioteca.
- `lib/`: Contém a biblioteca JDBC para SQLite (`sqlite-jdbc-3.50.1.0.jar`).
- `library.db`: O arquivo do banco de dados SQLite que será criado ou utilizado pelo sistema.

## Como Rodar o Projeto

Necessário Java Development Kit (JDK) instalado versão 19 ou superior.

## Compilação e Execução no Windows

### Compilação
Para compilar os arquivos Java, você precisará usar o comando `javac`. No PowerShell, você pode usar o seguinte comando para encontrar todos os arquivos `.java` e compilá-los:

```powershell
Get-ChildItem -Path . -Recurse -Include *.java | ForEach-Object { $_.FullName } > sources.txt
javac -d ..\bin -cp ..\lib\sqlite-jdbc-3.50.1.0.jar (Get-Content sources.txt)
```

### Execução

Após a compilação bem-sucedida, navegue até o diretório `bin` do projeto. Se você estava em `C:\Projetos\EvoluLibrary\src`, o comando seria:

```powershell
cd ..\bin
```

Agora, execute a aplicação usando o seguinte comando:

```powershell
java -cp ".;..\lib\sqlite-jdbc-3.50.1.0.jar" librarybooks.Main
```

**Observação:** Se você ainda encontrar o erro "No suitable driver found for jdbc:sqlite:library.db", pode ser necessário adicionar a linha `Class.forName("org.sqlite.JDBC");` no início do método `main` em `src\librarybooks\Main.java`, antes da chamada `Database.getConnection();`.

Ao executar, você será apresentado a um menu inicial no console do PowerShell, onde poderá interagir com o sistema de gerenciamento de biblioteca.

**Você também pode optar por rodar a ```Main.java``` na sua IDE de preferência**. 

## Compilação e Excução no Linux ou Mac

### Compilação
Navegue até o diretório `EvoluLibrary/src` e execute os seguintes comandos para compilar os arquivos Java:

```bash
find . -name "*.java" > sources.txt
javac -d ../bin -cp ../lib/sqlite-jdbc-3.50.1.0.jar @sources.txt
```

Estes comandos primeiro encontram todos os arquivos `.java` no diretório `src` e seus subdiretórios, salvam seus caminhos em `sources.txt`, e então compilam esses arquivos para o diretório `bin`, incluindo a biblioteca SQLite JDBC no classpath.

### Execução

Após a compilação, navegue até o diretório `EvoluLibrary/bin` e execute a aplicação:

```bash
java -cp .:../lib/sqlite-jdbc-3.50.1.0.jar librarybooks.Main
```

**Você também pode optar por rodar a ```Main.java``` na sua IDE de preferência**. 

Ao executar da maneira que achar mais adequado, você será apresentado a um menu inicial. Você pode interagir com o sistema através das opções do menu.

## Documentação com Javadoc

Para ajudar na organização do sistema, todas as classes e métodos públicos foram documentados usando o Javadoc que você pode acessar clicando [AQUI](https://pedro-hsf.github.io/EvoluLibrary/) ou na área "sobre", no canto superior direito no inicio do repositório.

