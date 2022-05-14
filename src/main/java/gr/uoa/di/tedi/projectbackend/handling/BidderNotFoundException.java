package gr.uoa.di.tedi.projectbackend.users.handling;

import gr.uoa.di.tedi.projectbackend.users.service.BidderService;

public class BidderNotFoundException extends RuntimeException{
    public BidderNotFoundException(Long id){super("Could not find Bid " + id);}
}
