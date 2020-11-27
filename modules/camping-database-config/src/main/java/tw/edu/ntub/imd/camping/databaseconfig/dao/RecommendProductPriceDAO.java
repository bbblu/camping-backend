package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.RecommendProductPrice;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.RecommendProductPriceId;

@Repository
public interface RecommendProductPriceDAO extends BaseViewDAO<RecommendProductPrice, RecommendProductPriceId> {

}
