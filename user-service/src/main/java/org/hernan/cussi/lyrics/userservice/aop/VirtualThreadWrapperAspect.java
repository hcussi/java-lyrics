package org.hernan.cussi.lyrics.userservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hernan.cussi.lyrics.utils.aop.LoggerAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${aspect.property.enabled:true}")
public class VirtualThreadWrapperAspect extends LoggerAspect {

  // Define the pointcut for HandleRestRequest annotation
  @Pointcut("@annotation(org.hernan.cussi.lyrics.userservice.aop.VirtualThreadWrapper)")
  public void handleAnnotationPointcut() {
  }

  @Around(value = "handleAnnotationPointcut()")
  public void around(ProceedingJoinPoint joinPoint) throws Throwable {
    Thread.ofVirtual().start(() -> {
      try {
        log(joinPoint.getTarget().getClass(), STR."Virtual thread started for: \{joinPoint.getSignature().getName()}");
        joinPoint.proceed();
      } catch (Throwable e) {
        log(joinPoint.getTarget().getClass(), joinPoint.getArgs(), joinPoint.getSignature().getName(), e);
      }
    });
  }

}
