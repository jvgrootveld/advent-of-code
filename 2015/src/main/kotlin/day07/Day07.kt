package day07

import shared.DebugLogger
import java.lang.UnsupportedOperationException

class Day07 {
    companion object {

        val log = DebugLogger(false)

        fun part1(input: List<String>): Map<String, Wire> {
            val circuit = Circuit()

            input.forEach {
                gateFactory(circuit, it)
            }

            return circuit.wires
        }

        fun part2(input: List<String>): Map<String, Wire> {
            val part1Results = part1(input)
            val circuit = Circuit()

            input.forEach {
                gateFactory(circuit, it)
            }

            circuit.wires["b"]?.apply {
                setSignal(part1Results["a"]!!.getSignal())
            }

            return circuit.wires
        }

        class Circuit {
            val wires = mutableMapOf<String, Wire>()

            fun getOrCreateWire(wireId: String): Wire {
                return wires.getOrPut(wireId) { Wire(wireId) }
            }
        }

        class Wire(val id: String, var inputGate: Gate? = null) {

            private var signal: UShort? = null

            fun setSignal(value: UShort) {
                signal = value
            }

            fun getSignal(): UShort {
                log.debugln("getSignal for $id")

                if (inputGate == null) {
                    throw Exception("Could not calculate signal for wire '$id'. No inputGate")
                }

                if (signal == null) {
                    signal = inputGate?.calculate()
                }
                log.debugln("getSignal result $id = $signal")
                return signal ?: throw Exception("Could not calculate signal for wire '$id'. Signal result null")
            }

            override fun toString(): String {
                return "$id($signal)"
            }
        }

        abstract class Gate(
            protected val target: Wire
        ) {
            abstract fun calculate(): UShort
        }

        class AssignNumberGate(
            private val signal: UShort, target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                log.debugln("calculate: $this = $signal")
                return signal
            }

            override fun toString(): String {
                return "$signal -> ${target.id}"
            }
        }

        class AssignWireGate(
            private val source: Wire, target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return source.getSignal()
            }

            override fun toString(): String {
                return "${source.id} -> ${target.id}"
            }
        }

        class NotGate(
            private val source: Wire, target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return source.getSignal() xor UShort.MAX_VALUE
            }

            override fun toString(): String {
                return "NOT ${source.id} -> ${target.id}"
            }
        }

        class AndGate(
            private val sourceLeft: Wire,
            private val sourceRight: Wire,
            target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return sourceLeft.getSignal() and sourceRight.getSignal()
            }

            override fun toString(): String {
                return "${sourceLeft.id} AND ${sourceRight.id} -> ${target.id}"
            }
        }

        class AndNumberGate(
            private val source: Wire,
            private val amount: UShort,
            target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return amount and source.getSignal()
            }

            override fun toString(): String {
                return "$amount AND ${source.id} -> ${target.id}"
            }
        }

        class OrGate(
            private val sourceLeft: Wire,
            private val sourceRight: Wire,
            target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return sourceLeft.getSignal() or sourceRight.getSignal()
            }

            override fun toString(): String {
                return "${sourceLeft.id} OR ${sourceRight.id} -> ${target.id}"
            }
        }

        class OrNumberGate(
            private val source: Wire,
            private val amount: UShort,
            target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return amount or source.getSignal()
            }

            override fun toString(): String {
                return "$amount OR ${source.id} -> ${target.id}"
            }
        }

        class ShiftLeftGate(
            private val source: Wire,
            private val amount: Int,
            target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return (source.getSignal().toUInt() shl amount).toUShort()
            }

            override fun toString(): String {
                return "${source.id} LSHIFT $amount -> ${target.id}"
            }
        }

        class ShiftRightGate(
            private val source: Wire,
            private val amount: Int,
            target: Wire
        ) :
            Gate(target) {

            override fun calculate(): UShort {
                return (source.getSignal().toUInt() shr amount).toUShort()
            }

            override fun toString(): String {
                return "${source.id} RSHIFT $amount -> ${target.id}"
            }
        }

        private fun isNumeric(toCheck: String): Boolean {
            return toCheck.toDoubleOrNull() != null
        }

        private fun gateFactory(
            circuit: Circuit,
            input: String
        ) {
            val (gateString, targetWireId) = input.split(" -> ")

            val targetWire = circuit.getOrCreateWire(targetWireId)

            val parts = gateString.split(' ')
            var gate: Gate? = null

            // Assign like "123 -> x" or "aa -> b"
            if (parts.size == 1) {
                parts[0].also {
                    gate = if (isNumeric(it)) {
                        AssignNumberGate(it.toUShort(), targetWire)
                    } else {
                        val sourceWire = circuit.getOrCreateWire(it)
                        AssignWireGate(sourceWire, targetWire)
                    }
                }
            } else if (parts.size == 2) { // 'NOT' like "NOT x -> h"
                val sourceWire = circuit.getOrCreateWire(parts[1])
                gate = NotGate(sourceWire, targetWire)
            } else {
                // The rest with 3 parts like "x AND y -> d" or "x LSHIFT 2 -> f"
                val sourceLeft = parts[0]
                val operation = parts[1]
                val sourceRight = parts[2]

                gate = when (operation) {
                    "AND" -> andGateFactory(circuit, sourceLeft, sourceRight, targetWire)
                    "OR" -> orGateFactory(circuit, sourceLeft, sourceRight, targetWire)
                    "LSHIFT" -> ShiftLeftGate(circuit.getOrCreateWire(sourceLeft), sourceRight.toInt(), targetWire)
                    "RSHIFT" -> ShiftRightGate(circuit.getOrCreateWire(sourceLeft), sourceRight.toInt(), targetWire)
                    else -> throw UnsupportedOperationException("Unknown action '$operation'")
                }
            }

            targetWire.inputGate = gate
        }

        private fun andGateFactory(
            circuit:
            Circuit, sourceLeft: String, sourceRight: String, targetWire: Wire
        ): Gate {

            if (isNumeric(sourceLeft)) {
                return AndNumberGate(circuit.getOrCreateWire(sourceRight), sourceLeft.toUShort(), targetWire)
            }

            val sourceLeftWire = circuit.getOrCreateWire(sourceLeft)

            if (isNumeric(sourceRight)) {
                return AndNumberGate(sourceLeftWire, sourceRight.toUShort(), targetWire)
            }

            return AndGate(sourceLeftWire, circuit.getOrCreateWire(sourceRight), targetWire)
        }

        private fun orGateFactory(
            circuit:
            Circuit, sourceLeft: String, sourceRight: String, targetWire: Wire
        ): Gate {

            if (isNumeric(sourceLeft)) {
                return OrNumberGate(circuit.getOrCreateWire(sourceRight), sourceLeft.toUShort(), targetWire)
            }

            val sourceLeftWire = circuit.getOrCreateWire(sourceLeft)

            if (isNumeric(sourceRight)) {
                return OrNumberGate(sourceLeftWire, sourceRight.toUShort(), targetWire)
            }

            return OrGate(sourceLeftWire, circuit.getOrCreateWire(sourceRight), targetWire)
        }
    }
}