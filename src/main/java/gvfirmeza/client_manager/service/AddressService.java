package gvfirmeza.client_manager.service;

import gvfirmeza.client_manager.exceptions.AddressNotFoundException;
import gvfirmeza.client_manager.model.Address;
import gvfirmeza.client_manager.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address addressDetails) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado com o id: " + id));

        address.setRua(addressDetails.getRua());
        address.setNumero(addressDetails.getNumero());
        address.setBairro(addressDetails.getBairro());
        address.setCidade(addressDetails.getCidade());
        address.setEstado(addressDetails.getEstado());
        address.setCep(addressDetails.getCep());

        return addressRepository.save(address);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public void deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
        } else {
            throw new AddressNotFoundException("Endereço não encontrado com o id: " + id);
        }
    }
}