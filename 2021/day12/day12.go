package day12

import (
	"strings"
)

func Part01(input []string) int {
	_, start, end := createCaveMap(input)

	currentPath := NewCavePath(start, false)
	paths := walkThePath(*currentPath, start, end)

	return len(paths)
}

func Part02(input []string) int {
	_, start, end := createCaveMap(input)

	currentPath := NewCavePath(start, true)
	paths := walkThePath(*currentPath, start, end)

	return len(paths)
}

func walkThePath(cavePath CavePath, currentCave *Cave, endCave *Cave) (pathsFound []CavePath) {

	for _, cave := range currentCave.ConnectedCaves {
		currentCavePath := cavePath // Copy to isolate new path

		// Check if small cave is already visited
		if cave.Type == Small && currentCavePath.ContainsCave(cave) {
			if !currentCavePath.HasTimeToVisitSmallCave || cave.IsStartOrEnd() {
				continue
			}
			currentCavePath.HasTimeToVisitSmallCave = false
		}

		// Add to current path
		currentCavePath.AddCave(cave)

		// At end
		if cave == endCave {
			pathsFound = append(pathsFound, currentCavePath)
			continue
		}

		pathsFound = append(pathsFound, walkThePath(currentCavePath, cave, endCave)...)
	}

	return
}

func createCaveMap(input []string) (caveMap map[string]*Cave, start, end *Cave) {
	caveMap = make(map[string]*Cave)
	start = getOrCreateCave(caveMap, "start")
	end = getOrCreateCave(caveMap, "end")

	// Create map
	for _, connection := range input {
		caves := strings.Split(connection, "-")

		from := getOrCreateCave(caveMap, caves[0])
		to := getOrCreateCave(caveMap, caves[1])
		from.addConnection(to)
	}

	return
}

func getOrCreateCave(caveMap map[string]*Cave, name string) *Cave {
	if cave, ok := caveMap[name]; ok {
		return cave
	}

	newCave := NewCave(name)
	caveMap[name] = newCave
	return newCave
}
