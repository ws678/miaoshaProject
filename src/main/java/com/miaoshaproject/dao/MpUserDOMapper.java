package com.miaoshaproject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miaoshaproject.dataobject.MpUserDO;

public interface MpUserDOMapper extends BaseMapper<MpUserDO> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mp_user
     *
     * @mbg.generated Thu Mar 30 15:53:27 CST 2023
     */
    int insert(MpUserDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mp_user
     *
     * @mbg.generated Thu Mar 30 15:53:27 CST 2023
     */
    int insertSelective(MpUserDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mp_user
     *
     * @mbg.generated Thu Mar 30 15:53:27 CST 2023
     */
    MpUserDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mp_user
     *
     * @mbg.generated Thu Mar 30 15:53:27 CST 2023
     */
    int updateByPrimaryKeySelective(MpUserDO row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mp_user
     *
     * @mbg.generated Thu Mar 30 15:53:27 CST 2023
     */
    int updateByPrimaryKey(MpUserDO row);
}