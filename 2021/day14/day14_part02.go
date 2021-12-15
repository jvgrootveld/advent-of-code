package day14

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"math"
	"strings"
)

type Change struct {
	Find   string
	Insert string
}

func Part02(input []string, steps int) int {
	defer shared.Elapsed("Day14 Part02")()

	// Create count of combo of letters
	template := input[0]
	comboCount := make(map[string]int)
	for i := 1; i < len(template); i++ {
		comboCount[template[i-1:i+1]]++
	}

	// Parse changes in easy accessible list
	var changes []Change
	for i := 2; i < len(input); i++ {
		parts := strings.Split(input[i], " -> ")
		changes = append(changes, Change{
			Find:   parts[0],
			Insert: parts[1],
		})
	}

	// Handle steps
	for step := 1; step <= steps; step++ {
		var currentStepChanges []Change

		// Store all changes per step because all changes happen simultaneously
		for _, change := range changes {
			if comboCount[change.Find] > 0 {
				currentStepChanges = append(currentStepChanges, change)
			}
		}

		// Copy for changes current step changes
		newComboCount := copyMap(comboCount)
		for _, stepChange := range currentStepChanges {
			currentAmount := comboCount[stepChange.Find]

			// New letter combo's
			first := stepChange.Find[0:1] + stepChange.Insert
			second := stepChange.Insert + stepChange.Find[1:2]

			// Add amount of current to new letter combo's
			newComboCount[first] += currentAmount
			newComboCount[second] += currentAmount

			// Subtract amount, because a letter is put in between
			// Can't reset to 0 because of new letter combo's in this step which are equal
			newComboCount[stepChange.Find] -= currentAmount
		}

		comboCount = newComboCount
	}

	// Count per letter
	countPerLetter := make(map[byte]int)

	for k, v := range comboCount {
		countPerLetter[k[0]] += v
		countPerLetter[k[1]] += v
	}

	largest := 0
	smallest := math.MaxInt

	for _, amount := range countPerLetter {
		// Normalize
		normalized := (amount / 2) + (amount % 2)
		//fmt.Println("Key:", string(key), "Count:", normalized)
		if normalized > largest {
			largest = normalized
		}
		if normalized < smallest {
			smallest = normalized
		}
	}

	return largest - smallest
}

func copyMap(mapToCopy map[string]int) (result map[string]int) {
	result = make(map[string]int)

	for k, v := range mapToCopy {
		result[k] = v
	}

	return
}
