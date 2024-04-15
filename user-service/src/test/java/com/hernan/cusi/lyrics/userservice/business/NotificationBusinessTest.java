package com.hernan.cusi.lyrics.userservice.business;

import org.hernan.cussi.lyrics.userservice.LyricsUserServiceApplication;
import org.hernan.cussi.lyrics.userservice.business.NotificationBusiness;
import org.hernan.cussi.lyrics.userservice.model.User;
import org.hernan.cussi.lyrics.userservice.repository.UserRepository;
import org.hernan.cussi.lyrics.utils.message.BindingNames;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = LyricsUserServiceApplication.class)
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Import(TestChannelBinderConfiguration.class)
public class NotificationBusinessTest {

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private JwtDecoder jwtDecoder;

  @Autowired
  private OutputDestination outputDestination;

  @Autowired
  private NotificationBusiness notificationBusiness;

  @Test
  public void notifyUser_ok() {
    notificationBusiness.notifyUserCreation(
      User.builder().name("test").email("test@gmail.com").password("s3cret").build()
    );

    Message<byte[]> result = outputDestination.receive(100, BindingNames.USER_CREATED.label);
    assertThat(result).isNotNull();
    assertThat(new String(result.getPayload())).contains("\"name\":\"test\",\"email\":\"test@gmail.com\"");
  }

}
