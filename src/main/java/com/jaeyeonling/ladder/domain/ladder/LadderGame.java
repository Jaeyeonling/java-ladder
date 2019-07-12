package com.jaeyeonling.ladder.domain.ladder;

import com.jaeyeonling.ladder.domain.Index;
import com.jaeyeonling.ladder.domain.line.DirectionGenerateStrategy;
import com.jaeyeonling.ladder.domain.line.Line;
import com.jaeyeonling.ladder.domain.line.Lines;
import com.jaeyeonling.ladder.domain.line.RandomDirectionGenerateStrategy;
import com.jaeyeonling.ladder.domain.reword.LadderReword;
import com.jaeyeonling.ladder.domain.reword.LadderRewords;
import com.jaeyeonling.ladder.domain.user.Username;
import com.jaeyeonling.ladder.domain.user.Users;

import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class LadderGame {

    private final GameInfo gameInfo;
    private final Lines lines;

    private LadderGame(final GameInfo gameInfo,
                       final Lines lines) {
        this.gameInfo = gameInfo;
        this.lines = lines;
    }

    public static Builder builder(final Users users,
                                  final LadderRewords ladderRewords) {
        return new Builder(users, ladderRewords);
    }

    public LadderGameResult play() {
        final Map<Username, LadderReword> ladderGameResult = gameInfo.range()
                .boxed()
                .map(Index::valueOf)
                .collect(toMap(gameInfo::findUsernameBy, this::rideAndFindLadderRewordBy));

        return LadderGameResult.of(ladderGameResult);
    }

    public Users getUsers() {
        return gameInfo.getUsers();
    }

    public LadderRewords getLadderRewords() {
        return gameInfo.getLadderRewords();
    }

    public Lines getLines() {
        return lines;
    }

    private LadderReword rideAndFindLadderRewordBy(final Index index) {
        return gameInfo.findLadderRewordBy(lines.ride(index));
    }

    public static class Builder {
        private static final DirectionGenerateStrategy DEFAULT_DIRECTION_GENERATE_STRATEGY =
                new RandomDirectionGenerateStrategy();
        private static final HeightOfLadder DEFAULT_HEIGHT_OF_LADDER = HeightOfLadder.valueOf(10);

        private final Users users;
        private final LadderRewords ladderRewords;

        private DirectionGenerateStrategy directionGenerateStrategy;
        private HeightOfLadder heightOfLadder;

        private Builder(final Users users,
                        final LadderRewords ladderRewords) {
            this.users = users;
            this.ladderRewords = ladderRewords;
        }

        public LadderGame build() {
            return new LadderGame(generateGameInfo(), generateLines());
        }

        public Builder directionGenerateStrategy(final DirectionGenerateStrategy directionGenerateStrategy) {
            this.directionGenerateStrategy = directionGenerateStrategy;
            return this;
        }

        public Builder heightOfLadder(final HeightOfLadder heightOfLadder) {
            this.heightOfLadder = heightOfLadder;
            return this;
        }

        private GameInfo generateGameInfo() {
            return GameInfo.with(users, ladderRewords);
        }

        private Lines generateLines() {
            return getHeightOfLadder().rangeClosed()
                    .mapToObj(ignore -> generateLine())
                    .collect(collectingAndThen(toList(), Lines::of));
        }

        private Line generateLine() {
            return Line.generate(getDirectionGenerateStrategy(), users);
        }

        private HeightOfLadder getHeightOfLadder() {
            return Optional.ofNullable(heightOfLadder)
                    .orElse(DEFAULT_HEIGHT_OF_LADDER);
        }

        private DirectionGenerateStrategy getDirectionGenerateStrategy() {
            return Optional.ofNullable(directionGenerateStrategy)
                    .orElse(DEFAULT_DIRECTION_GENERATE_STRATEGY);
        }
    }
}
