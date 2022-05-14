package gr.uoa.di.tedi.projectbackend.handling;

public class SellerNotFoundException extends RuntimeException{
    public SellerNotFoundException(Long id){super("Could not find Bid " + id);}
}
