package org.archvile.tasks;

import org.archvile.services.ContestService;
import org.archvile.services.FriendService;
import org.archvile.services.TweetService;

import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class RetweetTask implements Runnable {

    private static Logger log = Logger.getLogger(RetweetTask.class);

    @Autowired
    private TimelineOperations timelineOperations;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private TweetService tweetService;

    private Set<Tweet> contests = new HashSet<>();
    private List<Long> retweetIds = new ArrayList<>();

    @Override
    @Scheduled(initialDelay = 2000, fixedDelay = 600000)  //Every 10 minutes
    public void run() {

        setRetweetIds();

        Iterator<Tweet> iterator = getContests().iterator();
        while (iterator.hasNext()) {
            Tweet contest = iterator.next();
            if (!alreadyRetweeted(contest)) {
                processContest(contest);
            }
            iterator.remove();
        }

        log.info("No more contests");
    }

    private void setRetweetIds() {
        if (retweetIds.isEmpty()) {
            retweetIds = tweetService.getRetweetIds();
        }
    }

    private boolean alreadyRetweeted(Tweet contest) {
        return retweetIds.contains(Long.valueOf(contest.getId()));
    }

    private void processContest(Tweet contest) {
        try {
            tweetService.saveTweetAsContestTweet(contest);
            timelineOperations.retweet(contest.getId());
            if (contest.getText().toLowerCase().contains("follow")) {
                friendService.follow(contest.getFromUserId());
            }
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            log.error("InterruptedException: " + ex.getMessage());
        } catch (OperationNotPermittedException ex) {
            log.error("OperationNotPermittedException: " + ex.getMessage());
        }
    }

    private Set<Tweet> getContests() {
        if (contests.isEmpty()) {
            log.info("Getting contest tweets...");
            contests = contestService.getPotentialContests();
            log.info("Found " + contests.size() + " contest tweets...");
        }
        return contests;
    }

}