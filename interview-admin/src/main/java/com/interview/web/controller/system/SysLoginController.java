package com.interview.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.error.SaErrorCode;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.interview.common.constant.Constants;
import com.interview.common.core.domain.R;
import com.interview.common.core.domain.dto.RoleDTO;
import com.interview.common.core.domain.entity.SysMenu;
import com.interview.common.core.domain.entity.SysUser;
import com.interview.common.core.domain.model.EmailLoginBody;
import com.interview.common.core.domain.model.LoginBody;
import com.interview.common.core.domain.model.LoginUser;
import com.interview.common.core.domain.model.SmsLoginBody;
import com.interview.common.enums.DeviceType;
import com.interview.common.exception.user.UserException;
import com.interview.common.helper.LoginHelper;
import com.interview.common.utils.MessageUtils;
import com.interview.system.domain.CasUserInfo;
import com.interview.system.domain.VGxfnaiRsxx;
import com.interview.system.domain.VGxfnaiXsjbxx;
import com.interview.system.domain.vo.RouterVo;
import com.interview.system.mapper.VGxfnaiRsxxMapper;
import com.interview.system.mapper.VGxfnaiXsjbxxMapper;
import com.interview.system.service.ISysMenuService;
import com.interview.system.service.ISysUserService;
import com.interview.system.service.SysLoginService;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.exception.CasdoorAuthException;
import org.casbin.casdoor.service.CasdoorAuthService;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.dev33.satoken.exception.NotLoginException.NOT_TOKEN;
import static cn.dev33.satoken.exception.NotLoginException.NOT_TOKEN_MESSAGE;
import static com.interview.common.helper.LoginHelper.LOGIN_USER_KEY;
import static com.interview.common.helper.LoginHelper.USER_KEY;
/**
 * 登录验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
public class SysLoginController {

    private final SysLoginService loginService;
    private final ISysMenuService menuService;
    private final ISysUserService userService;

    @Resource
    private CasdoorAuthService casdoorAuthService;

    @Resource
    private final VGxfnaiRsxxMapper teacherMapper;

    @Resource
    private final VGxfnaiXsjbxxMapper studentMapper;
    @Autowired
    private SysLoginService sysLoginService;


//     /**
//      * cas 单点登录
//      * @param request
//      */
//     @GetMapping(value = "/hkyLogin")
//     @SaIgnore
//     public void loginByNameAndCardNo(HttpServletRequest request, HttpServletResponse response) throws IOException {
//         log.info("开始登录");
//         //如果没有登录，则返回抛出异常
// //        request.getParameterMap().containsKey("username");

//         Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
//         if(assertion == null){
//             throw NotLoginException.newInstance(String.valueOf(1), NOT_TOKEN, NOT_TOKEN_MESSAGE, null).setCode(SaErrorCode.CODE_11011);
//         }
//         //1.解析username
//         String username = assertion.getPrincipal().getName();
//         LoginUser loginUser;
//         VGxfnaiRsxx vGxfnaiRsxx = new VGxfnaiRsxx(1L,"张三","教授","教授","部门","1");
//         loginUser = builderLoginUser(vGxfnaiRsxx);
//         //3.构建loginuser
//         LoginHelper.loginByDevice(loginUser , DeviceType.PC);
//         // 获取token
//         String loginToken = StpUtil.getTokenValue();
//         // 生成令牌
//         log.info("当前用户token：{}",loginToken);
//         response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//         response.sendRedirect("http://10.104.64.6:3000/login?token=" +loginToken);
//     }

/**
     * cas 单点登录
     * @param request
     */
    @GetMapping(value = "/hkyLogin")
    @SaIgnore
    public void loginByNameAndCardNo(HttpServletRequest request, HttpServletResponse response,String signUrl) throws IOException {
        log.info("开始登录");
//         Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
//         if(assertion == null){
//             throw NotLoginException.newInstance(String.valueOf(1), NOT_TOKEN, NOT_TOKEN_MESSAGE, null).setCode(SaErrorCode.CODE_11011);
//         }
        //1.解析username
        // String studentId = assertion.getPrincipal().getName();

        String studentId = "20231714422";



        // Map<String, Object> attributes = assertion.getPrincipal().getAttributes();

        // log.info("属性：{}",attributes);

        // 获取昵称
        // String nickName = (String) attributes.get("comsys_realname");
        String nickName = "刘志文";
        log.info("昵称：{}",nickName);

        log.info("学号：{}",studentId);

        SysUser sysUser = userService.selectUserByUserName(studentId);
        if (ObjectUtil.isNull(sysUser)){
            sysUser = new SysUser();
            sysUser.setUserName(studentId);
            sysUser.setNickName(nickName);
            sysUser.setUserType("sys_user");
            sysUser.setStatus("0");
            userService.insertUser(sysUser);
        }
        String loginToken = sysLoginService.casLogin(sysUser);

        log.info("token:{}",loginToken);
        log.info("当前用户信息：{}",sysUser);
        // 生成令牌
        log.info("当前用户token：{}",loginToken);

        response.sendRedirect(signUrl + "?token=" +loginToken);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<Map<String, Object>> getInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
//        SysUser user = userService.selectUserById(loginUser.getUserId());
        SysUser user = new SysUser();
        user.setNickName(loginUser.getNickName());
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("user", user);
//        ajax.put("roles", loginUser.getRolePermission());
//        ajax.put("permissions", loginUser.getMenuPermission());
        return R.ok(ajax);
    }


//    /**
//     * 获取用户信息
//     *
//     * @return 用户信息
//     */
//    @GetMapping("getInfo")
//    public R<Map<String, Object>> getInfo() {
//        LoginUser loginUser = LoginHelper.getLoginUser();
//        SysUser user = userService.selectUserById(loginUser.getUserId());
//        Map<String, Object> ajax = new HashMap<>();
//        ajax.put("user", user);
//        ajax.put("roles", loginUser.getRolePermission());
//        ajax.put("permissions", loginUser.getMenuPermission());
//        return R.ok(ajax);
//    }


    @RequestMapping("toLogin")
    @SaIgnore
    public ModelAndView toLogin(String signUrl) {
//        "http://192.168.21.11:3000/login"
//        return new ModelAndView("redirect:" + casdoorAuthService.getSigninUrl(signUrl));
        String loginToken = StpUtil.getTokenValue();
        return new ModelAndView("http://10.104.64.6:3000/login?token=" +loginToken);
    }

    public static void loginByDevice(LoginUser loginUser, DeviceType deviceType) {
        SaStorage storage = SaHolder.getStorage();
        storage.set(LOGIN_USER_KEY, loginUser);
        storage.set(USER_KEY, loginUser.getUserId());
        SaLoginModel model = new SaLoginModel();
        if (ObjectUtil.isNotNull(deviceType)) {
            model.setDevice(deviceType.getDevice());
        }
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
//        UserType userType = UserType.getUserType(loginUser.getUserType());
//        if (userType == UserType.SYS_USER) {
//            model.setTimeout(86400).setActiveTimeout(1800);
//        } else if (userType == UserType.APP_USER) {
//            model.setTimeout(86400).setActiveTimeout(1800);
//        }
        StpUtil.login(loginUser.getLoginId(), model.setExtra(USER_KEY, loginUser.getUserId()));
        StpUtil.getTokenSession().set(LOGIN_USER_KEY, loginUser);
    }

    public LoginUser builderLoginUser(VGxfnaiXsjbxx user) {
        LoginUser result = new LoginUser();
        result.setNickName(user.getName());
        result.setUserId(user.getStudentId());
        result.setDeptName(user.getClasss());
        result.setUserType("sys_user");
        result.setRoleId(2L);
        List<RoleDTO> roles = new ArrayList<>();
        RoleDTO role = new RoleDTO();
        role.setRoleId(2L);
        role.setRoleName("普通角色");
        role.setRoleKey("common");
        roles.add(role);
        result.setRoles(roles);
        return result;
    }


    public LoginUser builderLoginUser(VGxfnaiRsxx user) {
        LoginUser result = new LoginUser();
//        result.setNickName(user.getTeacherName());
        result.setUsername(String.valueOf(user.getTeacherId()));
        result.setUserId(1L);
        result.setDeptName(user.getDepartment());
        result.setUserType("sys_user");
        result.setRoleId(1L);
        List<RoleDTO> roles = new ArrayList<>();
//        result.setMenuPermission(permissionService.getMenuPermission(user));
//        result.setRolePermission(permissionService.getRolePermission(user));
        RoleDTO role = new RoleDTO();
        role.setRoleId(1L);
        role.setRoleName("老师");
        role.setRoleKey("teacher");
        roles.add(role);
        result.setRoles(roles);
        return result;
    }



    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/login")
    public R<Map<String, Object>> login(@Validated @RequestBody LoginBody loginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
            loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 短信登录
     *
     * @param smsLoginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/smsLogin")
    public R<Map<String, Object>> smsLogin(@Validated @RequestBody SmsLoginBody smsLoginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.smsLogin(smsLoginBody.getPhonenumber(), smsLoginBody.getSmsCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 邮件登录
     *
     * @param body 登录信息
     * @return 结果
     */
    @PostMapping("/emailLogin")
    public R<Map<String, Object>> emailLogin(@Validated @RequestBody EmailLoginBody body) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.emailLogin(body.getEmail(), body.getEmailCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 小程序登录(示例)
     *
     * @param xcxCode 小程序code
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/xcxLogin")
    public R<Map<String, Object>> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.xcxLogin(xcxCode);
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = LoginHelper.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }










//    /**
//     * cas 单点登录
//     *
//     * @param request
//     */
//    @GetMapping(value = "/hkyLogin")
//    @SaIgnore
//    public Object loginByNameAndCardNo(HttpServletRequest request) {
//        CasUserInfo userInfo = CasUtil.getCasUserInfoFromCas(request);
//        log.info("userInfo = " + JSONUtil.parse(userInfo));
////        userInfo = {"userAccount":"201302026","attributes":{
////        "comsys_department":
////        "DEPARTMENTNAME:网络与信息化管理中心,DEPARTMENTIDENTIFY:10603",
////        "isFromNewLogin":"false",
////        "authenticationDate":"2023-12-27T15:40:26.763+08:00[Asia/Shanghai]",
////        "comsys_cardid":"410184198912224417",
////        "successfulAuthenticationHandlers":"RememberMeUsernamePasswordCaptchaAuthenticationHandler",
////        "comsys_username":"201302026",
////        "comsys_phone":"18238639276",
////        "comsys_name":"王俊阁",
////        "credentialType":"RememberMeUsernamePasswordCaptchaCredential",
////        "comsys_third_uuids":"weChat:otJm3wbJQelLIgIFffSST_CXZEWQ",
////        "comsys_email":"wangjunge@hist.edu.cn",
////        "authenticationMethod":"RememberMeUsernamePasswordCaptchaAuthenticationHandler",
////        "comsys_org_code":"10603",
////        "longTermAuthenticationRequestTokenUsed":"false",
////        "comsys_usertype":"1"},
////        "userName":"201302026"}
//
//        if(ObjectUtil.isNull(userInfo)) {
//            return R.fail("信息获取失败");
//        }
//
//        SysUser loginUser = userService.selectUserByUserName(userInfo.getUserAccount());
//        if(ObjectUtil.isNull(loginUser)) {
//            loginUser = new SysUser();
//            loginUser.setUserName(userInfo.getUserAccount());
//            loginUser.setNickName((String) userInfo.getAttributes().get("comsys_realname"));
//            loginUser.setAvatar(null);
//            loginUser.setEmail((String) userInfo.getAttributes().get("comsys_email"));
//            loginUser.setPhonenumber((String) userInfo.getAttributes().get("comsys_phone"));
//            loginUser.setUserType("sys_user");
////            loginUser.setSex(user.getGender());
//            userService.insertUser(loginUser);
//        }
//
//        Map<String, Object> ajax = new HashMap<>();
//        // 生成令牌
//        String loginToken = loginService.casLogin(loginUser);
//        ajax.put(Constants.TOKEN, loginToken);
//        return R.ok(ajax);
//    }

    @RequestMapping("casLogin")
    @SaIgnore
    public Object casLogin(String code, String state, HttpServletRequest request) {
        String token = "";
        CasdoorUser user = null;
        try {
            token = casdoorAuthService.getOAuthToken(code, state);
            user = casdoorAuthService.parseJwtToken(token);
        } catch (CasdoorAuthException e) {
            e.printStackTrace();
            return R.fail("code错误");
        }
        SysUser loginUser = userService.selectUserByUserName(user.getId());
        if(ObjectUtil.isNull(loginUser)) {
            loginUser = new SysUser();
            loginUser.setUserName(user.getId());
            loginUser.setNickName(user.getDisplayName());
            loginUser.setAvatar(user.getAvatar());
            loginUser.setEmail(user.getEmail());
            loginUser.setPhonenumber(user.getPhone());
            loginUser.setUserType("sys_user");
//            loginUser.setSex(user.getGender());
            userService.insertUser(loginUser);
        }

        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String loginToken = loginService.casLogin(loginUser);
        ajax.put(Constants.TOKEN, loginToken);
        return R.ok(ajax);

    }
}
