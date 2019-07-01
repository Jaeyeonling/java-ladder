package com.jaeyeonling.ladder.domain.ladder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LadderResults {

    static final String SEPARATOR = "\\s*,\\s*";

    private final List<String> results;

    private LadderResults(final List<String> results) {
        this.results = results;
    }

    public static LadderResults ofSeparator(final String rawResults) {
        return Arrays.stream(rawResults.split(SEPARATOR))
                .collect(Collectors.collectingAndThen(Collectors.toList(), LadderResults::new));
    }

    public String findByIndex(final int index) {
        return results.get(index);
    }

    public List<String> getResults() {
        return Collections.unmodifiableList(results);
    }

    public int size() {
        return results.size();
    }
}
