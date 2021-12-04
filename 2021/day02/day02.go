package day02

import (
	"strconv"
	"strings"
)

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
