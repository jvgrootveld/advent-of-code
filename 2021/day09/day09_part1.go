package day09

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
)

// Part01 find and sum all lowest points in height map
func Part01(input []string) int {
	heightMap := parseHeightmap(input)
	checkedMap := createEmptyBoolMap(input)
	lowestMap := createEmptyBoolMap(input)

	lowestPointsScore := findLowestPoints(heightMap, checkedMap, lowestMap)
	//printHeightMap(heightMap, lowestMap)

	return shared.SumInts(lowestPointsScore)
}

// Find and mark the lowest points
// Return lowest points score for part 1
func findLowestPoints(heightMap [][]int, checkedMap, lowestMap [][]bool) (lowestPointsScore []int) {

	for y := 0; y < len(heightMap); y++ {
		for x := 0; x < len(heightMap[y]); x++ {
			score, lowestX, lowestY := walkAndMark(heightMap, checkedMap, x, y)
			lowestPointsScore = append(lowestPointsScore, score)
			if lowestX >= 0 {
				lowestMap[lowestY][lowestX] = true
			}
		}
	}

	return lowestPointsScore
}

// Walk from x,y to lower Point, marking each step and returning the lowest value score (value + 1)
// or 0 if dead end/already marked
func walkAndMark(heightMap [][]int, checkedMap [][]bool, startX, startY int) (lowestPoint, lowestX, lowestY int) {
	x := startX
	y := startY

	var current, leftX, rightX, upY, downY int

	// Note: Not the most efficient path but the first smallest found
	for {
		current = heightMap[y][x]
		if checkedMap[y][x] {
			return 0, -1, -1 // Already checked
		}
		checkedMap[y][x] = true

		// Check left
		leftX = x - 1
		if leftX >= 0 && heightMap[y][leftX] <= current {
			x = leftX
			continue // Will be marked in next iteration
		}

		// Check right
		rightX = x + 1
		if rightX < len(heightMap[y]) && heightMap[y][rightX] <= current {
			x = rightX
			continue // Will be marked in next iteration
		}

		// Check up
		upY = y - 1
		if upY >= 0 && heightMap[upY][x] <= current {
			y = upY
			continue // Will be marked in next iteration
		}

		// Check down
		downY = y + 1
		if downY < len(heightMap) && heightMap[downY][x] <= current {
			y = downY
			continue // Will be marked in next iteration
		}

		// Non found, return score
		return current + 1, x, y
	}
}
