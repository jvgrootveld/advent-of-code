package day05

import (
	"strconv"
	"strings"
)

// Part01 count overlapping points of lines
// Skip diagonal lines
func Part01(input []string) int {
	plain := NewPlain(1000, 1000)

	for _, line := range input {
		x1, y1, x2, y2 := parseAsPoints(line)

		// Only horizontal
		if x1 != x2 && y1 != y2 {
			continue
		}

		plain.AddLine(x1, y1, x2, y2)
	}

	//plain.DrawField()

	return plain.CountValues(2)
}

// Part02 like part but include diagonal lines
func Part02(input []string) int {
	plain := NewPlain(1000, 1000)

	for _, line := range input {
		x1, y1, x2, y2 := parseAsPoints(line)

		plain.AddLine(x1, y1, x2, y2)
	}

	//plain.DrawField()

	return plain.CountValues(2)
}

func parseAsPoints(line string) (x1, y1, x2, y2 int) {
	points := strings.Split(line, " -> ")
	from := strings.Split(points[0], ",")
	to := strings.Split(points[1], ",")

	x1, _ = strconv.Atoi(from[0])
	y1, _ = strconv.Atoi(from[1])
	x2, _ = strconv.Atoi(to[0])
	y2, _ = strconv.Atoi(to[1])
	return
}

