package day13

type FoldType int

const (
	FoldX FoldType = iota
	FoldY
)

type Fold struct {
	At   int
	Type FoldType
}

func Part01(input []string) int {
	paper, folds := parseInput(input)
	//fmt.Println(printPaper(paper))

	var folded [][]int
	for _, fold := range folds {
		if fold.Type == FoldY {
			folded = foldY(paper, fold.At)
		} else {
			folded = foldX(paper, fold.At)
		}
		break // Just one fold
	}

	//fmt.Println(printPaper(paper))

	return countPoints(folded)
}

func Part02(input []string) string {
	paper, folds := parseInput(input)
	//fmt.Println(printPaper(paper))

	folded := paper
	for _, fold := range folds {
		if fold.Type == FoldY {
			folded = foldY(folded, fold.At)
		} else {
			folded = foldX(folded, fold.At)
		}
	}

	return printPaper(folded)
}

func foldY(paper [][]int, foldAtY int) (folded [][]int) {
	firstSize := foldAtY                     // 0-based zo size is equal to fold
	secondSize := len(paper) - firstSize - 1 // Minus the fold line

	offset := firstSize - secondSize

	folded = make([][]int, firstSize)
	for y := 0; y < firstSize; y++ {
		folded[y] = paper[y]
	}

	for y := 0; y < firstSize; y++ {
		for x, _ := range folded[y] {
			if y+offset < len(folded) {
				folded[y+offset][x] += paper[len(paper)-y-1][x]
			}
		}
	}

	return
}

func foldX(paper [][]int, foldAtX int) (folded [][]int) {
	firstSize := foldAtX                        // 0-based zo size is equal to fold
	secondSize := len(paper[0]) - firstSize - 1 // Minus the fold line
	offset := firstSize - secondSize

	folded = make([][]int, len(paper))

	for y := 0; y < len(paper); y++ {
		folded[y] = paper[y][:firstSize]

		for x := 0; x < firstSize; x++ {
			folded[y][x] = paper[y][x]
			folded[y][x+offset] += paper[y][len(paper[0])-x-1]
		}
	}

	return
}
