package com.miaoshaproject.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.miaoshaproject.controller.viewobject.TestExportExcelVO;
import com.miaoshaproject.controller.viewobject.TestExportExcelVO2;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * @Author wangshuo
 * @Date 2022/7/18, 10:38
 * @Description: 测试导出Controller
 */
@Controller("/test-export")
@RequestMapping("/test-export")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class TestExportController {

    @RequestMapping(value = "/one")
    public void export(HttpServletResponse response){

        //造数据
        ArrayList<TestExportExcelVO> testExportExcelVOS = new ArrayList<>(3);
        ArrayList<TestExportExcelVO2> result = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            TestExportExcelVO vo = new TestExportExcelVO();

            vo.setTwo("Two-" + i);
            vo.setThree("Three-" + i);
            vo.setFour("Four-" + i);
            vo.setFive("Five-" + i);
            vo.setSix("Six-" + i);
            vo.setSeven("Seven-" + i);
            vo.setEight("Eight-" + i);
            vo.setNine("Nine-" + i);
            vo.setTen("Ten-" + i);
            testExportExcelVOS.add(vo);
        }
        for (TestExportExcelVO vo : testExportExcelVOS) {
            TestExportExcelVO2 vo2 = new TestExportExcelVO2();

            BeanUtils.copyProperties(vo, vo2);
            if (null != vo.getOne()) {
                if (vo.getOne() == 1) {
                    vo2.setOne(999);
                }
            }
            if (vo.getFive().equals("Five-2")){
                vo2.setFive("Five-222222");
            }
            vo2.setEle("哈哈");
            result.add(vo2);
        }

        //导出
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams(), TestExportExcelVO2.class, result);
        //响应到客户端
        OutputStream os = null;
        try {
            setResponseAttribute(response, "测试EasyPoi导出");
            os = response.getOutputStream();
            sheets.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //设置导出response属性
    public static void setResponseAttribute(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}