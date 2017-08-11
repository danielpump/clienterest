# Biblioteca de conexão Rest com o serviço de cadastro de status

Esta aplicação é uma biblioteca para conexão com o sistema de cadastro de status.

### Tecnologias

Essencialmente esta lib utiliza o spring e suas abstrações para as conexões

Spring Web 

String Test

Mockito

JUnit


### Deploy

Esta aplicação não executa sozinha pois é uma biblioteca.<br>
Mas para os outros sistemas a utilizarem é necessário executar o comando mvn clean install. <br>
Ess processo baixa as dependência e executa a suite de testes automatizados do componente.<br>

### Dependências

Java 8<br>
Maven 3

### Serviços acessados

As URLs da aplicação de cadastro de status que a biblioteca acessa são:

Requisição GET: http://localhost:8081/placa/consultar?numero=Placa do veículo<br>
Requisição GET: http://localhost:8081/placa/consultar?status=OK<br>
Requisição POST: http://localhost:8081/placa/cadastrar<br>
Requisição POST: http://localhost:8081/placa/atualizar?numero=Placa do veículo<br>
Requisição DELETE: http://localhost:8081/placa/excluir?numero=Placa do veículo


### Testes

Foram implementados testes de integração nos testes automatizados. <br>
Foi utilizado mock para os serviços externos que a aplicação acesso.<br>

### Considerações
Projeto foi liberado na versão 0.1.0. Mas não foi gerado nenhum tag;<br>
Não foram colocados logs na aplicação por falta de tempo, mas os logs seriam colocados com uma lib de aspecto para manter o código limpo e conseguir usar o log sem interferir na aplicação;<br>
O código foi feito em português por escolha pessoal, apesar de poder ser em inglês acredito que fica melhor escrito em português pelo modelo estar em português. <br>

