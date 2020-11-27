package tw.edu.ntub.imd.camping.bean;

import lombok.Data;

@Data
public class ThirdPartyProductRecordIndexBean {
    private Integer id;
    private Integer brandId;
    private String brandName;
    private Integer type;
    private String typeName;
    private Integer subType;
    private String subTypeName;
    private Integer price;
}
