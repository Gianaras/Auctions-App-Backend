package gr.uoa.di.tedi.projectbackend.handling;

import gr.uoa.di.tedi.projectbackend.service.BidderService;

public class BidderNotFoundException extends RuntimeException{
    public BidderNotFoundException(Long id){super("Could not find Bid " + id);}
}
