package ma.ac.inpt.security.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import ma.ac.inpt.security.models.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{

}
