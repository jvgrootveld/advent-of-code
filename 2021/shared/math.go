package shared

import "math"

func SumInts(values []int) int {
	total := 0
	for _, value := range values {
		total += value
	}
	return total
}

// Pow quick way to power the wanted uint64 type
func Pow(x, pow int) uint64 {
	return uint64(math.Pow(float64(x), float64(pow)))
}

// PowInt quick way to power the wanted int type
func PowInt(x, pow int) int {
	return int(math.Pow(float64(x), float64(pow)))
}

// IntAbs Absolute for integers
func IntAbs(value int) int {
	if value < 0 {
		return -value
	}
	return value
}
