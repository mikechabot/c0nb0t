package org.archvile.services;

import java.util.List;

public interface StaticDataService {

    List<String> getSearchTermsAndHashTags();
    List<String> getSearchTerms();
    List<String> getHashtags();

}
