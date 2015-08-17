package org.archvile.tasks;

import org.archvile.services.RateLimitService;
import org.archvile.util.DateUtil;

import org.springframework.scheduling.annotation.Async;
import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

@Component
public class RateLimitTask {

    private Logger log = Logger.getLogger(RateLimitTask.class);

    @Autowired
    private RateLimitService rateLimitService;

    private Map<    ResourceFamily, List<RateLimitStatus>> previousStatusByFamily = new HashMap<>();

    @Async
    @Scheduled(initialDelay = 2000, fixedRate = 30000)
    public void run() {

//        log.info("Running RateLimitTask, next run time: " + DateUtil.nowPlus(30000));
//
//        Map<ResourceFamily, List<RateLimitStatus>> statusesByFamily = rateLimitService.getRateLimitStatus();
//
//        log.info("family\tendpoint\tquarterOfHourLimit\tprevious hits\tremaining hits\thit difference");
//        for (Map.Entry<ResourceFamily, List<RateLimitStatus>> entry : statusesByFamily.entrySet()) {
//            ResourceFamily family = entry.getKey();
//            List<RateLimitStatus> statuses = entry.getValue();
//            for (RateLimitStatus status : statuses) {
//                for (RateLimitStatus previousStatus : getPreviousStatuses(family, statuses)) {
//                    if (status.getEndpoint().equals(previousStatus.getEndpoint())) {
//                        log.info(family.name().toUpperCase() + "\t" + status.getEndpoint() + "\t" + status.getQuarterOfHourLimit() + "\t" + previousStatus.getRemainingHits() + "\t" + status.getRemainingHits() + "\t" + (status.getRemainingHits() - previousStatus.getRemainingHits()));
//                        previousStatusByFamily.put(family, statuses);
//                    }
//                }
//            }
//        }

    }

    private List<RateLimitStatus> getPreviousStatuses(ResourceFamily family, List<RateLimitStatus> currentStatuses) {
        List<RateLimitStatus> previousStatuses = previousStatusByFamily.get(family);
        if (previousStatuses != null) {
            return previousStatuses;
        }
        previousStatuses = currentStatuses;
        previousStatusByFamily.put(family, previousStatuses);
        return previousStatuses;
    }

}
