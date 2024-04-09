package org.hernan.cussi.lyrics.authenticationservice.config;

import org.hernan.cussi.lyrics.utils.mongo.MongoOffsetDateTimeReader;
import org.hernan.cussi.lyrics.utils.mongo.MongoOffsetDateTimeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Optional;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class MongoConfig {

    /**
     * Based on <a href="https://bootify.io/mongodb/created-and-last-modified-date-in-spring-data-mongodb.html">CreatedDate and LastModifiedDate in Spring Data MongoDB</a>
     * @return the Mongo conversion bean
     */
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new MongoOffsetDateTimeWriter(),
                new MongoOffsetDateTimeReader()
        ));
    }

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

}
