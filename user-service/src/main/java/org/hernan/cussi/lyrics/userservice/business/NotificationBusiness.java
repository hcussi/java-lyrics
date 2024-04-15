package org.hernan.cussi.lyrics.userservice.business;

import lombok.extern.slf4j.Slf4j;
import org.hernan.cussi.lyrics.userservice.aop.VirtualThreadWrapper;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.hernan.cussi.lyrics.utils.message.BindingNames;
import org.hernan.cussi.lyrics.utils.message.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.Date;

@Service
@Slf4j
public class NotificationBusiness {

  private final StreamBridge streamBridge;

  @Autowired
  public NotificationBusiness(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  @VirtualThreadWrapper
  public void notifyUserCreation(final User user) {
    try {
      var userInfo = new UserInfo(user.getName(), user.getEmail(), user.getPassword(), new Date());
      streamBridge.send(BindingNames.USER_CREATED.label, userInfo, MimeTypeUtils.APPLICATION_JSON);
      log.info(STR."Message send to binding name: \{BindingNames.USER_CREATED.label}");
    } catch (Exception ex) {
      log.error(STR."Failed to send message to: \{BindingNames.USER_CREATED.label}", ex);
    }

  }
}
