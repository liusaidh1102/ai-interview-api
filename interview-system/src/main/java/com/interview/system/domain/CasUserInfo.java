package com.interview.system.domain;

/**
 * @program: smpe-admin
 * @description:
 * @author: sangjinchao
 * @create: 2023-12-12 19:17
 **/

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Author:
 * @Date:
 * @Description: 返回的用户信息
 */
@Setter
@Getter
public class CasUserInfo {
    /** 用户名 */
    private String userName;
    /** 用户 */
    private String userAccount;
    /** 用户信息 */
    private Map<String, Object> attributes;
}
