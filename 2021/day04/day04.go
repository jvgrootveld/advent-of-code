package day04

import (
	"regexp"
	"strconv"
	"strings"
)

var spaces = regexp.MustCompile("\\s+")

// Part01 Check which bingo board is the first winner
func Part01(input []string) int {
	draws := strings.Split(input[0], ",")
	boards := createBingoBoards(input)

	leaderBoard, lastDraw := play(boards, draws, 1)

	// Calculate score
	drawNumber, err := strconv.Atoi(lastDraw)
	if err != nil {
		panic(err)
	}

	return drawNumber * leaderBoard[0].SumOfUnmarkedValues()
}

// Part02 Check which bingo board is the last winner
func Part02(input []string) int {
	draws := strings.Split(input[0], ",")
	boards := createBingoBoards(input)

	leaderBoard, lastDraw := play(boards, draws, len(boards))

	// Calculate score
	drawNumber, err := strconv.Atoi(lastDraw)
	if err != nil {
		panic(err)
	}

	return drawNumber * leaderBoard[len(leaderBoard)-1].SumOfUnmarkedValues()
}

func createBingoBoards(input []string) []BingoBoard {
	var boards []BingoBoard
	var fieldRows [][]string

	// Skip first two rows which contains the draws and an empty line
	for i := 2; i < len(input); i++ {
		row := input[i]
		// On empty row create boards and reset rows
		if row == "" {
			boards = append(boards, NewBoard(fieldRows))
			fieldRows = [][]string{}
			continue
		}

		fieldRows = append(fieldRows, spaces.Split(strings.TrimPrefix(row, " "), -1))
	}

	// Add last and return
	return append(boards, NewBoard(fieldRows))

	//for i := len(input) - 1; i > 0; i-- {
	//	row := input[i]
	//	// On empty row create boards and reset rows
	//	if row == "" {
	//		boards = append(boards, NewBoard(fieldRows))
	//		fieldRows = [][]string{}
	//		continue
	//	}
	//
	//	fieldRows = append(fieldRows, spaces.Split(strings.TrimPrefix(row, " "), -1))
	//}

	//return boards
}

// play
// Loop over draws and check each bingoBoard for success
func play(boards []BingoBoard, draws []string, podiumSize int) ([]BingoBoard, string) {
	var leaderboard []BingoBoard
	var lastDraw string

	for i := 0; i < len(draws); i++ {
		lastDraw = draws[i]
		var lastUnfinishedBoard BingoBoard

		for _, currentBoard := range boards {
			if currentBoard.HasWon() { // Could optimise to pop from a board stack
				continue
			}
			lastUnfinishedBoard = currentBoard

			currentBoard.Mark(lastDraw)

			if currentBoard.CheckWinCondition() {
				leaderboard = append(leaderboard, currentBoard)
			}
		}

		// If wanted podium size is met, don't play further
		if len(leaderboard) >= podiumSize {
			break
		}

		// If last remaining add as last
		// One draw is made for last round (don't know why)
		if len(leaderboard) == len(boards) -1 {
			leaderboard = append(leaderboard, lastUnfinishedBoard)
		}
	}

	return leaderboard, lastDraw
}
