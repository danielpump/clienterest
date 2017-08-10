# Biblioteca de conexão Rest com o serviço de cadastro de status

Esta aplicação é uma biblioteca para conexão com o sistema de cadastro de status.

### Tecnologias

Essencialmente esta lib utiliza o spring e suas abstrações para as conexões

Spring Web 

String Test

Mockito

JUnit


### Deploy

Esta aplicação não executa sozinha pois é uma biblioteca

### Serviços acessados

As URLs da aplicação de cadastro de status que a biblioteca acessa são:

http://localhost:8081/placa/consultar?numero=Placa do veículo
http://localhost:8081/placa/consultar?status=OK
http://localhost:8081/placa/cadastrar
http://localhost:8081/placa/atualizar?numero=Placa do veículo
http://localhost:8081/placa/excluir?numero=Placa do veículo


### Testes

Foram implementados testes de integração nos testes automatizados. Foi utilizado mock para os serviçoes externos que a aplicação acesso.