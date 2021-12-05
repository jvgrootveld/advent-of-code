package day02

import (
	"strconv"
	"strings"
)

// Part01
// forward X increases the horizontal position by X units.
// down X increases the depth by X units.
// up X decreases the depth by X units.
func Part01(input []string) int {
	distance := 0
	depth := 0

	for _, current := range input {
		parts := strings.Split(current, " ")
		amount, _ := strconv.Atoi(parts[1])

		// Check action
		switch parts[0] {
		case "forward":
			distance += amount
		case "up":
			depth -= amount
		case "down":
			depth += amount
		}
	}

	return distance * depth
}

// Part02
// down X increases your aim by X units.
// up X decreases your aim by X units.
// forward X does two things:
// - It increases your horizontal position by X units.
// - It increases your depth by your aim multiplied by X.
func Part02(input []string) int {
	distance := 0
	depth := 0
	aim := 0

	for _, current := range input {
		parts := strings.Split(current, " ")
		amount, _ := strconv.Atoi(parts[1])

		// Check action
		switch parts[0] {
		case "forward":
			distance += amount
			depth += amount * aim
		case "up":
			aim -= amount
		case "down":
			aim += amount
		}
	}

	return distance * depth
}
