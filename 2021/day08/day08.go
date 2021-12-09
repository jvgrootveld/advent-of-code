 package day08

import (
	"fmt"
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"sort"
	"strings"
)

const (
	LetterSize1 = 2
	LetterSize4 = 4
	LetterSize7 = 3
	LetterSize8 = 7
)

// Part01 Count letters for 1, 3, 4 and 7 in the result part
func Part01(input []string) int {
	count := 0
	for _, value := range input {
		parts := strings.Split(value, " | ")

		for _, digit := range strings.Split(parts[1], " ") {
			l := len(digit)
			if l == 2 || l == 3 || l == 4 || l == 7 {
				count++
			} else {
			}
		}
	}

	return count
}

// Part02 determine number of letters in left part and sum right part
func Part02(input []string) int {
	total := 0
	for _, value := range input {
		parts := strings.Split(value, " | ")

		digitCodex := make([]string, 10) // The index is the number, value the letter group
		digitGroups := strings.Split(parts[0], " ")
		sortEachGroup(digitGroups) // Letters within group are also random

		// Set known letters 1, 3, 4 and 7
		setKnownLettersToCodex(digitGroups, digitCodex)

		// Of the group of 5 letters we can determine
		// - The letter 3 by checking which group had the same letters as '1'
		// Of the leftovers of the group with 5 letters we can determine 2 and 5 by checking matches with '4'
		// - 2 = 2 matches
		// - 5 = 3 matches
		fiveLetterGroup := extractGroup(digitGroups, 5)

		for _, group := range fiveLetterGroup {
			overlapCount := countOverlap(group, digitCodex[1])

			if overlapCount == LetterSize1 { // All matches with 1 = 3
				digitCodex[3] = group
			} else {
				overlapCount = countOverlap(group, digitCodex[4])

				if overlapCount == 2 { // 2 matches with 4 = 2
					digitCodex[2] = group
				} else { // 3 matches with 4 = 5
					digitCodex[5] = group
				}
			}
		}

		// Of the group of 6 letters we can determine
		// - The letter 6 by checking which group has only 2 matches with '7'
		// Of the leftovers of the group with 6 letters we can determine 0 and 9 by checking matches with '4'
		// - 0 = 3 matches
		// - 9 = 4 matches
		sixLetterGroup := extractGroup(digitGroups, 6)

		for _, group := range sixLetterGroup {
			overlapCount := countOverlap(group, digitCodex[7])

			if overlapCount == 2 { // 2 matches with 7 = 6
				digitCodex[6] = group
			} else {
				overlapCount = countOverlap(group, digitCodex[4])

				if overlapCount == 3 { // 3 matches with 7 = 0
					digitCodex[0] = group
				} else { // 4 matches with 7 = 9
					digitCodex[9] = group
				}
			}
		}

		// Count result part
		digitResultGroup := strings.Split(parts[1], " ")
		sortEachGroup(digitResultGroup) // Letters within group are also random

		subTotal := 0
		for number, group := range digitCodex {
			for i, resultGroup := range digitResultGroup {
				if group == resultGroup {
					// Each digit in its own place
					subTotal += number * shared.PowInt(10, len(digitResultGroup) - 1 - i)
				}
			}
		}

		total += subTotal
	}

	return total
}

// sortEachGroup sort letters in each group
func sortEachGroup(groups []string) {
	for i := 0; i < len(groups); i++ {
		groups[i] = sortString(groups[i])
	}
}

// sortString sort letters in string
func sortString(value string) string {
	letters := strings.Split(value, "")
	sort.Strings(letters)
	return strings.Join(letters, "")
}

// setKnownLettersToCodex Sets known letters of  1, 3, 4 and 7 in codes at place
func setKnownLettersToCodex(digitGroups []string, codex []string) {
	for _, group := range digitGroups {
		length := len(group)
		switch length {
			case LetterSize1: codex[1] = group
			case LetterSize7: codex[7] = group
 			case LetterSize4: codex[4] = group
			case LetterSize8: codex[8] = group
		}
	}
}

func printCodex(codex []string) {
	for number, group := range codex {
		fmt.Printf(" [%d] %s\n", number, group)
	}
}

func extractGroup(digitGroups []string, wantedGroupSize int) []string {
	var group []string

	for _, currentGroup := range digitGroups {
		if len(currentGroup) == wantedGroupSize {
			group = append(group, currentGroup)
		}
	}

	return group
}

func countOverlap(value, overlap string) int {
	count := 0
	overlapLength := len(overlap)

	for _, v := range value {
		for _, o := range overlap {
			if v == o {
				count++
			}
			// Little efficiency if we already matched the whole overlap
			if count == overlapLength {
				return count
			}
		}
	}

	return count
}