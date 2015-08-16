package org.archvile.tasks;

import org.archvile.services.ContestService;
import org.archvile.services.FriendService;
import org.archvile.services.RateLimitService;
import org.archvile.services.TweetService;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.social.OperationNotPermittedException;

import org.springframework.social.twitter.api.ResourceFamily;
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
    private static DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");

    private static final String RATE_LIMIT_STATUS_ENDPOINT = "/application/rate_limit_status";

    private static final long DELAY_BETWEEN_RETWEETS_IN_SECONDS = 25;   // Delay between retweets
    private static final int REMAINING_HITS_THRESHOLD = 15;             // Number of remaining API hits before throttling
    private static final long THROTTLE_TIME_IN_SECONDS = 300;           // Throttle the application for this for this long when the hits threshold is reached

    @Autowired
    private TimelineOperations timelineOperations;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private RateLimitService rateLimitService;

    private Set<Tweet> contests = new HashSet<>();
    private List<Long> retweetIds = new ArrayList<>();

    @Override
    @Scheduled(initialDelay = 2000, fixedDelay = 600000)  //Every 10 minutes
    public void run() {

        setRetweetIds();
        Iterator<Tweet> iterator = getContests().iterator();

        int i = 0;
        while (iterator.hasNext()) {
            if (i % 10 ==0) {
                checkRateLimitStatus();
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

    private void checkRateLimitStatus() {
        int remainingHits = rateLimitService.getRemainingHitsForFamilyAndEndpoint(ResourceFamily.APPLICATION, RATE_LIMIT_STATUS_ENDPOINT);
        if (remainingHits <= REMAINING_HITS_THRESHOLD) {
            try {
                log.info("Throttling traffic until " + dtf.print(new DateTime().plus(THROTTLE_TIME_IN_SECONDS * 1000)));
                Thread.sleep(THROTTLE_TIME_IN_SECONDS * 1000);
            } catch (InterruptedException ex) {
                log.error("InterruptedException", ex);
            }
        } else {
            log.info("Endpoint: " + RATE_LIMIT_STATUS_ENDPOINT + ": " + remainingHits);
        }
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
            log.info("Retweeting user '" + contest.getFromUser() + "', tweet '" + contest.getText() + "'");
            tweetService.saveTweetAsContestTweet(contest);
            timelineOperations.retweet(contest.getId());
            if (contest.getText().toLowerCase().contains("follow")) {
                friendService.follow(contest.getFromUserId());
            }
            Thread.sleep(DELAY_BETWEEN_RETWEETS_IN_SECONDS * 1000);

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