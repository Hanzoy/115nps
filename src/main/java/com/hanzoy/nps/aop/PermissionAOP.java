package com.hanzoy.nps.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAOP {
    @Pointcut("")
    public void getClientList(){}
}
