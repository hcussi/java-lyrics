package org.hernan.cussi.lyrics.clientservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hernan.cussi.lyrics.utils.aop.LoggerAspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HandleRestRequestAspect extends LoggerAspect {

  // Define the pointcut for HandleRestRequest annotation
  @Pointcut("@annotation(org.hernan.cussi.lyrics.utils.aop.HandleRestRequest)")
  public void handleAnnotationPointcut() {
  }

  @Before("handleAnnotationPointcut()")
  public void processBefore(JoinPoint joinPoint) {
    log(joinPoint.getTarget().getClass(), joinPoint.getArgs(), joinPoint.getSignature().getName());
  }

  @AfterThrowing(value = "handleAnnotationPointcut()", throwing = "error")
  public void processAfter(JoinPoint joinPoint, Exception error) {
    throw new RuntimeException(STR."Failed for \{joinPoint.getSignature().getName()}", error);
  }

}
