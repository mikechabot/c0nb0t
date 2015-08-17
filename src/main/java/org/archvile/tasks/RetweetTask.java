package org.archvile.tasks;

import org.archvile.services.FriendService;
import org.archvile.services.RateLimitService;
import org.archvile.services.ContestTweetService;
import org.archvile.util.DateUtil;

import org.springframework.scheduling.annotation.Async;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

@Component
public class RetweetTask {

    private static Logger log = Logger.getLogger(RetweetTask.class);

    private static final long DELAY_BETWEEN_RETWEETS_IN_SECONDS = 87;
    private static final long DELAY_BETWEEN_RETWEETS_IN_MILLISECONDS = DELAY_BETWEEN_RETWEETS_IN_SECONDS * 1000;

    @Autowired
    private TimelineOperations timelineOperations;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ContestTweetService contestTweetService;

    @Autowired
    private RateLimitService rateLimitService;

    private Set<Tweet> contests = new HashSet<>();
    private List<Long> retweetIds = new ArrayList<>();

    @Async
    @Scheduled(initialDelay = 2000, fixedRate = 30000)
    public void run() {

        log.info("Running RetweetTask, next run time: " + DateUtil.nowPlus(30000));

        setRetweetIds();

        if (contests.isEmpty()) {
            contests = contestTweetService.getPotentialContestTweets();
        }

        log.info("Found " + contests.size() + " contest tweets");
        Iterator<Tweet> iterator = contests.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            if (i % 10 == 0 && rateLimitService.isThrottleable()) {
                rateLimitService.throttle();
            }
            Tweet contest = iterator.next();
            if (!alreadyRetweeted(contest)) {
                processContest(contest);
                i++;
            }
            iterator.remove();
        }

        log.info("No more contests");
    }

    private void setRetweetIds() {
        if (retweetIds.isEmpty()) {
            retweetIds = contestTweetService.getRetweetIds();
        }
    }

    private boolean alreadyRetweeted(Tweet contest) {
        return retweetIds.contains(Long.valueOf(contest.getId()));
    }

    private void processContest(Tweet contest) {
        try {
            log.info("Retweeting user '" + contest.getFromUser() + "', tweet '" + contest.getText() + "'");
            contestTweetService.saveTweet(contest);
            timelineOperations.retweet(contest.getId());
            if (contest.getText().toLowerCase().contains("follow")) {
                friendService.follow(contest.getFromUserId());
            }
            Thread.sleep(DELAY_BETWEEN_RETWEETS_IN_MILLISECONDS);
        } catch (InterruptedException ex) {
            log.error("InterruptedException: " + ex.getMessage());
        } catch (OperationNotPermittedException ex) {
            log.error("OperationNotPermittedException: " + ex.getMessage());
        }
    }

}