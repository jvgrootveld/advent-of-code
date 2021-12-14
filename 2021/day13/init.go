package day13

import (
	. "github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"strings"
)

func parseInput(input []string) (paper [][]int, folds []Fold) {

	var points []Point
	var highestX int
	var highestY int

	for _, in := range input {
		if in == "" {
			continue
		}
		//fold along x=655
		if strings.HasPrefix(in, "fold along x=") {
			folds = append(folds, Fold{
				At:   StrToInt(strings.Split(in, "=")[1]),
				Type: FoldX,
			})
			continue
		} else if strings.HasPrefix(in, "fold along y=") {
			folds = append(folds, Fold{
				At:   StrToInt(strings.Split(in, "=")[1]),
				Type: FoldY,
			})
			continue
		}

		values := strings.Split(in, ",")
		point := NewPoint(StrToInt(values[0]), StrToInt(values[1]))

		points = append(points, point)

		if point.X > highestX {
			highestX = point.X
		}
		if point.Y > highestY {
			highestY = point.Y
		}
	}

	paper = createPaper(highestY, highestX)
	markPoints(paper, points)

	return
}

func markPoints(paper [][]int, points []Point) {
	for _, p := range points {
		paper[p.Y][p.X]++
	}
}