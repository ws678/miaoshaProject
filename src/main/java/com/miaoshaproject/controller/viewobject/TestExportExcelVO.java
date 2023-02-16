package com.miaoshaproject.controller.viewobject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author wangshuo
 * @Date 2022/7/18, 10:23
 * @Description: 测试easypoi导出
 */
@Data
public class TestExportExcelVO {


    @Excel(name = "第一列")
    private Integer one;

    @Excel(name = "第二列")
    private String two;

    @Excel(name = "第三列")
    private String three;

    @Excel(name = "第四列")
    private String four;

    @Excel(name = "第五列")
    private String five;

    @Excel(name = "第六列")
    private String six;

    @Excel(name = "第七列")
    private String seven;

    @Excel(name = "第八列")
    private String eight;

    @Excel(name = "第九列")
    private String nine;

    @Excel(name = "第十列")
    private String ten;
}
