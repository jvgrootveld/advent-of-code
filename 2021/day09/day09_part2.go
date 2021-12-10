package day09

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
)

type Point struct {
	x, y int
}

// Part02 find and multiply the three largest basins sizes
func Part02(input []string) int {
	heightMap := parseHeightmap(input)
	checkedMap := createEmptyBoolMap(input)
	lowestMap := createEmptyBoolMap(input)

	findLowestPoints(heightMap, checkedMap, lowestMap)

	basinsMap := createEmptyBoolMap(input)
	var basinSizes []int

	//printHeightMap(heightMap, lowestMap)

	for y := 0; y < len(lowestMap); y++ {
		for x, isLowest := range lowestMap[y] {
			if isLowest {
				basinSize := checkBasinSize(heightMap, basinsMap, x, y)
				basinSizes = append(basinSizes, basinSize)
			}
		}
	}
	//fmt.Println()
	printHeightMap(heightMap, basinsMap)
	shared.SortDesc(basinSizes)

	return basinSizes[0] * basinSizes[1] * basinSizes[2]
}

func checkBasinSize(heightMap [][]int, basinsMap [][]bool, startX, startY int) (basinSize int) {
	x := startX
	y := startY

	basinsMap[y][x] = true
	basinSize++

	newPointsToCheck := checkAround(heightMap, basinsMap, x, y)
	basinSize += len(newPointsToCheck)

	for currentPoints := newPointsToCheck; len(currentPoints) > 0; currentPoints = newPointsToCheck {
		newPointsToCheck = []Point{} // Clear new map

		for _, point := range currentPoints {
			newPointsToCheck = append(newPointsToCheck, checkAround(heightMap, basinsMap, point.x, point.y)...)
		}

		basinSize += len(newPointsToCheck)
	}
	return basinSize
}

func checkAround(heightMap [][]int, basinsMap [][]bool, x int, y int) (newPointsToCheck []Point) {
	// Check left
	currentX := x - 1
	// If within bounds, not a 9 and not already checked
	if currentX >= 0 && heightMap[y][currentX] != 9 && !basinsMap[y][currentX] {
		basinsMap[y][currentX] = true
		newPointsToCheck = append(newPointsToCheck, Point{currentX, y})
	}

	// Check right
	currentX = x + 1
	// If within bounds, not a 9 and not already checked
	if currentX < len(heightMap[y]) && heightMap[y][currentX] != 9 && !basinsMap[y][currentX] {
		basinsMap[y][currentX] = true
		newPointsToCheck = append(newPointsToCheck, Point{currentX, y})
	}

	// Check up
	currentY := y - 1
	// If within bounds, not a 9 and not already checked
	if currentY >= 0 && heightMap[currentY][x] != 9 && !basinsMap[currentY][x] {
		basinsMap[currentY][x] = true
		newPointsToCheck = append(newPointsToCheck, Point{x, currentY})
	}

	// Check down
	currentY = y + 1
	// If within bounds, not a 9 and not already checked
	if currentY < len(heightMap) && heightMap[currentY][x] != 9 && !basinsMap[currentY][x] {
		basinsMap[currentY][x] = true
		newPointsToCheck = append(newPointsToCheck, Point{x, currentY})
	}

	return newPointsToCheck
}
