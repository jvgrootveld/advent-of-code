package day13

import (
	"bytes"
	"fmt"
)

func createPaper(height, width int) [][]int {
	paper := make([][]int, height+1)

	for i := 0; i <= height; i++ {
		paper[i] = make([]int, width+1)
	}
	return paper
}

func countPoints(paper [][]int) (total int) {
	for _, row := range paper {
		for _, value := range row {
			if value > 0 {
				total++
			}
		}
	}
	return
}

func printPaper(sheet [][]int) string {
	paper := bytes.NewBufferString("")

	for _, row := range sheet {
		for _, value := range row {
			if value > 0 {
				fmt.Fprint(paper," #")
			} else {
				fmt.Fprint(paper," .")
			}
		}
		fmt.Fprintln(paper)
	}

	return paper.String()
}