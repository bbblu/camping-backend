package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroupComment;

@Repository
public interface ProductGroupCommentDAO extends BaseDAO<ProductGroupComment, Integer> {
    @Query("SELECT NULLIF(AVG(c.comment), 0) FROM ProductGroupComment c WHERE c.groupId = :groupId GROUP BY c.groupId")
    Object getAverageCommentByGroupId(@Param("groupId") Integer groupId);

    Boolean existsByGroupIdAndCommentAccount(int id, String commentAccount);
}
