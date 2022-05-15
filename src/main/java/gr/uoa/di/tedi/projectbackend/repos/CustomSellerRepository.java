package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;
import java.util.List;


public interface CustomSellerRepository {

    public List<Items> getListings(Long sellerID);


}
