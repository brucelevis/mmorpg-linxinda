package com.wan37.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Deprecated
public class GeneralDipatchHandlerMapInitializer implements ApplicationContextAware {

  public void init(Map<Integer,GeneralHandler> handlerMap){
      // NOOP
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
