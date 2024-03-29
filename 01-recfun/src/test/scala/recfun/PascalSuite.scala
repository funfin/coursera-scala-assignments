package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PascalSuite extends FunSuite {
  import Main.pascal
  test("pascal: col=0,row=2") {
    assert(pascal(0,2) === 1)
  }

  test("pascal: col=1,row=2") {
    assert(pascal(1,2) === 2)
  }

  test("pascal: col=1,row=3") {
    assert(pascal(1,3) === 3)
  }

  test("pascal: col=2,row=3") {
    assert(pascal(2,3) === 3)
  }

  test("pascal: col=3,row=3") {
    assert(pascal(3,3) === 1)
  }

  test("pascal: col=1,row=4") {
    assert(pascal(1,4) === 4)
  }

  test("pascal: col=2,row=4") {
    assert(pascal(2,4) === 6)
  }

  test("others"){
    assert(pascal(0,0) === 1)
    assert(pascal(0,1) === 1)
    assert(pascal(1, 1) === 1)
    assert(pascal(2,5)===10)
    assert(pascal(3,5)===10)
    assert(pascal(2,6)===15)
    assert(pascal(4,6)===15)
    assert(pascal(3,6)===20)

  }

  test("no such element pascal(1,0)" ) {
    intercept[NoSuchElementException]{
      pascal(1,0)
    }
  }

  test("no such element pascal(2,1)" ) {
    intercept[NoSuchElementException]{
      pascal(2,1)
    }
  }

  test("no such element pascal(-1,1)" ) {
    intercept[NoSuchElementException]{
      pascal(-1,1)
    }
  }


}
