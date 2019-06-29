package com.jaeyeonling.ladder.domain.line;

import com.jaeyeonling.ladder.domain.user.CountOfUsers;

import java.util.ArrayList;
import java.util.List;

public abstract class RoleBaseLineGenerator implements LineGenerator {

    private static final int BEFORE_TERM = 1;

    abstract Boolean generate(final Boolean before);

    @Override
    public Line generate(final CountOfUsers countOfUsers) {
        final List<Boolean> points = generatePoints(countOfUsers);

        return Line.ofPoints(points);
    }

    private List<Boolean> generatePoints(final CountOfUsers countOfUsers) {
        final List<Boolean> points = initializePoints();
        for (int i = points.size(); i < countOfUsers.getValue(); i++) {
            final Boolean before = points.get(points.size() - BEFORE_TERM);

            final Boolean point = generate(before);

            points.add(point);
        }

        return points;
    }

    private List<Boolean> initializePoints() {
        final List<Boolean> points = new ArrayList<>();

        points.add(false);

        return points;
    }
}
