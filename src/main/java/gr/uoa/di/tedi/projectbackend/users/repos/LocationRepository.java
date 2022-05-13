package gr.uoa.di.tedi.projectbackend.users.repos;

import gr.uoa.di.tedi.projectbackend.users.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}