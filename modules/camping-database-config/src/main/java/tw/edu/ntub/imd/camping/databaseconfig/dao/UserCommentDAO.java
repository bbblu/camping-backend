package tw.edu.ntub.imd.camping.databaseconfig.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.camping.databaseconfig.entity.UserComment;

@Repository
public interface UserCommentDAO extends BaseDAO<UserComment, Integer> {
    @Query("SELECT NULLIF(AVG(c.comment), 0) FROM UserComment c WHERE c.userAccount = :userAccount GROUP BY c.userAccount")
    Object getAverageCommentByUserAccount(@Param("userAccount") String userAccount);

    boolean existsByUserAccountAndCommentAccount(String account, String commentAccount);
}
