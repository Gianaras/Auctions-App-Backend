package gr.uoa.di.tedi.projectbackend.service;

import gr.uoa.di.tedi.projectbackend.handling.ImageNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Image;
import gr.uoa.di.tedi.projectbackend.repos.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    public final ImageRepository repository;

    @Autowired
    public ImageService(ImageRepository repository){ this.repository = repository; }

    public Image getImage(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    public Image addImage(Image newImage){
        return repository.save(newImage);
    }

}
