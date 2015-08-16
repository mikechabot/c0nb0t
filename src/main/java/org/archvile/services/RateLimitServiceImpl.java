package org.archvile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RateLimitServiceImpl implements RateLimitService {

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

}
