package day11

import (
	"container/list"
	"fmt"
)

func Part01(input []string) int {

	steps := 100
	octopusField := createOctopusField(input)
	flashCount := 0

	for step := 1; step <= steps; step++ {
		flashCount += handleStep(octopusField)
	}

	//printField(octopusField)

	return flashCount
}

func Part02(input []string) int {

	octopusField := createOctopusField(input)

	step := 0
	flashCount := 0

	for {
		step++
		flashCount = handleStep(octopusField)

		if flashCount == 100 {
			break
		}
	}

	return step
}

func handleStep(octopusField [][]int) (flashCount int) {
	increase(octopusField)
	flashMap := createEmptyBoolMap(len(octopusField))

	newOctopusToCheck := list.New()

	// Check initial flashes after increase
	for y, row := range octopusField {
		for x, point := range row {
			if point > 9 {
				flashCount++
				flashMap[y][x] = true
				octopusField[y][x] = 0
				distributeFlashEnergy(octopusField, flashMap, x, y, newOctopusToCheck)
			}
		}
	}

	// Handle every octopus that gained energy
	for e := newOctopusToCheck.Front(); e != nil; e = e.Next() {
		if point, ok := e.Value.(*Point); ok {
			energyLevel := octopusField[point.Y][point.X]
			if energyLevel > 9 {
				flashCount++
				flashMap[point.Y][point.X] = true
				octopusField[point.Y][point.X] = 0
				distributeFlashEnergy(octopusField, flashMap, point.X, point.Y, newOctopusToCheck)
			}
		}
	}

	return
}

type Point struct {
	X int
	Y int
}

var directionChanges = []Point{
	{0, -1},  // North
	{1, -1},  // North East
	{1, 0},   // East
	{1, 1},   // South East
	{0, 1},   // South
	{-1, 1},  // South West
	{-1, 0},  // West
	{-1, -1}, // North West
}

func distributeFlashEnergy(octopusField [][]int, flashMap [][]bool, midX int, midY int, newPointsToCheck *list.List) {

	for _, direction := range directionChanges {
		x := midX + direction.X
		y := midY + direction.Y

		// Check out of bounds
		if x < 0 || x >= len(octopusField) || y < 0 || y >= len(octopusField) {
			continue
		}

		// If already flashed, skip
		if flashMap[y][x] {
			continue
		}

		newPointsToCheck.PushBack(&Point{x, y})
		octopusField[y][x]++
	}
}

// Increase by 1
func increase(field [][]int) {
	for y, row := range field {
		for x, _ := range row {
			field[y][x]++
		}
	}
}

func createOctopusField(input []string) [][]int {
	field := make([][]int, len(input))

	for y, row := range input {
		field[y] = make([]int, len(row))
		for x, value := range row {
			field[y][x] = int(value - '0') // Convert rune/char to real int value
		}
	}

	return field
}

func printField(field [][]int) {
	fmt.Println("----------")
	for _, row := range field {
		for _, value := range row {
			fmt.Print(value)
		}
		fmt.Println()
	}
}

// Create an empty 2-dimensional map of booleans
func createEmptyBoolMap(size int) [][]bool {
	checkedMap := make([][]bool, size)
	for i := 0; i < size; i++ {
		checkedMap[i] = make([]bool, size)
	}
	return checkedMap
}
