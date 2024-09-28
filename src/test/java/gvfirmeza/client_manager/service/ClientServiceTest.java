package gvfirmeza.client_manager.service;

import gvfirmeza.client_manager.exceptions.*;
import gvfirmeza.client_manager.model.Client;
import gvfirmeza.client_manager.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddClient_Success() {
        Client client = new Client();
        client.setNome("Test Client");
        client.setEmail("test@test.com");
        client.setCpf("12345678900");
        client.setDataNascimento(LocalDate.of(2000, 1, 1));

        when(clientRepository.existsByEmail(client.getEmail())).thenReturn(false);
        when(clientRepository.existsByCpf(client.getCpf())).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.addClient(client);

        assertNotNull(savedClient);
        assertEquals("Test Client", savedClient.getNome());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testAddClient_EmailAlreadyInUse() {
        Client client = new Client();
        client.setEmail("test@test.com");

        when(clientRepository.existsByEmail(client.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyInUseException.class, () -> clientService.addClient(client));
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void testAddClient_CpfAlreadyInUse() {
        Client client = new Client();
        client.setCpf("12345678900");

        when(clientRepository.existsByCpf(client.getCpf())).thenReturn(true);

        assertThrows(CpfAlreadyInUseException.class, () -> clientService.addClient(client));
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void testAddClient_UnderageClient() {
        Client client = new Client();
        client.setDataNascimento(LocalDate.of(2010, 1, 1)); // Cliente menor de 18 anos

        assertThrows(UnderageClientException.class, () -> clientService.addClient(client));
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void testUpdateClient_Success() {
        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setNome("Old Client");

        Client updatedClient = new Client();
        updatedClient.setNome("Updated Client");
        updatedClient.setEmail("updated@test.com");
        updatedClient.setCpf("98765432100");
        updatedClient.setDataNascimento(LocalDate.of(1990, 1, 1));
        updatedClient.setTelefone("123456789");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        Client result = clientService.updateClient(1L, updatedClient);

        assertEquals("Updated Client", result.getNome());
        assertEquals("updated@test.com", result.getEmail());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testUpdateClient_ClientNotFound() {
        Client client = new Client();
        client.setNome("Test Client");

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.updateClient(1L, client));
    }

    @Test
    void testGetAllClients() {
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());

        when(clientRepository.findAll()).thenReturn(clientList);

        List<Client> result = clientService.getAllClients();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClientById_Success() {
        Client client = new Client();
        client.setId(1L);
        client.setNome("Test Client");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getClientById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Client", result.get().getNome());
    }

    @Test
    void testGetClientById_ClientNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Client> result = clientService.getClientById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteClient_Success() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClient_ClientNotFound() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> clientService.deleteClient(1L));
        verify(clientRepository, never()).deleteById(1L);
    }
}