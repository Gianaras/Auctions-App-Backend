package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;
import gr.uoa.di.tedi.projectbackend.model.Seller;

import java.sql.Timestamp;
import java.util.List;

public interface CustomItemsRepository {

    List<Items> getOngoingItems(Timestamp current);
    List<Items> getOngoingItemsOfSeller(Timestamp current, Long sellerId);
    Seller getSellerFromItemsId(Long itemsId);
}
