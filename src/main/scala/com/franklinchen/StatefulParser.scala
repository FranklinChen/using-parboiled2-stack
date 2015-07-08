package com.franklinchen

import org.parboiled2._
import shapeless._

import scala.collection.mutable.ArrayBuffer

class StatefulParser(val input: ParserInput) extends Parser {
  /** Whether only upper. */
  type State = Boolean

  /** Stuff to actually collect. */
  type Info = String

  /** Top level.

    Start by pushing relevant state.
    Throw away the final state.
    */
  def Start: Rule1[Seq[Info]] = rule {
    push(false) ~
    Document ~> {
      (_: State, buf: ArrayBuffer[Info]) =>
      buf
    } ~ EOI
  }

  def Document: Rule[State :: HNil,
                     State :: ArrayBuffer[Info] :: HNil] = {
    rule {
      push(ArrayBuffer[Info]()) ~
        (
          Line ~ '\n'
        ).*
    }
  }

  /** Reduction rule. */
  def Line: Rule[State :: ArrayBuffer[Info] :: HNil,
                 State :: ArrayBuffer[Info] :: HNil] = rule {
    run {
      (upperOnly: State, buf: ArrayBuffer[Info]) =>
      Directive ~> {
        newUpperOnly: State =>
        push(newUpperOnly) ~
        push(buf) // put back
      } |
      Word ~> {
        info: Info =>
        push(upperOnly) ~ // put back
        push(buf += info)
      }
    }
  }

  /** Change state during parse. */
  def Directive: Rule1[State] = rule {
    "@UPPER_ONLY" ~ push(true) |
    "@lower_only" ~ push(false)
  }

  /**
    Definition of word depends on whether we are in upper-mode.

    Do not change the mode.
    */
  def Word: Rule[State :: HNil, Info :: HNil] = rule {
    run {
      upperOnly: State =>
      if (upperOnly) {
        capture("UPPER")
      } else {
        capture("lower")
      }
    }
  }
}

object StatefulParser extends App {
  // TODO Fix org.parboiled2.ValueStackUnderflowException
  val emptyInput = ""
  println(new StatefulParser(emptyInput).Start.run())

  val goodInput = """@UPPER_ONLY
UPPER
UPPER
@lower_only
lower
lower
"""
  println(new StatefulParser(goodInput).Start.run())

  val badInput = """@UPPER_ONLY
UPPER
lower
"""
  println(new StatefulParser(badInput).Start.run())
}
