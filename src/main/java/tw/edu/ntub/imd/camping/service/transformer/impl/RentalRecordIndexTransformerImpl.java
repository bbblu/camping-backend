package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.bean.RentalRecordIndexBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecord;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordIndexTransformer;

@Component
public class RentalRecordIndexTransformerImpl implements RentalRecordIndexTransformer {
    @NonNull
    @Override
    public RentalRecord transferToEntity(@NonNull RentalRecordIndexBean rentalRecordIndexBean) {
        throw new UnsupportedOperationException("不支援的操作");
    }

    @NonNull
    @Override
    public RentalRecordIndexBean transferToBean(@NonNull RentalRecord rentalRecord) {
        RentalRecordIndexBean result = new RentalRecordIndexBean();
        result.setId(rentalRecord.getId());
        if (rentalRecord.getId() != null) {
            String idString = String.format("%06d", rentalRecord.getId());
            result.setIdString(String.join(" ", idString.split("")));
        }
        result.setStatus(rentalRecord.getStatus());
        result.setRentalDate(rentalRecord.getRentalDate());
        return result;
    }
}
