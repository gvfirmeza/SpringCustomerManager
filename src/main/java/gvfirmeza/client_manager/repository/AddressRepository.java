package gvfirmeza.client_manager.repository;

import gvfirmeza.client_manager.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> { }
