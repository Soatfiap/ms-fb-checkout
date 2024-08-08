## O uso do Padrão SAGA Coreografada

### Desacoplamento de Serviços
Optamos pelo padrão SAGA coreografada devido à sua capacidade de promover o desacoplamento entre serviços. Nesse modelo, cada serviço se comunica através de eventos, eliminando a necessidade de um coordenador central. Isso não só facilita a manutenção como também melhora a escalabilidade, permitindo que cada serviço evolua e seja escalado de forma independente.

### Resiliência
A resiliência é um aspecto crítico em sistemas distribuídos. No padrão coreografado, a falha de um serviço não compromete o funcionamento dos demais. Cada serviço gerencia seus próprios eventos e estados, contribuindo para a robustez geral do sistema. Dessa forma, nosso sistema continua operando de maneira eficiente, mesmo diante de falhas pontuais.

### Simplicidade na Orquestração
Em vez de centralizar a lógica de controle em um único serviço (orquestrador), a coreografia distribui essa complexidade entre os serviços. Cada serviço conhece apenas os eventos aos quais deve reagir, simplificando o fluxo de controle e reduzindo a complexidade do sistema como um todo.

### Facilidade de Implementação com Mensageria
Utilizamos o RabbitMQ para facilitar a implementação do padrão coreografado. RabbitMQ permite que os serviços publiquem e consumam eventos de maneira assíncrona, alinhando-se perfeitamente com a natureza distribuída e reativa do padrão SAGA coreografada. Isso proporciona uma comunicação eficiente e resiliente entre os serviços.

### Adaptabilidade a Mudanças
O padrão SAGA coreografada oferece uma grande flexibilidade para adicionar ou modificar passos no processo. Como cada serviço só precisa saber quais eventos observar e como reagir a eles, mudanças podem ser implementadas sem impacto significativo nos serviços existentes. Isso torna o sistema mais adaptável e pronto para evoluir com as necessidades do negócio.

Em resumo, a escolha pelo padrão SAGA coreografada se baseia na busca por um sistema mais modular, resiliente e adaptável. Essa abordagem se mostrou adequada para nosso ambiente distribuído, onde a independência e a reatividade dos serviços são cruciais para o sucesso e a eficiência operacional.