package wrong

import (
	"fmt"
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"math"
)

var NorthEast = shared.NewPoint(1, -1)

// Part01 Thought I didn't need the good old Dijkstra's
// I was wrong. This works for the example but not for the input.
func Part01(input [][]int) int {
	//print(input)
	// For easy ref, assuming equal widths and heights
	height := len(input)
	width := len(input[0])

	// Per diagonal line, start at maxX - 1, maxY
	currentY := height - 1 // Last
	currentX := width - 2  // On before last

	// Till we are at the 0,0 point
	for !(currentY == 0 && currentX == 0) {

		// Save starting point
		startedAtY := currentY
		startedAtX := currentX

		// Go diagonally
		for currentY >= 0 && currentX < width {

			// Get smallest of sides
			smallest := math.MaxInt
			for _, direction := range directionChecks {
				x := currentX + direction.X
				y := currentY + direction.Y

				// Check out of bounds
				if x < width && y < height {

					if input[y][x] < smallest {
						smallest = input[y][x]
					}
				}
			}

			// Update current to smallest + itself
			input[currentY][currentX] += smallest

			// Go diagonally up
			currentY += NorthEast.Y
			currentX += NorthEast.X
		}

		// Get starting point for next diagonal line
		if startedAtX > 0 {
			currentY = startedAtY
			currentX = startedAtX - 1
		} else {
			currentY = startedAtY - 1
			currentX = 0
		}
	}

	print(input)

	// Get smallest route
	smallest := input[1][0]
	if input[0][1] < smallest {
		smallest = input[0][1]
	}
	return smallest
}

func print(input [][]int) {
	fmt.Println("----------")
	for _, row := range input {
		fmt.Println(row)
	}
}

var directionChecks = []shared.Point{
	{0, 1}, // South
	{1, 0}, // East
}
