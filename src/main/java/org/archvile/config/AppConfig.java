package org.archvile.config;

import org.archvile.beans.StaticData;
import org.archvile.beans.MongoProperties;

import com.mongodb.WriteConcern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = "org.archvile")
@PropertySource("classpath:bootstrap.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public MongoProperties mongoProperties() {
        return new MongoProperties(
                environment.getRequiredProperty("mongo-db-name"),
                environment.getRequiredProperty("mongo-model-base-package"),
                environment.getRequiredProperty("mongo-host-name"),
                environment.getRequiredProperty("mongo-port-number", Integer.class),
                environment.getProperty("mongo-write-concern", WriteConcern.class, WriteConcern.SAFE)
        );
    }

    /**
     * Get access to the Twitter API
     * @return
     */
    @Bean
    public TwitterTemplate twitterTemplate() {
        return new TwitterTemplate(
                environment.getProperty("twitter-consumer-key"),
                environment.getProperty("twitter-consumer-secret"),
                environment.getProperty("twitter-access-token"),
                environment.getProperty("twitter-access-secret")
        );
    }

    /**
     * Read timelines and post tweets
     * @return
     */
    @Bean
    public TimelineOperations timelineOperations() {
        return twitterTemplate().timelineOperations();
    }

    /**
     * Search tweets and view trends
     * @return
     */
    @Bean
    public SearchOperations searchOperations() {
        return twitterTemplate().searchOperations();
    }

    /**
     * Retrieve followers and friends
     * @return
     */
    @Bean
    public FriendOperations friendOperations() {
        return twitterTemplate().friendOperations();
    }

    /**
     * Retrieve user profile data
     * @return
     */
    @Bean
    public UserOperations userOperations() {
        return twitterTemplate().userOperations();
    }

    @Bean
    public StaticData staticData() {
        return new StaticData();
    }

}