package day06

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"testing"
)

type testResult struct {
	name     string
	days     int
	expected int
}

func TestPart01(t *testing.T) {
	var tests = []testResult{
		{"example", 18, 26},
		{"example", 80, 5934},
		{"input", 80, 376194},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsCommaInts(test.name + ".txt")
			result := Part01(content, test.days)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}

func TestPart02(t *testing.T) {
	var tests = []testResult{
		{"example", 256, 26984457539},
		{"input", 256, -1},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsCommaInts(test.name + ".txt")
			result := Part02(content, test.days)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}
