package com.miaoshaproject.controller.viewobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author wangshuo
 * @Date 2022/7/18, 15:08
 * @Description: 測試
 */
@Data
public class TestExportExcelVO2 extends TestExportExcelVO {

    @Excel(name = "十一")
    private String ele;
}
