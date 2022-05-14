package gr.uoa.di.tedi.projectbackend.users.handling;

public class SellerNotFoundException extends RuntimeException{
    public SellerNotFoundException(Long id){super("Could not find Bid " + id);}
}
