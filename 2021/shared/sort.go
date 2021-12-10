package shared

import "sort"

// SortDesc same as sort.Ints but in descending order
func SortDesc(values []int) {
	sort.Slice(values, func(i, j int) bool {
		return values[i] > values[j]
	})
}