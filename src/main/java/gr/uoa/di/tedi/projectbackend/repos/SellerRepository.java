package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller,Long>,CustomSellerRepository {

}