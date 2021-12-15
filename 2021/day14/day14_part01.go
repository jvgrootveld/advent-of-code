package day14

import (
	"fmt"
	"github.com/jvgrootveld/advent-of-code/avent2021/shared"
	"math"
	"strings"
)

type NodeChange struct {
	Node    *Node
	NewNode *Node
}

func Part01(input []string, steps int) int {
	defer shared.Elapsed("Day14 Part01")()

	nodeMap := createNodeMap(input[0])
	//printNodeMap(nodeMap)

	for step := 1; step <= steps; step++ {
		// Store all changes per step because all changes happen simultaneously
		var nodeChanges []NodeChange

		for i := 2; i < len(input); i++ {
			parts := strings.Split(input[i], " -> ")
			letters := parts[0]
			letterBetween := rune(parts[1][0])

			nodes, _ := nodeMap[rune(letters[0])]
			letterTo := rune(letters[1])

			currentNodes := nodes

			for _, node := range currentNodes {
				// If match, Add to changes
				if node.HasNextNode(letterTo) {
					nodeChanges = append(nodeChanges, NodeChange{
						Node:    node,
						NewNode: NewNode(letterBetween),
					})
				}
			}
		}

		for _, change := range nodeChanges {
			change.NewNode.Next = change.Node.Next
			change.Node.Next = change.NewNode
			addToNodeMap(nodeMap, change.NewNode)
		}
	}

	largest := 0
	smallest := math.MaxInt

	for _, nodes := range nodeMap {
		//fmt.Println("Key:", string(key), "Count:", len(nodes))
		if len(nodes) > largest {
			largest = len(nodes)
		} else if len(nodes) < smallest {
			smallest = len(nodes)
		}
	}

	return largest - smallest
}

// Create node with references and map them with its letter like
// NNCB
// ----
// N
// - N -> N
// - N -> C
// C
// - C -> B
// B
// - B ->
func createNodeMap(template string) (nodeMap map[rune][]*Node) {
	nodeMap = make(map[rune][]*Node)

	previousNode := NewNode(rune(template[0]))
	addToNodeMap(nodeMap, previousNode)

	for i := 1; i < len(template); i++ {
		newNode := NewNode(rune(template[i]))
		previousNode.Next = newNode
		addToNodeMap(nodeMap, newNode)
		previousNode = newNode
	}

	return
}

func addToNodeMap(nodeMap map[rune][]*Node, node *Node) {
	nodes, _ := nodeMap[node.Letter]
	nodeMap[node.Letter] = append(nodes, node)
}

func printNodeMap(nodeMap map[rune][]*Node) {
	for k, v := range nodeMap {
		fmt.Println(string(k))
		for i, n := range v {
			if n.Next != nil {
				fmt.Printf("- %d: %s -> %s\n", i, string(n.Letter), string(n.Next.Letter))
			} else {
				fmt.Printf("- %d: %s -> %s\n", i, string(n.Letter), "")
			}
		}
	}
}
