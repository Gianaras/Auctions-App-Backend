package gr.uoa.di.tedi.projectbackend.repos;

import gr.uoa.di.tedi.projectbackend.model.Items;

import java.sql.Timestamp;
import java.util.List;

public interface CustomItemsRepository {

    List<Items> getOngoingItems(Timestamp current);
}
