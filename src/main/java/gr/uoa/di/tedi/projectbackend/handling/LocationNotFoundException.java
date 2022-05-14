package gr.uoa.di.tedi.projectbackend.handling;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(Long id){super("Could not find Bid " + id);}
}
