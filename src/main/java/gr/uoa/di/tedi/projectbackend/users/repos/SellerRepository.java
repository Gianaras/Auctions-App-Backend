package gr.uoa.di.tedi.projectbackend.users.repos;

import gr.uoa.di.tedi.projectbackend.users.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {

}