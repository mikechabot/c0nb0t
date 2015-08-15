package org.archvile.services;

import org.archvile.beans.StaticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaticDataServiceImpl implements StaticDataService {

    @Autowired
    private StaticData staticData;

    @Override
    public List<String> getSearchTermsAndHashTags() {
        List<String> searchTermsAndHashtags = new ArrayList<>();
        searchTermsAndHashtags.addAll(getSearchTerms());
        searchTermsAndHashtags.addAll(getHashtags());
        return searchTermsAndHashtags;
    }

    @Override
    public List<String> getSearchTerms() {
        return staticData.getSearchTerms();
    }

    @Override
    public List<String> getHashtags() {
        return staticData.getHashTags();
    }

}
