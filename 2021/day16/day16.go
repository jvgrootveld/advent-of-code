package day16

import (
	"fmt"
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"strconv"
)

func Part01(input []string) int {
	defer shared.Elapsed("day16 Part02")()

	a := "D2FE28"
	// convert hex to int and print outputs
	if i, err := strconv.ParseInt(a, 16, 0); err != nil {
		fmt.Println(err)
	} else {
		// bin
		fmt.Print("Binary = ")
		fmt.Printf("%b\n", i)
	}

	return 0
}

func Part02(input []string) int {
	defer shared.Elapsed("day16 Part02")()

	fmt.Println("HO HO HO!")

	return 0
}

