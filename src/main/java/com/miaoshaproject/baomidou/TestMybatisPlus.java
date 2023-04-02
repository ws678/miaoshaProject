package com.miaoshaproject.baomidou;

import com.miaoshaproject.dao.MpUserDOMapper;
import com.miaoshaproject.dataobject.MpUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description: test
 * @author: wangshuo
 * @date: 2023-03-30 22:38:43
 */
@Controller("/mp")
@RequestMapping("/mp")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class TestMybatisPlus {

    @Autowired
    private MpUserDOMapper mpUserDOMapper;

    @GetMapping("/findAll")
    @ResponseBody
    public void findAll() {
        List<MpUserDO> mpUserDOS = mpUserDOMapper.selectList(null);
        mpUserDOS.forEach(System.out::println);
    }
}
