package tw.edu.ntub.imd.camping.service.transformer.impl;

import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.RentalRecordCheckLogBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.RentalRecordCheckLogImageDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLog;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalRecordCheckLogImage;
import tw.edu.ntub.imd.camping.service.transformer.RentalRecordCheckLogTransformer;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RentalRecordCheckLogTransformerImpl implements RentalRecordCheckLogTransformer {
    private final RentalRecordCheckLogImageDAO imageDAO;

    @NonNull
    @Override
    public RentalRecordCheckLog transferToEntity(@NonNull RentalRecordCheckLogBean rentalRecordCheckLogBean) {
        return JavaBeanUtils.copy(rentalRecordCheckLogBean, new RentalRecordCheckLog());
    }

    @NonNull
    @Override
    public RentalRecordCheckLogBean transferToBean(@NonNull RentalRecordCheckLog log) {
        RentalRecordCheckLogBean result = JavaBeanUtils.copy(log, new RentalRecordCheckLogBean());
        if (log.getId() != null) {
            result.setImageUrlList(
                    imageDAO.findByLogId(log.getId())
                            .stream()
                            .map(RentalRecordCheckLogImage::getUrl)
                            .collect(Collectors.toList())
            );
        }
        return result;
    }
}
