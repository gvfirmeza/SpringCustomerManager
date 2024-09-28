package gvfirmeza.client_manager.repository;

import gvfirmeza.client_manager.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Long clientId;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setNome("John Doe");
        client.setEmail("john.doe@example.com");
        client.setCpf("123.456.789-00");
        client.setDataNascimento(LocalDate.of(1990, 1, 1));

        Client savedClient = clientRepository.save(client);
        clientId = savedClient.getId();
    }

    @Test
    void testFindById_Success() {
        Optional<Client> client = clientRepository.findById(clientId);
        assertTrue(client.isPresent());
        assertEquals("John Doe", client.get().getNome());
    }

    @Test
    void testExistsByEmail_True() {
        boolean exists = clientRepository.existsByEmail("john.doe@example.com");
        assertTrue(exists);
    }

    @Test
    void testExistsByEmail_False() {
        boolean exists = clientRepository.existsByEmail("unknown@example.com");
        assertFalse(exists);
    }

    @Test
    void testExistsByCpf_True() {
        boolean exists = clientRepository.existsByCpf("123.456.789-00");
        assertTrue(exists);
    }

    @Test
    void testExistsByCpf_False() {
        boolean exists = clientRepository.existsByCpf("987.654.321-00");
        assertFalse(exists);
    }

    @Test
    void testSaveClient() {
        Client newClient = new Client();
        newClient.setNome("Jane Doe");
        newClient.setEmail("jane.doe@example.com");
        newClient.setCpf("987.654.321-00");
        newClient.setDataNascimento(LocalDate.of(1995, 5, 5));

        Client savedClient = clientRepository.save(newClient);

        assertNotNull(savedClient.getId());
        assertEquals("Jane Doe", savedClient.getNome());
    }
}
