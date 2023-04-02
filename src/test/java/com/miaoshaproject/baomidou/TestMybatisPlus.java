package com.miaoshaproject.baomidou;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miaoshaproject.dao.MpUserDOMapper;
import com.miaoshaproject.dataobject.MpUserDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @description: please add a comment
 * @author: wangshuo
 * @date: 2023-03-31 15:14:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestMybatisPlus {

    @Autowired
    private MpUserDOMapper mpUserDOMapper;

    @Test
    public void selectList() {

        mpUserDOMapper.selectList(null).stream().forEach(System.out::println);
    }

    @Test
    public void testInsert() {

        MpUserDO mpUserDO = new MpUserDO("zhangsan", "123", "张三", 18, "zhangsan@qq.com");
        mpUserDO.setId(1L);
        int insert = mpUserDOMapper.insert(mpUserDO);
        System.out.println(insert);
        System.out.println("mpUserDO.getId() = " + mpUserDO.getId()); //id自动回填
    }

    @Test
    public void testSelectById() {
        System.out.println(mpUserDOMapper.selectByPrimaryKey(2L));
    }

    @Test
    public void testUpdateById() {

        MpUserDO mpUserDO = new MpUserDO("userNameA", "passwordA", "nameA", 188, "emailA");
        mpUserDO.setId(6L);
        int i = mpUserDOMapper.updateByPrimaryKey(mpUserDO);
        System.out.println("受影响的条数 = " + i);
    }

    @Test
    public void testUpdateByQueryWrapper() {
        MpUserDO mpUserDO = new MpUserDO("userNameB", "passwordB", "nameB", 181, "emailB");
        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>(); //条件类
        queryWrapper.eq("emial", "test3@gmail.com"); // email = test3@gmail.com
        int result = mpUserDOMapper.update(mpUserDO, queryWrapper);
        System.out.println("result = " + result);
    }

    @Test
    public void testUpdateByUpdateWrapper() {

        UpdateWrapper<MpUserDO> mpUserDOUpdateWrapper = new UpdateWrapper<>(); //更新条件类
        mpUserDOUpdateWrapper
                .set("age", 21)
                .set("name", "nameOne")
                .eq("user_name", "wangwu"); //将userName = "王五"的那条数据修改为age = 21 、 name = "nameOne"
        int update = mpUserDOMapper.update(null, mpUserDOUpdateWrapper);//这里entity可以设置为null
        System.out.println(update);
    }

    @Test
    public void testDeleteById() {
        int i = mpUserDOMapper.deleteById(7L);
        System.out.println("条数 = " + i);
    }

    @Test
    public void testDeleteByMap() {
        //多个条件放到一个map 条件之间是And关系
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("user_name", "zhangsan");
        stringObjectHashMap.put("name", "张三");
        int i = mpUserDOMapper.deleteByMap(stringObjectHashMap);
        System.out.println("条数 = " + i);
    }

    @Test
    public void testDeleteByWrapper() {
        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", "sunqi").eq("password", "1234567");
        int delete = mpUserDOMapper.delete(queryWrapper);
        System.out.println("delete = " + delete);
    }

    @Test
    public void deleteByIds() {
        int i = mpUserDOMapper.deleteBatchIds(Arrays.asList(2L, 3L, 4L)); //根据ID进行批量删除
        System.out.println("条数 = " + i);
    }

    @Test
    public void testSelectByIds() {
        mpUserDOMapper.selectBatchIds(Arrays.asList(6L, 7L, 8L, 9L)).stream().forEach(System.out::println);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "name");
        MpUserDO mpUserDO = mpUserDOMapper.selectOne(queryWrapper); //只查询一条数据 若无匹配数据 返回null 若有多条 抛出异常
        System.out.println(mpUserDO);
    }

    @Test
    public void testSelectCount() {

        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("name", "name");
        queryWrapper.gt("age", 20); //gt : great than 、lt : less than
        System.out.println("条数为：" + mpUserDOMapper.selectCount(queryWrapper));
    }

    @Test
    public void testSelectList() {

        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("emial", "email");
        List<MpUserDO> mpUserDOS = mpUserDOMapper.selectList(queryWrapper);
        mpUserDOS.forEach(System.out::println);
    }

    @Test
    public void testSelectPage() {
        // IPage Mp提供的分页接口
        Page<MpUserDO> mpUserDOPage = new Page<MpUserDO>(1, 2); //每页2条数据 查询第一页
        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("emial", "email");
        Page<MpUserDO> mpUserDOPage1 = mpUserDOMapper.selectPage(mpUserDOPage, queryWrapper);
        System.out.println("总条数: getTotal() = " + mpUserDOPage1.getTotal());
        System.out.println("总页数: getPages() = " + mpUserDOPage1.getPages());
        mpUserDOPage1.getRecords().forEach(System.out::println); //data: getRecords()
    }

    @Test
    public void testAllEq() {
        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "name");
        paramMap.put("password", "password");
        paramMap.put("id", null);
        //queryWrapper.allEq(paramMap);
        queryWrapper.allEq(paramMap, false); //可以设置自动过滤null值
        mpUserDOMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    @Test
    public void testCompare() {
        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("password", "password") //equals
                .ge("age", 3) //great than or equals
                .between("id", 0L, 10L)
                .in("name", "张三", "lisi", "李四", "name");
        mpUserDOMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    @Test
    public void testLike() {
        QueryWrapper<MpUserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("password", "67"); // "%val"
        mpUserDOMapper.selectList(queryWrapper).forEach(System.out::println);
    }

    @Test
    public void testOrderBy() {
        mpUserDOMapper.selectList(new QueryWrapper<MpUserDO>().orderByDesc("id")).forEach(System.out::println); //根据ID倒排
    }

    @Test
    public void testOr() {
        mpUserDOMapper.selectList(new QueryWrapper<MpUserDO>()
                .eq("name", "name")
                .or()
                .eq("age", 18) //where name = "name" or age = 18;
        ).forEach(System.out::println);
    }

    @Test
    public void testSelectColumn() {
        mpUserDOMapper.selectList(new QueryWrapper<MpUserDO>()
                .select("name", "age", "emial")
        ).forEach(System.out::println); //select name, age, emial from mp_user;
    }
}
