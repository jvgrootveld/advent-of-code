package day05

import (
	"fmt"
	"strings"
)

type Plain interface {
	// DrawField draws field to console
	DrawField()

	// AddLine add file to field
	AddLine(x1, y1, x2, y2 int)

	// CountValues counts values equal or higher than given
	CountValues(valueOrHigher int) int
}

type plain struct {
	field [][]int
}

func NewPlain(width, height int) Plain {
	field := make([][]int, height)
	for row := 0; row < height; row++ {
		field[row] = make([]int, width)
	}

	return &plain{
		field: field,
	}
}

func (p *plain) DrawField() {
	// Draw header
	fmt.Print("   ")
	for i, _ := range p.field[0] {
		fmt.Printf(" %d ", i)
	}
	fmt.Println()

	line := strings.Repeat("---", len(p.field[0]))
	fmt.Println("  ", line)

	// Draw field
	for y, row := range p.field {
		fmt.Print(y, " |")
		for _, point := range row {
			if point == 0 {
				fmt.Printf(" . ")
			} else {
				fmt.Printf(" %d ", point)
			}
		}
		fmt.Println()
	}
	fmt.Println("  ", line)
}

func (p *plain) AddLine(x1, y1, x2, y2 int) {
	incrementX := 1
	incrementY := 1
	if x1 > x2 {
		incrementX = -1
	}

	if y1 > y2 {
		incrementY = -1
	}

	for x, y := x1, y1; x != x2 || y != y2; {
		p.field[x][y]++

		if x != x2 {
			x += incrementX
		}

		if y != y2 {
			y += incrementY
		}
	}

	// Add last
	p.field[x2][y2]++
}

func (p *plain) CountValues(valueOrHigher int) int {
	var count int
	for _, row := range p.field {
		for _, point := range row {
			if point >= valueOrHigher {
				count++
			}
		}
	}

	return count
}

func GetOrdered(a, b int) (higher, lower int) {
	if a < b {
		return b, a
	}

	return a, b
}
