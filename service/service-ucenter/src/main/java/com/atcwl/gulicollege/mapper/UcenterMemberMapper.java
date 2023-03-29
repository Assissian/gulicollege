package com.atcwl.gulicollege.mapper;

import com.atcwl.gulicollege.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author atcwl
 * @since 2023-02-02
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer selectRegisterUserNo(String day);
}
