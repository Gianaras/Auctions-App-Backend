package gr.uoa.di.tedi.projectbackend.handling;

public class BidderNotFoundException extends RuntimeException{
    public BidderNotFoundException(Long id){super("Could not find Bid " + id);}
}
