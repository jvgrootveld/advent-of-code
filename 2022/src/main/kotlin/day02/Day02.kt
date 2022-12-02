package day02

class Day02 {

    companion object {

        private const val SCORE_ROCK = 1;
        private const val SCORE_PAPER = 2;
        private const val SCORE_SCISSORS = 3;

        private const val SCORE_WIN = 6;
        private const val SCORE_DRAW = 3;
        private const val SCORE_LOSE = 0;

        fun part1(input: List<String>): Int {
            var totalScore = 0

            // Shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
            // Outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)

            for(line in input) {
                when(line) {
                    "A X" -> totalScore += SCORE_ROCK + SCORE_DRAW // Rock Rock
                    "B Y" -> totalScore += SCORE_PAPER + SCORE_DRAW // Paper Paper
                    "C Z" -> totalScore += SCORE_SCISSORS + SCORE_DRAW // Scissors Scissors
                    "A Y" -> totalScore += SCORE_PAPER + SCORE_WIN // Rock Paper
                    "B Z" -> totalScore += SCORE_SCISSORS + SCORE_WIN // Paper Scissors
                    "C X" -> totalScore += SCORE_ROCK + SCORE_WIN // Scissors Rock
                    "A Z" -> totalScore += SCORE_SCISSORS + SCORE_LOSE // Rock Scissors
                    "B X" -> totalScore += SCORE_ROCK + SCORE_LOSE // Paper Rock
                    "C Y" -> totalScore += SCORE_PAPER + SCORE_LOSE // Scissors Paper
                }
            }

            return totalScore
        }

        fun part2(input: List<String>): Int {

            // Shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
            // Outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)
            // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win

            var totalScore = 0

            for(line in input) {
                when(line) {
                    "A X" -> totalScore += SCORE_SCISSORS + SCORE_LOSE // Rock must lose = Scissors
                    "B Y" -> totalScore += SCORE_PAPER + SCORE_DRAW // Paper must draw = Paper
                    "C Z" -> totalScore += SCORE_ROCK + SCORE_WIN // Scissors must win = Rock
                    "A Y" -> totalScore += SCORE_ROCK + SCORE_DRAW // Rock must draw = Rock
                    "B Z" -> totalScore += SCORE_SCISSORS + SCORE_WIN // Paper must win = Scissors
                    "C X" -> totalScore += SCORE_PAPER + SCORE_LOSE // Scissors must lose = Paper
                    "A Z" -> totalScore += SCORE_PAPER + SCORE_WIN // Rock must win = Paper
                    "B X" -> totalScore += SCORE_ROCK + SCORE_LOSE // Paper must lose = Rock
                    "C Y" -> totalScore += SCORE_SCISSORS + SCORE_DRAW // Scissors must draw = Scissors
                }
            }

            return totalScore
        }
    }
}