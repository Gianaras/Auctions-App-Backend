package gr.uoa.di.tedi.projectbackend.users.handling;

public class BidNotFoundException extends RuntimeException{
    public BidNotFoundException(Long id) {
        super("Could not find Bid " + id);
    }
}
