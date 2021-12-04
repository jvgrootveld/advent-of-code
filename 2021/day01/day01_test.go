package day01

import (
	"fmt"
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"testing"
)

func TestPart01(t *testing.T) {
	content := shared.ReadFileAsInts("input.txt")

	result := Part01(content)
	fmt.Println("Result: ", result) // 1390
}

func TestPart02(t *testing.T) {
	content := shared.ReadFileAsInts("input.txt")

	result := Part02(content)
	fmt.Println("Result: ", result) // 1457
}