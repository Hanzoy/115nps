package com.hanzoy.nps.aop;

import com.hanzoy.nps.mapper.TunnelMapper;
import com.hanzoy.nps.mapper.UserMapper;
import com.hanzoy.nps.pojo.bo.TokenBO;
import com.hanzoy.nps.pojo.dto.CommonResult;
import com.hanzoy.nps.pojo.po.TunnelPO;
import com.hanzoy.nps.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class PermissionAOP {

    @Autowired
    UserService userService;

    @Resource
    UserMapper userMapper;

    @Resource
    TunnelMapper tunnelMapper;

    @Pointcut("execution(public com.hanzoy.nps.pojo.dto.CommonResult com.hanzoy.nps.service.NPSService+.*(..))")
    public void checkAuth(){}

    @Pointcut("execution(public com.hanzoy.nps.pojo.dto.CommonResult com.hanzoy.nps.service.NPSService+.*Tunnel(..))")
    public void checkTunnelAuth(){}

    /**
     * 根据token进行权限拦截
     * @param joinPoint 切入点
     * @return 拦截后业务结果
     */
    @Order(0)
    @Around("checkAuth()")
    public CommonResult checkAuth(ProceedingJoinPoint joinPoint){
        //获取请求参数上的token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[args.length - 1];

        //检查token
        userService.checkToken(token);

        //获取执行的方法名
        Class[] argsClass = new Class[args.length];
        for(int i=0; i<args.length; i++){
            argsClass[i] = args[i].getClass();
        }
        String method_name = joinPoint.getSignature().getName();
        Method method = null;
        try {
            method = joinPoint.getTarget().getClass().getMethod(method_name, argsClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assert method != null;
        String func = method.getAnnotation(Function.class).value();

        //获取token中储存的数据
        TokenBO tokenInfo = userService.getTokenInfo(token);

        //是否允许调用该接口
        boolean permissionAllowed = userMapper.selectAuth(tokenInfo.getId(), func);
        log.info("用户id:{},用户名:{},调用了 {} 接口，调用状态:{}", tokenInfo.getId(), tokenInfo.getName(), func, permissionAllowed?"允许调用":"拒绝调用");

        //接口权限拦截
        if(!permissionAllowed){
            return CommonResult.fail("A0300", "接口权限不足");
        }

        //执行业务方法
        try {
            return (CommonResult) joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Order(1)
    @Around("checkTunnelAuth()")
    public CommonResult checkTunnelAuth(ProceedingJoinPoint joinPoint){
        //获取tunnelId和用户token
        Object[] args = joinPoint.getArgs();
        String tunnelId = (String) args[0];
        String token = (String) args[args.length - 1];

        //获取token中的信息
        TokenBO tokenInfo = userService.getTokenInfo(token);

        boolean permissionAllowed;
        if(tokenInfo.getRole().equals("管理员")){
            //管理员可以修改所有隧道
            permissionAllowed = true;
        }else{
            TunnelPO tunnelPO = tunnelMapper.selectTunnelByTunnelAndCreator(tunnelId, tokenInfo.getId().toString());
            //如果不是该隧道创建者将无法修改隧道
            permissionAllowed = tunnelPO != null;
        }
        if(!permissionAllowed){
            return CommonResult.fail("A0300", "接口权限不足");
        }
        CommonResult proceed = null;
        try {
            proceed = (CommonResult) joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }
}
