package day08

import shared.DebugLogger

class Day08 {
    companion object {

        val log = DebugLogger(false)

        fun part1(input: ByteArray): Day08Result {

            val stateMachine = StateMachine()

            input.forEach(stateMachine::addChar)

            stateMachine.newLine() // Add last result to the results
            return Day08Result(stateMachine.lineResults)
        }

        fun part2(input: List<String>): Int {
            TODO("IMPLEMENT")
        }

        class Day08Result(val lineResults: List<LineResult>) {
            fun countTotal() = lineResults.sumOf {
                it.codeCount - it.memoryCount
            }
        }


        enum class CharBytes(private val char: Char) {
            NEW_LINE('\n'),
            ZERO('0'),
            NINE('9'),
            SLASH('\\'),
            QUOTE('"'),
            X('x');

            val code: Int
                get() = char.code

            val byte: Byte
                get() = code.toByte()
        }

        data class LineResult(var codeCount: Int = 0, var memoryCount: Int = 0) {
            fun clear() {
                codeCount = 0
                memoryCount = 0
            }
        }

        interface CharState {
            fun addChar(stateMachine: StateMachine, charByte: Byte)
        }

        class StateMachine {
            private var currentState: CharState = NormalState()

            val lineResults = mutableListOf<LineResult>()
            var currentLineResult = LineResult(0, 0)

            fun addChar(charByte: Byte) {
                currentLineResult.codeCount++ // Always increase code count
                log.debugln("\naddChar: '${charByte.toInt().toChar()}' codeCount: ${currentLineResult.codeCount}")
                currentState.addChar(this, charByte)
            }

            fun addMemoryCount() {
                currentLineResult.memoryCount++
                log.debugln("addMemoryCount: ${currentLineResult.memoryCount}")
            }

            fun newLine() {
                currentLineResult.memoryCount -= 2 // Remove start and end quotes
                log.debugln("newLine code '${currentLineResult.codeCount}' memory '${currentLineResult.memoryCount}'")
                log.debugln("=".repeat(20))
                lineResults.add(currentLineResult)

                // Reset line and state
                currentLineResult = LineResult(0, 0)
                setNewState(NormalState())
            }

            fun setNewState(newState: CharState) {
                log.debugln("setNewState: $currentState > $newState")
                currentState = newState
            }
        }

        // ""          =  2 code, 0 memory
        // "abc"       =  5 code, 3 memory
        // "aaa\"aaa"  = 10 code, 7 memory
        // "\x27"      =  6 code, 1 memory
        class NormalState() : CharState {
            override fun addChar(stateMachine: StateMachine, charByte: Byte) {
                log.debugln("--- NormalState")

                when(charByte) {
                    CharBytes.SLASH.byte -> stateMachine.setNewState(SlashState())
                    CharBytes.NEW_LINE.byte -> {
                        stateMachine.currentLineResult.codeCount -= 1 // Remove new line
                        stateMachine.newLine()
                    }
                    else -> stateMachine.addMemoryCount()
                }
            }
        }

        class SlashState() : CharState {
            override fun addChar(stateMachine: StateMachine, charByte: Byte) {
                log.debugln("--- SlashState")
                when(charByte) {
                    CharBytes.X.byte -> stateMachine.setNewState(HexState())
                    else -> { // Like '\\' or '\"'
                        stateMachine.addMemoryCount()
                        stateMachine.setNewState(NormalState())
                    }
                }
            }
        }

        class HexState() : CharState {
            var hexadecimalCount = 0;

            override fun addChar(stateMachine: StateMachine, charByte: Byte) {
                log.debugln("--- HexState")

                hexadecimalCount++

                // Like '\x27' or '\x3d'
                if (hexadecimalCount == 2) {
                    stateMachine.addMemoryCount()
                    stateMachine.setNewState(NormalState())
                }
            }
        }

    }
}