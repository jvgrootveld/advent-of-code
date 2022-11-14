package day07

import shared.DebugLogger
import java.lang.UnsupportedOperationException

class Day07 {
    companion object {

        val log = DebugLogger(true)

        fun part1(input: List<String>): Map<String, Wire> {
            val circuit = Circuit()

            input.map {
                gateFactory(circuit, it)
            }.filterIsInstance<AssignNumberGate>()
                .forEach(Gate::invokeIfPreconditionsAreMet)

            return circuit.wires
        }

        fun part2(input: List<String>): Map<String, UShort> {
            TODO("IMPLEMENT")
        }

        class Circuit {
            val wires = mutableMapOf<String, Wire>()

            fun getOrCreateWire(wireId: String): Wire {
                return wires.getOrPut(wireId) { Wire(wireId) }
            }
        }

        class Wire(val id: String) {
            var signal: UShort = UShort.MIN_VALUE
                set(value) {
                    field = value
                    checkGates()
                }

            // Gates where this wire is a source
            val gates: MutableList<Gate> = mutableListOf()

            fun hasSignal(): Boolean {
                return signal > UShort.MIN_VALUE
            }

            private fun checkGates() {
                log.debugln("CheckGates for $id($signal)")
                gates.forEach {
                    log.debugln("|  Gate $it")
                }
                gates.forEach(Gate::invokeIfPreconditionsAreMet)
            }

            override fun toString(): String {
                return "$id($signal)"
            }
        }

        abstract class Gate(
            protected val target: Wire
        ) {
            private var isInvoked = false

            fun invokeIfPreconditionsAreMet() {
                if (!isInvoked && preconditionsMet()) {
                    isInvoked = true
                    invoke()
                }
            }

            // Check if all preconditions are met e.g. all sources have a signal
            abstract fun preconditionsMet(): Boolean
            abstract fun invoke()
        }

        class AssignNumberGate(
            private val signal: UShort, target: Wire
        ) :
            Gate(target) {

            override fun preconditionsMet(): Boolean = true

            override fun invoke() {
                target.signal = signal
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "$signal -> ${target.id}"
            }
        }

        class AssignWireGate(
            private val source: Wire, target: Wire
        ) :
            Gate(target) {

            init {
                source.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return source.hasSignal()
            }

            override fun invoke() {
                target.signal = source.signal
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "${source.id}(${source.signal}) -> ${target.id}"
            }
        }

        class NotGate(
            private val source: Wire, target: Wire
        ) :
            Gate(target) {

            init {
                source.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return source.hasSignal()
            }

            override fun invoke() {
                target.signal = source.signal xor UShort.MAX_VALUE
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "NOT ${source.id}(${source.signal}) -> ${target.id}"
            }
        }

        class AndGate(
            private val sourceLeft: Wire,
            private val sourceRight: Wire,
            target: Wire
        ) :
            Gate(target) {

            init {
                sourceLeft.gates.add(this)
                sourceRight.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return sourceLeft.hasSignal() && sourceRight.hasSignal()
            }

            override fun invoke() {
                target.signal = sourceLeft.signal and sourceRight.signal
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "${sourceLeft.id}(${sourceLeft.signal}) AND ${sourceRight.id}(${sourceRight.signal}) -> ${target.id}"
            }
        }

        class AndNumberGate(
            private val source: Wire,
            private val amount: UShort,
            target: Wire
        ) :
            Gate(target) {

            init {
                source.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return source.hasSignal()
            }

            override fun invoke() {
                target.signal = amount and source.signal
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "$amount AND ${source.id}(${source.signal}) -> ${target.id}"
            }
        }

        class OrGate(
            private val sourceLeft: Wire,
            private val sourceRight: Wire,
            target: Wire
        ) :
            Gate(target) {

            init {
                sourceLeft.gates.add(this)
                sourceRight.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return sourceLeft.hasSignal() && sourceRight.hasSignal()
            }

            override fun invoke() {
                target.signal = sourceLeft.signal or sourceRight.signal
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "${sourceLeft.id}(${sourceLeft.signal}) OR ${sourceRight.id}(${sourceRight.signal}) -> ${target.id}"
            }
        }

        class OrNumberGate(
            private val source: Wire,
            private val amount: UShort,
            target: Wire
        ) :
            Gate(target) {

            init {
                source.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return source.hasSignal()
            }

            override fun invoke() {
                target.signal = amount or source.signal
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "$amount OR ${source.id}(${source.signal}) -> ${target.id}"
            }
        }

        class ShiftLeftGate(
            private val source: Wire,
            private val amount: Int,
            target: Wire
        ) :
            Gate(target) {

            init {
                source.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return source.hasSignal()
            }

            override fun invoke() {
                target.signal = (source.signal.toUInt() shl amount).toUShort()
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "${source.id}(${source.signal}) LSHIFT $amount -> ${target.id}"
            }
        }

        class ShiftRightGate(
            private val source: Wire,
            private val amount: Int,
            target: Wire
        ) :
            Gate(target) {

            init {
                source.gates.add(this)
            }

            override fun preconditionsMet(): Boolean {
                return source.hasSignal()
            }

            override fun invoke() {
                target.signal = (source.signal.toUInt() shr amount).toUShort()
                log.debugln("Invoke: $this = ${target.signal}")
            }

            override fun toString(): String {
                return "${source.id}(${source.signal}) RSHIFT $amount -> ${target.id}"
            }
        }

        private fun isNumeric(toCheck: String): Boolean {
            return toCheck.toDoubleOrNull() != null
        }

        private fun gateFactory(
            circuit:
            Circuit, input: String
        ): Gate {
            log.debugln("Parse: $input")
            val (gateString, targetWireId) = input.split(" -> ")

            val targetWire = circuit.getOrCreateWire(targetWireId)

            return gateString.split(' ').let { parts ->

                // Assign like "123 -> x" or "aa -> b"
                if (parts.size == 1) {
                    return parts[0].let {
                        if (isNumeric(it)) {
                            AssignNumberGate(it.toUShort(), targetWire)
                        } else {
                            val sourceWire = circuit.getOrCreateWire(it)
                            AssignWireGate(sourceWire, targetWire)
                        }
                    }
                } else if (parts.size == 2) { // 'NOT' like "NOT x -> h"
                    val sourceWire = circuit.getOrCreateWire(parts[1])
                    return NotGate(sourceWire, targetWire)
                }

                // The rest with 3 parts like "x AND y -> d" or "x LSHIFT 2 -> f"
                val sourceLeft = parts[0]
                val operation = parts[1]
                val sourceRight = parts[2]

                when (operation) {
                    "AND" -> andGateFactory(circuit, sourceLeft, sourceRight, targetWire)
                    "OR" -> orGateFactory(circuit, sourceLeft, sourceRight, targetWire)
                    "LSHIFT" -> ShiftLeftGate(circuit.getOrCreateWire(sourceLeft), sourceRight.toInt(), targetWire)
                    "RSHIFT" -> ShiftRightGate(circuit.getOrCreateWire(sourceLeft), sourceRight.toInt(), targetWire)
                    else -> throw UnsupportedOperationException("Unknown action '$operation'")
                }
            }
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