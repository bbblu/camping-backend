package tw.edu.ntub.imd.camping.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.BooleanUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.UserCommentDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;
import tw.edu.ntub.imd.camping.exception.RentalRecordStatusException;

import java.util.EnumSet;

@RequiredArgsConstructor
@Component
public class RentalRecordNotCommentMapper implements RentalRecordStatusMapper {
    private static final EnumSet<RentalRecordStatus> CAN_CHANGE_TO_STATUS_SET = EnumSet.of(
            RentalRecordStatus.ALREADY_COMMENT
    );
    private final UserCommentDAO userCommentDAO;

    @Override
    public RentalRecordStatus getMappedStatus() {
        return RentalRecordStatus.NOT_COMMENT;
    }

    @Override
    public EnumSet<RentalRecordStatus> getCanChangeToStatusSet() {
        return CAN_CHANGE_TO_STATUS_SET;
    }

    @Override
    public void validate(RentalRecord record, RentalRecordStatus newStatus) throws RentalRecordStatusException {
        if (newStatus == RentalRecordStatus.ALREADY_COMMENT && isNotCompleteComment(record)) {
            throw new NotAlreadyCommentException();
        }
    }

    private boolean isNotCompleteComment(RentalRecord record) {
        ProductGroup productGroup = record.getProductGroupByProductGroupId();
        String productOwner = productGroup.getCreateAccount();
        String renter = record.getRenterAccount();
        return BooleanUtils.isFalse(
                userCommentDAO.existsByRentalRecordIdAndUserAccountAndCommentAccount(record.getId(), renter, productOwner) &&
                        userCommentDAO.existsByRentalRecordIdAndUserAccountAndCommentAccount(record.getId(), productOwner, renter)
        );
    }

    @Override
    public void beforeChange(RentalRecord record, RentalRecordStatus newStatus, Object payload) throws ClassCastException {

    }

    @Override
    public void afterChange(RentalRecord record, RentalRecordStatus originStatus, Object payload) throws ClassCastException {

    }

    private static class NotAlreadyCommentException extends RentalRecordStatusException {
        public NotAlreadyCommentException() {
            super("雙方尚未評價對方，無法更新至已評價");
        }

        @Override
        public String getReason() {
            return "NotAlreadyComment";
        }
    }
}
