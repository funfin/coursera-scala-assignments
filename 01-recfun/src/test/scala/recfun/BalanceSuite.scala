package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  test("balance: '(()' is unbalanced"){
    assert(!balance("(()".toList))
  }

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }

  test("balance: '(a(b?c) d (/ 1 e))' is balanced") {
    assert(balance("(a(b?c) d (/ 1 e))".toList))
  }

  test("balance: '((())())' is balanced"){
    assert(balance("((())())".toList))
  }

  test("long test without parentheses"){
    assert(balance("Kra kre mija lis ma nore ide nos tre.".toList));
  }

  test("empty is balanced")  {
    assert(balance("".toList))
  }

  test("empty is with whitespace balanced")  {
    assert(balance(" ".toList))
  }

  test(")") {
    assert(balance(")".toList)===false)
  }

  test("(") {
    assert(balance("(".toList)===false)
  }
  test("()") {
    assert(balance("()".toList))
  }
  test(")(") {
    assert(balance(")(".toList)===false)
  }
  test("))") {
    assert(balance("))".toList)===false)
  }
  test("((") {
    assert(balance("((".toList)===false)
  }
  test(")()") {
    assert(balance(")()".toList)===false)
  }
  test("())") {
    assert(balance("())".toList)===false)
  }

  test("())(") {
    assert(balance("())(".toList)===false)
  }
  test("()()") {
    assert(balance("()()".toList))
  }

  test("(())") {
    assert(balance("(())".toList))
  }


}
