package day03

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"strconv"
)

// Part01 Each bit in the gamma rate can be determined by finding the most common bit
// in the corresponding position of all numbers in the diagnostic report.
func Part01(input []string) int {
	length := len(input[0])

	// E.g: 2^size = 2^5 = 32 = 100000 in bit
	bitSize := shared.Pow(2, length)

	amountOfOnes := make([]int, length)
	for _, current := range input {
		// Note: Could loop over the chars but this was more fun
		number := bitStringToNumber(current)

		// Shift 1 bit from the highest to the lowest. For size 5 = 32
		// 16 = 10000
		// 8  = 01000
		// 4  = 00100
		// 2  = 00010
		// 1  = 00001
		bit := bitSize
		for i := 0; i < length; i++ {
			bit >>= 1

			// Is current bit location a 1
			// E.g: (10110 & 00100) == 00100 = true
			if number&bit == bit {
				amountOfOnes[i]++
			}
		}
	}

	// Count the majority ones as bits in its respectable location
	// majority = amount ones >= half (equal = 1 wins)
	half := len(input) / 2
	var gammaRate uint64

	bit := bitSize
	for _, ones := range amountOfOnes {
		bit >>= 1

		if ones >= half {
			gammaRate += bit
		}
	}

	// Inverse (bitwise NOT) of gammaRate
	// E.g:
	//  32-1 = 100000 - 1 = 11111
	//  11111
	//  10110
	//  ----- ^
	//  01001
	epsilonRate := bitSize - 1 ^ gammaRate

	return int(gammaRate * epsilonRate)
}

// Part02
// To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position,
// and keep only numbers with that bit in that position.
// If 0 and 1 are equally common, keep values with a 1 in the position being considered.
//
// To find CO2 scrubber rating, determine the least common value (0 or 1) in the current bit position,
// and keep only numbers with that bit in that position.
// If 0 and 1 are equally common, keep values with a 0 in the position being considered.
func Part02(input []string) int {
	// Duplicate code is extra documented in Part 1
	length := len(input[0])
	bitSize := shared.Pow(2, length)
	currentInput := bitStringsToNumbers(input)

	onesChecker := func(onesAmount, zerosAmount int) bool {
		return onesAmount >= zerosAmount
	}

	zerosChecker := func(onesAmount, zerosAmount int) bool {
		return onesAmount < zerosAmount
	}

	oxygenGeneratorRating := findPart02(currentInput, length, bitSize, onesChecker)
	co2ScrubberRating := findPart02(currentInput, length, bitSize, zerosChecker)

	return int(oxygenGeneratorRating * co2ScrubberRating)
}

type KeepOnesChecker = func(onesAmount, zerosAmount int) bool

func findPart02(input []uint64, length int, bitSize uint64, keepOnesChecker KeepOnesChecker) uint64 {
	bit := bitSize // E.g. 32
	currentInput := input

	for i := 0; i < length; i++ {
		var ones []uint64
		var zeros []uint64

		// Shift per iteration
		bit = bit >> 1 // E.g. 16

		// Count ones and zeroes
		for _, current := range currentInput {
			// Is current bit location a 1 add to ones, else zeroes
			if current & bit == bit {
				ones = append(ones, current)
			} else {
				zeros = append(zeros, current)
			}
		}

		if keepOnesChecker(len(ones), len(zeros)) {
			currentInput = ones
		} else {
			currentInput = zeros
		}

		// Break if we already have one value left
		if len(currentInput) == 1 {
			break
		}
	}

	return currentInput[0]
}

func bitStringsToNumbers(bitStrings []string) []uint64 {
	numbers := make([]uint64, len(bitStrings))

	for i, bitString := range bitStrings {
		numbers[i] = bitStringToNumber(bitString)
	}

	return numbers
}

func bitStringToNumber(bitString string) uint64 {
	number, err := strconv.ParseUint(bitString, 2, 64)
	if err != nil {
		panic(err)
	}
	return number
}
