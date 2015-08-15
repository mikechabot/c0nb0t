package org.archvile;

import org.archvile.tasks.RetweetTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;

@Component
public class AppListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = Logger.getLogger(AppListener.class);

    @Autowired
    private RetweetTask retweet;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Starting c0nb0t...");
    }

}