package day01

import (
	"fmt"
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"testing"
)

func TestPart01(t *testing.T) {
	content := shared.ReadFileAsInts("input.txt")

	result := Part01(content)
	answer := 1390

	fmt.Println("Result: ", result)
	if result != answer {
		t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, answer)
	}
}

func TestPart02(t *testing.T) {
	content := shared.ReadFileAsInts("input.txt")

	result := Part02(content)
	answer := 1457

	fmt.Println("Result: ", result)
	if result != answer {
		t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, answer)
	}
}