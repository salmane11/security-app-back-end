package ma.ac.inpt.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.ac.inpt.security.models.Client;


//This will be AUTO IMPLEMENTED by Spring into a Bean called JpaRepository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
