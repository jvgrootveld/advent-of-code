package day02

import (
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"testing"
)

type testResult struct {
	name     string
	expected int
}

func TestPart01(t *testing.T) {
	var tests = []testResult{
		{"example", 150},
		{"input", 1947824},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings(test.name + ".txt")
			result := Part01(content)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}

func TestPart02(t *testing.T) {
	var tests = []testResult{
		{"example", 900},
		{"input", 1813062561},
	}

	for _, test := range tests {
		test := test
		t.Run(test.name, func(t *testing.T) {
			t.Parallel()

			content := shared.ReadFileAsStrings(test.name + ".txt")
			result := Part02(content)

			t.Log("Result: ", result)
			if result != test.expected {
				t.Fatalf("Incorrect answer. Got '%v' expected '%v'", result, test.expected)
			}
		})
	}
}
