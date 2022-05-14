package gr.uoa.di.tedi.projectbackend.service;

import gr.uoa.di.tedi.projectbackend.handling.LocationNotFoundException;
import gr.uoa.di.tedi.projectbackend.model.Location;
import gr.uoa.di.tedi.projectbackend.repos.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository repository;

    @Autowired
    public LocationService(LocationRepository repository){ this.repository = repository; }

    public Location getLocation(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    public Location addLocation(Location newLocation) { return repository.save(newLocation); }

    public void deleteLocation(Long id) { repository.deleteById(id); }

    public Location updateUser(Location location) { return repository.save(location); }

    public List<Location> getAllLocations() { return repository.findAll(); }



}
