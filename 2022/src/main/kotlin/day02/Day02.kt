package day02

class Day02 {

    companion object {

        private const val ELF_ROCK = "A";
        private const val ELF_PAPER = "B";
        private const val ELF_SCISSORS = "C";

        private const val ME_ROCK = "X";
        private const val ME_PAPER = "Y";
        private const val ME_SCISSORS = "Z";

        private const val ME_LOSE = "X";
        private const val ME_DRAW = "Y";
        private const val ME_WIN = "Z";

        private const val SCORE_ROCK = 1;
        private const val SCORE_PAPER = 2;
        private const val SCORE_SCISSORS = 3;

        private const val SCORE_WIN = 6;
        private const val SCORE_DRAW = 3;
        private const val SCORE_LOSE = 0;

        fun part1(input: List<String>): Int {
            var totalScore = 0

            for (line in input) {
                when (line) {
                    "$ELF_ROCK $ME_ROCK" -> totalScore += SCORE_ROCK + SCORE_DRAW
                    "$ELF_PAPER $ME_PAPER" -> totalScore += SCORE_PAPER + SCORE_DRAW
                    "$ELF_SCISSORS $ME_SCISSORS" -> totalScore += SCORE_SCISSORS + SCORE_DRAW
                    "$ELF_ROCK $ME_PAPER" -> totalScore += SCORE_PAPER + SCORE_WIN
                    "$ELF_PAPER $ME_SCISSORS" -> totalScore += SCORE_SCISSORS + SCORE_WIN
                    "$ELF_SCISSORS $ME_ROCK" -> totalScore += SCORE_ROCK + SCORE_WIN
                    "$ELF_ROCK $ME_SCISSORS" -> totalScore += SCORE_SCISSORS + SCORE_LOSE
                    "$ELF_PAPER $ME_ROCK" -> totalScore += SCORE_ROCK + SCORE_LOSE
                    "$ELF_SCISSORS $ME_PAPER" -> totalScore += SCORE_PAPER + SCORE_LOSE
                }
            }

            return totalScore
        }

        fun part2(input: List<String>): Int {

            // Shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
            // Outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you won)
            // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win

            var totalScore = 0

            for (line in input) {
                when (line) {
                    "$ELF_ROCK $ME_LOSE" -> totalScore += SCORE_SCISSORS + SCORE_LOSE // Rock must lose = Scissors
                    "$ELF_PAPER $ME_DRAW" -> totalScore += SCORE_PAPER + SCORE_DRAW // Paper must draw = Paper
                    "$ELF_SCISSORS $ME_WIN" -> totalScore += SCORE_ROCK + SCORE_WIN // Scissors must win = Rock
                    "$ELF_ROCK $ME_DRAW" -> totalScore += SCORE_ROCK + SCORE_DRAW // Rock must draw = Rock
                    "$ELF_PAPER $ME_WIN" -> totalScore += SCORE_SCISSORS + SCORE_WIN // Paper must win = Scissors
                    "$ELF_SCISSORS $ME_LOSE" -> totalScore += SCORE_PAPER + SCORE_LOSE // Scissors must lose = Paper
                    "$ELF_ROCK $ME_WIN" -> totalScore += SCORE_PAPER + SCORE_WIN // Rock must win = Paper
                    "$ELF_PAPER $ME_LOSE" -> totalScore += SCORE_ROCK + SCORE_LOSE // Paper must lose = Rock
                    "$ELF_SCISSORS $ME_DRAW" -> totalScore += SCORE_SCISSORS + SCORE_DRAW // Scissors must draw = Scissors
                }
            }

            return totalScore
        }
    }
}