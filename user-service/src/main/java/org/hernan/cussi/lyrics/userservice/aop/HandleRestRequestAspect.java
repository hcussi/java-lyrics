package org.hernan.cussi.lyrics.userservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HandleRestRequestAspect extends LoggerAspect {

  // Define the pointcut for HandleRestRequest annotation
  @Pointcut("@annotation(org.hernan.cussi.lyrics.userservice.aop.HandleRestRequest)")
  public void handleAnnotationPointcut() {
  }

  @Before("handleAnnotationPointcut()")
  public void processBefore(JoinPoint joinPoint) {
    var hasArgs = joinPoint.getArgs().length > 0;
    var log = getLogger(joinPoint);
    if (log.isInfoEnabled()) {
      log.info(STR."REST method: \{joinPoint.getSignature().getName()}" + (hasArgs ? STR." called with: \{joinPoint.getArgs()[0]}" : ""));
    }
  }

  @AfterThrowing(value = "handleAnnotationPointcut()", throwing = "error")
  public void processAfter(JoinPoint joinPoint, Exception error) {
    throw new RuntimeException(STR."Failed for \{joinPoint.getSignature().getName()}", error);
  }

}
