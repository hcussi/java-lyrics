package com.hernan.cusi.lyrics.userservice.business;

import org.hernan.cussi.lyrics.userservice.LyricsUserServiceApplication;
import org.hernan.cussi.lyrics.userservice.business.NotificationBusiness;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.hernan.cussi.lyrics.utils.message.BindingNames;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = LyricsUserServiceApplication.class)
@Import(TestChannelBinderConfiguration.class)
public class NotificationBusinessTest {

  @Autowired
  private OutputDestination outputDestination;

  @Autowired
  private NotificationBusiness notificationBusiness;

  @Test
  public void notifyUser_ok() {
    notificationBusiness.notifyUserCreation(
      User.builder().name("test").email("test@gmail.com").build()
    );

    Message<byte[]> result = outputDestination.receive(100, BindingNames.USER_CREATED.label);
    assertThat(result).isNotNull();
    assertThat(new String(result.getPayload())).contains("\"name\":\"test\",\"email\":\"test@gmail.com\"");
  }

}
