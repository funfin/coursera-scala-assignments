package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val even = ((x: Int) => x % 2 == 0)
    val odd = ((x: Int) => !(even(x)))

    val mod3 = ((x: Int) => x%3==0)
    val mod4 = ((x: Int) => x%4==0)

    val zeroToTen = ((x: Int) => x>=0 && x<=10 )

  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("singletonSet(2) not contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(!contains(s2, 1))
    }
  }

  test("contains even numbers and not odd"){
    new TestSets {
      assert(contains(even, 2))
      assert(contains(even, 10000))
      assert(!contains(even, 1))
      assert(!contains(even, 9999))

      assert(contains(odd, 1))
      assert(contains(odd, 9999))
      assert(!contains(odd, 2))
      assert(!contains(odd, 10000))
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("union of odd and even contains all elements") {
    new TestSets {
      val all = union(even, odd)
      assert(contains(all, 1), "Union 1")
      assert(contains(all, 2), "Union 2")
      assert(contains(all, 9999), "Union 3")
      assert(contains(all, 10000), "Union 4")
    }
  }


  test("intersect contains all elements that are in both sets") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "intersect 1")
      assert(!contains(s, 2), "intersect 2")
      assert(!contains(s, 3), "intersect 3")

      val mod3mod4 = intersect(mod3, mod4)

      assert(contains(mod3, 3), "intersect 4")
      assert(!contains(mod3, 4), "intersect 5")
      assert(contains(mod4, 4), "intersect 6")
      assert(!contains(mod4, 9), "intersect 7")
      assert(contains(mod3mod4, 12), "intersect 8")
      assert(contains(mod3mod4, 24), "intersect 9")
      assert(!contains(mod3mod4, 9), "intersect 10")
      assert(!contains(mod3mod4, 8), "intersect 11")
    }
  }

  test("diff test"){
    new TestSets {
      val s = diff(odd, mod3)

      assert(contains(s, 1), "diff 1")
      assert(contains(s, 5), "diff 2")
      assert(contains(s, 7), "diff 3")
      assert(contains(s, 11), "diff 4")
      assert(!contains(s, 3), "diff 5")
      assert(!contains(s, 9), "diff 6")
      assert(!contains(s, 15), "diff 7")
    }
  }

  test("filter test"){
    new TestSets {
      val s = filter(zeroToTen, p=> p>5)
      assert(contains(zeroToTen, 0), "filter 1")
      assert(contains(zeroToTen, 10), "filter 2")

      assert(!contains(s, 0), "filter 3")
      assert(!contains(s, 5), "filter 4")
      assert(contains(s, 6), "filter 5")
      assert(contains(s, 10), "filter 6")
      assert(!contains(s, 11), "filter 7")
    }
  }

  test("forall test"){
    new TestSets {
      assert(forall(even, p=> p%2==0), "forall 1")
      assert(forall(zeroToTen, p=> p>=0), "forall 2")
      assert(!forall(zeroToTen, p=> p<0), "forall 3")
      assert(!forall(zeroToTen, p=> p%2==0), "forall 4")

    }
  }

  test("exists test") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(exists(s2, x => x%2==0), "exists 1")
      assert(exists(even, x => x==2), "exists 2")
      assert(exists(zeroToTen, x => x==7), "exists 3")
      assert(exists(even, x => x== -1000), "exists 4")
      assert(exists(even, x => x== 1000), "exists 5")
      assert(!exists(even, x => x== 1001), "exists 6")

    }
  }

  test("map test"){
    new TestSets {
      val s = map(even, x=> x+1)
      assert(contains(s, 1), "map 1")
      assert(contains(s, 3), "map 2")
      assert(!contains(s, 0), "map 3")
      assert(!contains(s, 2), "map 4")

      val unionSet =         union(union(union(union(union(singletonSet(1), singletonSet(3)), singletonSet(4)),singletonSet(5)), singletonSet(7)), singletonSet(1000))
      val expectedAfterMap = union(union(union(union(union(singletonSet(0), singletonSet(2)), singletonSet(3)),singletonSet(4)), singletonSet(6)), singletonSet(999))
      assert(FunSets.toString(map(unionSet, x=>x-1)) === FunSets.toString(expectedAfterMap))


      assert(forall(map(unionSet, x=>2*x), p=> p%2==0))

    }
  }




}
