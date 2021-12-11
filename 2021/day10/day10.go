package day10

import (
	"fmt"
	"sort"
)

func Part01(input []string) int {
	_, corrupted := parseSyntax(input)

	score := 0
	for _, sign := range corrupted {
		score += sign.PointsPart1
	}

	return score
}

func Part02(input []string) int {
	incomplete, _ := parseSyntax(input)

	var scores []int

	for _, stack := range incomplete {
		score := 0
		for {
			sign, err := stack.Pop()
			if err == ErrIsEmpty {
				break
			} else if err != nil {
				panic(err)
			}

			score *= 5
			score += sign.PointsPart2
		}

		scores = append(scores, score)
	}

	sort.Ints(scores)

	return scores[len(scores) / 2]
}

func parseSyntax(input []string) (incomplete []*SignStack, corruptedSigns []Sign) {
	for _, row := range input {
		stack := NewSignStack()
		for _, char := range row {
			sign, ok := signMap[char]
			if !ok {
				panic(fmt.Errorf("unsupported sign: %s", string(char)))
			}

			if char == sign.Open {
				stack.Push(sign)
				continue
			}

			// Is closing type, check corruption or incomplete
			lastSign, err := stack.Last()
			if err != nil {
				panic(err) // e.g. Empty stack?
			}

			// If not the same, the line is corrupt
			if lastSign != sign {
				corruptedSigns = append(corruptedSigns, sign)
				stack.Clear()
				break
			}

			// If the same, cancel each other out and remove from stack
			stack.Pop()
		}

		// Incomplete
		if stack.Size() > 0 {
			incomplete = append(incomplete, stack)
		}
	}

	return
}
