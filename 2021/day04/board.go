package day04

import (
	"fmt"
	"strconv"
)

type BingoBoard interface {

	// PrintMarkedValues prints values pretty with marks
	PrintMarkedValues()

	// ResetMarks removes all marks based on field values amounts
	ResetMarks()

	// Mark the value in the field
	Mark(value string)

	// CheckWinCondition checks if the win condition is met
	CheckWinCondition() bool

	// SumOfUnmarkedValues Sums all unmarked values as numbers
	SumOfUnmarkedValues() int

	// HasWon returns true if board met win condition
	HasWon() bool
}

type bingoBoard struct {
	// Field of values by X and Y
	fieldValues [][]string
	fieldMarks  [][]int // 1 = marked
	won         bool
}


func (b *bingoBoard) PrintMarkedValues() {
	for x, row := range b.fieldValues {
		fmt.Print("[")
		for y, cell := range row {
			if b.fieldMarks[x][y] == 1 {
				fmt.Printf(" %5s ", fmt.Sprint("(", cell, ")"))
			} else {
				fmt.Printf("  %3s  ", cell)
			}

		}
		fmt.Println("]")
	}
}

func NewBoard(field [][]string) BingoBoard {
	newBoad := &bingoBoard{
		fieldValues: field,
	}

	newBoad.ResetMarks()

	return newBoad
}

// ResetMarks removes all marks based on field values amounts
func (b *bingoBoard) ResetMarks() {
	b.fieldMarks = make([][]int, len(b.fieldValues))

	for i, row := range b.fieldValues {
		b.fieldMarks[i] = make([]int, len(row))
	}
}

// Mark the value in the field
func (b *bingoBoard) Mark(value string) {
	for x, row := range b.fieldValues {
		for y, cell := range row {
			if cell == value {
				b.fieldMarks[x][y] = 1
			}
		}
	}
}

// CheckWinCondition checks if the win condition is met
func (b *bingoBoard) CheckWinCondition() bool {
	// Check condition with a variadic size of rows and columns
	columnLength := len(b.fieldMarks)
	columnsTotal := make([]int, columnLength)

	for _, row := range b.fieldMarks {
		var currentRowTotal int
		for y, cell := range row {
			columnsTotal[y] += cell
			currentRowTotal += cell
		}

		// Check if column has all ones, if zo direct return
		if currentRowTotal == len(row) {
			b.won = true
			return true
		}
	}

	// Check columns
	for _, columnTotal := range columnsTotal {
		if columnTotal == columnLength {
			b.won = true
			return true
		}
	}

	return false
}

// SumOfUnmarkedValues Sums all unmarked values as numbers
func (b *bingoBoard) SumOfUnmarkedValues() int {
	var total int

	for x, row := range b.fieldMarks {
		for y, cell := range row {
			if cell == 0 {
				number, err := strconv.Atoi(b.fieldValues[x][y])
				if err != nil {
					panic(err)
				}
				total += number
			}
		}
	}

	return total
}

func (b *bingoBoard) HasWon() bool {
	return b.won
}
