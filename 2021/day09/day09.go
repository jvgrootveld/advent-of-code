package day09

import "fmt"

// Create an empty 2-dimensional map of booleans
func createEmptyBoolMap(input []string) [][]bool {
	checkedMap := make([][]bool, len(input))
	for i, row := range input {
		checkedMap[i] = make([]bool, len(row))
	}
	return checkedMap
}

// Parse heatmap in a [y][x]int
func parseHeightmap(input []string) [][]int {
	var heightMap [][]int

	for _, row := range input {
		rowPoints := make([]int, len(row))

		for i, point := range row {
			rowPoints[i] = int(point - '0') // Convert rune/char to real int value
		}

		heightMap = append(heightMap, rowPoints)
	}

	return heightMap
}

// Print map and mark checked locations
func printHeightMap(heightMap [][]int, checkedMap [][]bool) {
	for y, row := range heightMap {
		for x, point := range row {
			if checkedMap[y][x] {
				fmt.Printf("[%d]", point)
			} else {
				fmt.Printf(" %d ", point)
			}
		}
		fmt.Println()
	}
}
