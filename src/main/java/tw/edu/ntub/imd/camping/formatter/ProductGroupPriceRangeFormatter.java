package tw.edu.ntub.imd.camping.formatter;

import lombok.extern.log4j.Log4j2;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.MathUtils;
import tw.edu.ntub.imd.camping.enumerate.ProductGroupPriceRange;
import tw.edu.ntub.imd.camping.exception.UnknownProductGroupPriceRangeException;

import javax.annotation.Nonnull;
import java.text.ParseException;
import java.util.Locale;

@Log4j2
@Component
public class ProductGroupPriceRangeFormatter implements Formatter<ProductGroupPriceRange> {
    @Nonnull
    @Override
    public ProductGroupPriceRange parse(@Nonnull String text, @Nonnull Locale locale) throws ParseException {
        int ordinal = Integer.parseInt(text);
        ProductGroupPriceRange[] allRange = ProductGroupPriceRange.values();
        if (MathUtils.isInRangeExcludeEnd(ordinal, 0, allRange.length)) {
            return allRange[ordinal];
        } else {
            UnknownProductGroupPriceRangeException e = new UnknownProductGroupPriceRangeException(ordinal);
            log.error("不支援的價格範圍", e);
            throw e;
        }
    }

    @Nonnull
    @Override
    public String print(@Nonnull ProductGroupPriceRange object, @Nonnull Locale locale) {
        return String.valueOf(object.ordinal());
    }
}
