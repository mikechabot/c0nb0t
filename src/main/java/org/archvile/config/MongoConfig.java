package org.archvile.config;

import org.archvile.beans.MongoProperties;
import org.archvile.repository.RepositoryPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackageClasses=RepositoryPackage.class)
public class MongoConfig extends AbstractMongoConfiguration {

    @Autowired
    private MongoProperties properties;

    @Bean
    @Override
    public MongoClient mongo() throws Exception {
        MongoClient client = new MongoClient(getHostName(), getPortNumber());
        client.setWriteConcern(properties.getWriteConcern());
        return client;
    }

    @Override
    protected String getMappingBasePackage() {
        return properties.getModelBasePackage();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return properties.getDatabaseName();
    }

    protected String getHostName() {
        return properties.getHostName();
    }

    protected int getPortNumber() {
        return properties.getPortNumber();
    }

}