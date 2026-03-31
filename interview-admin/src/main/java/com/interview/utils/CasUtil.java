package com.interview.utils;

/**
 * @program: smpe-admin
 * @description:
 * @author: sangjinchao
 * @create: 2023-12-12 19:18
 **/

import com.interview.system.domain.CasUserInfo;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author:
 * @Date:
 * @Description: 使用cas对接封装的cas返回的用户信息的对象
 */
public class CasUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CasUtil.class);
    /**
     * cas client 默认的session key
     */
    public final static String CAS = "_const_cas_assertion_";
    /**
     * 封装CasUserInfo
     *
     * @param request
     * @return
     */
    public static CasUserInfo getCasUserInfoFromCas(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(CAS);
        if (null == object) {
            return null;
        }
        Assertion assertion = (Assertion) object;
        return buildCasUserInfoByCas(assertion);
    }
    /**
     * 构建CasUserInfo
     *
     * @param assertion
     * @return
     */
    private static CasUserInfo buildCasUserInfoByCas(Assertion assertion) {
        if (null == assertion) {
            LOGGER.error(" Cas没有获取到用户 ");
            return null;
        }
        CasUserInfo casUserInfo = new CasUserInfo();
        String userName = assertion.getPrincipal().getName();
        LOGGER.info(" cas对接登录用户= " + userName);
        casUserInfo.setUserAccount(userName);
        //获取属性值
        Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
        Object name = attributes.get("cn");
        casUserInfo.setUserName(name == null ? userName : name.toString());
        casUserInfo.setAttributes(attributes);
        return casUserInfo;
    }
}
