package gr.uoa.di.tedi.projectbackend.users.repos;

import gr.uoa.di.tedi.projectbackend.users.model.Bidder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidderRepository extends JpaRepository<Bidder, Long> {
}