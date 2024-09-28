package gvfirmeza.client_manager.repository;

import gvfirmeza.client_manager.model.Address;
import gvfirmeza.client_manager.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setNome("John Doe");
        client.setEmail("john.doe@example.com");
        client.setCpf("123.456.789-00");
        client.setDataNascimento(LocalDate.of(1990, 1, 1));
        clientRepository.save(client);
    }

    @Test
    void testSaveAddress() {
        Address address = new Address();
        address.setRua("Rua Teste");
        address.setNumero("123");
        address.setBairro("Centro");
        address.setCidade("São Paulo");
        address.setEstado("SP");
        address.setCep("12345-678");
        address.setCliente(client);

        Address savedAddress = addressRepository.save(address);

        assertNotNull(savedAddress.getId());
        assertEquals("Rua Teste", savedAddress.getRua());
        assertEquals("123", savedAddress.getNumero());
        assertEquals(client.getId(), savedAddress.getCliente().getId());
    }

    @Test
    void testFindById_Success() {
        Address address = new Address();
        address.setRua("Rua Teste");
        address.setNumero("123");
        address.setBairro("Centro");
        address.setCidade("São Paulo");
        address.setEstado("SP");
        address.setCep("12345-678");
        address.setCliente(client);
        addressRepository.save(address);

        Optional<Address> foundAddress = addressRepository.findById(address.getId());

        assertTrue(foundAddress.isPresent());
        assertEquals("Rua Teste", foundAddress.get().getRua());
        assertEquals("123", foundAddress.get().getNumero());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Address> foundAddress = addressRepository.findById(999L);
        assertFalse(foundAddress.isPresent());
    }
}
