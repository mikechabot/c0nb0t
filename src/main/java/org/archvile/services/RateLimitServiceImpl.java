package org.archvile.services;

import org.archvile.util.DateUtil;

import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private static Logger log = Logger.getLogger(RateLimitServiceImpl.class);

    private static final int REMAINING_HITS_THRESHOLD = 20;     // Number of remaining API hits before throttling
    private static final long THROTTLE_TIME_IN_SECONDS = 600;   // Throttle the application for this for this long when the hits threshold is reached
    private static final long THROTTLE_TIME_IN_MILLISECONDS = THROTTLE_TIME_IN_SECONDS * 1000;

    private static final String RATE_LIMIT_STATUS_ENDPOINT = "/application/rate_limit_status";

    @Autowired
    private UserOperations userOperations;

    @Override
    public Map<ResourceFamily, List<RateLimitStatus>> getRateLimitStatus() {
        return userOperations.getRateLimitStatus(ResourceFamily.values());
    }

    @Override
    public List<RateLimitStatus> getRateLimitStatusByResourceFamily(ResourceFamily family) {
        if (family == null) throw new IllegalArgumentException("ResourceFamily cannot be null");
        return userOperations.getRateLimitStatus(family).get(family);
    }

    @Override
    public int getRemainingHitsForFamilyAndEndpoint(ResourceFamily family, String endpoint) {
        for (RateLimitStatus status : getRateLimitStatusByResourceFamily(family)) {
            if (status.getEndpoint().equals(endpoint)) {
                return status.getRemainingHits();
            }
        }
        return -1;
    }

    @Override
    public void throttle() {
        try {
            log.info("Throttling traffic until " + DateUtil.nowPlus(THROTTLE_TIME_IN_MILLISECONDS));
            Thread.sleep(THROTTLE_TIME_IN_MILLISECONDS);
        } catch (InterruptedException ex) {
            log.error("InterruptedException", ex);
        }
    }

    @Override
    public boolean isThrottleable() {
        return getRemainingHitsForFamilyAndEndpoint(ResourceFamily.APPLICATION, RATE_LIMIT_STATUS_ENDPOINT)
                <= REMAINING_HITS_THRESHOLD;
    }

}
