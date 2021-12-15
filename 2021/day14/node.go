package day14

type Node struct {
	Letter rune
	Next   *Node
}

func NewNode(letter rune) *Node {
	return &Node{
		Letter: letter,
	}
}

func (n *Node) HasNextNode(letter rune) bool {
	return n.Next != nil && n.Next.Letter == letter
}
