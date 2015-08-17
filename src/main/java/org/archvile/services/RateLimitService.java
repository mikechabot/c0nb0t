package org.archvile.services;

import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;

import java.util.List;
import java.util.Map;

public interface RateLimitService {

    Map<ResourceFamily, List<RateLimitStatus>> getRateLimitStatus();
    List<RateLimitStatus> getRateLimitStatusByResourceFamily(ResourceFamily family);
    int getRemainingHitsForFamilyAndEndpoint(ResourceFamily family, String endpoint);
    boolean isThrottleable();
    void throttle();

}
