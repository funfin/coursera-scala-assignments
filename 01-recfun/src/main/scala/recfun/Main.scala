package recfun
import common._
import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(c<0 || r<0 || c>r) throw new NoSuchElementException()
    if (c ==0 || c==r) 1
    else pascal(c-1, r-1)+pascal(c, r-1);
  }



  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    @tailrec
    def isBalanced(chars: List[Char], opened: Int, closed: Int): Boolean = {
      if (chars.isEmpty)
        opened == closed;
      else if (chars.head == '(')
        isBalanced(chars.tail, opened + 1, closed)
      else if (chars.head == ')' && opened > 0 && opened > closed)
        isBalanced(chars.tail, opened, closed + 1)
      else if (chars.head == ')' && closed >=opened)
        false
      else
        isBalanced(chars.tail, opened, closed)
    }
    isBalanced(chars, 0, 0);
  }



  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {

    def innerCountChange(sum: Int,  coins: List[Int]): Int = {
      if(coins.isEmpty)
        0
      else if(sum+coins.head< money) {
        innerCountChange(sum + coins.head, coins)+innerCountChange(sum, coins.tail)
      } else if(sum+coins.head== money) {
        1+ innerCountChange(sum, coins.tail)
      }else{
        innerCountChange(sum, coins.tail)
      }
    }

    innerCountChange(0, coins: List[Int])
  }
}
