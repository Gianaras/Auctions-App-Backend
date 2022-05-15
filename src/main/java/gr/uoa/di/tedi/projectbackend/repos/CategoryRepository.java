package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}