package gr.uoa.di.tedi.projectbackend.repos;


import gr.uoa.di.tedi.projectbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
