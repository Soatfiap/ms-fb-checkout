package net.fiap.postech.fastburger.application.usecases;

import net.fiap.postech.fastburger.application.domain.Client;
import net.fiap.postech.fastburger.application.ports.outputports.client.FindClientByCpfOutPutPort;
import net.fiap.postech.fastburger.application.ports.outputports.client.SaveClientOutPutPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientUseCaseTest {

    private SaveClientOutPutPort saveClientOutPutPort;
    private FindClientByCpfOutPutPort findClientByCpfOutPutPort;
    private ClientUseCase clientUseCase;

    @BeforeEach
    void setUp() {
        saveClientOutPutPort = Mockito.mock(SaveClientOutPutPort.class);
        findClientByCpfOutPutPort = Mockito.mock(FindClientByCpfOutPutPort.class);

        clientUseCase = new ClientUseCase(saveClientOutPutPort, findClientByCpfOutPutPort);
    }

    @Test
    void find() {
        String cpf = "12345678901";
        Client client = new Client();
        when(findClientByCpfOutPutPort.find(anyString())).thenReturn(client);
        Client foundClient = clientUseCase.find(cpf);
        assertEquals(client, foundClient);
    }

    @Test
    void save() {
        Client client = new Client();
        when(saveClientOutPutPort.save(any(Client.class))).thenReturn(client);
        Client savedClient = clientUseCase.save(client);
        assertEquals(client, savedClient);
    }
}