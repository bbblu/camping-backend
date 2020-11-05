package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.CanBorrowProductGroup;

import java.util.List;

@Repository
public interface CanBorrowProductGroupDAO extends BaseViewDAO<CanBorrowProductGroup, Integer> {
    List<CanBorrowProductGroup> findByCreateAccount(String createAccount);
}
