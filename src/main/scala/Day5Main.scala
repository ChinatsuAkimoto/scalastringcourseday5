import java.io.{PrintStream, ByteArrayOutputStream, StringWriter, PrintWriter}
import java.util.StringJoiner

/**
  * @author ynupc
  *         Created on 2016/01/26
  */
object Day5Main {
  private val array: Array[String] = Array[String]("abc", "cde", "efg")

  def main(args: Array[String]): Unit = {
    val executionTime: ExecutionTime = new ExecutionTime(1000000)
    val strA = "A"
    val strB = "B"
    val strC = "C"
    val strD = "D"
    println("+ vs union vs concat (round 1)")
    println("+")
    executionTime.printlnAverageExecutionTime(strA + strB)
    executionTime.printlnAverageExecutionTime(strA + strB)
    executionTime.printlnAverageExecutionTime(strA + strB)
    println("union")
    executionTime.printlnAverageExecutionTime(strA.union(strB))
    executionTime.printlnAverageExecutionTime(strA.union(strB))
    executionTime.printlnAverageExecutionTime(strA.union(strB))
    println("concat")
    executionTime.printlnAverageExecutionTime(strA.concat(strB))
    executionTime.printlnAverageExecutionTime(strA.concat(strB))
    executionTime.printlnAverageExecutionTime(strA.concat(strB))
    println()
    println("+ vs union vs concat (round 2)")
    println("+")
    executionTime.printlnAverageExecutionTime(strA + strB + strC)
    executionTime.printlnAverageExecutionTime(strA + strB + strC)
    executionTime.printlnAverageExecutionTime(strA + strB + strC)
    println("union")
    executionTime.printlnAverageExecutionTime(strA.union(strB.union(strC)))
    executionTime.printlnAverageExecutionTime(strA.union(strB.union(strC)))
    executionTime.printlnAverageExecutionTime(strA.union(strB.union(strC)))
    println("concat")
    executionTime.printlnAverageExecutionTime(strA.concat(strB.concat(strC)))
    executionTime.printlnAverageExecutionTime(strA.concat(strB.concat(strC)))
    executionTime.printlnAverageExecutionTime(strA.concat(strB.concat(strC)))
    println()
    println("+ vs union vs concat (round 3)")
    println("+")
    executionTime.printlnAverageExecutionTime(strA + strB + strC + strD)
    executionTime.printlnAverageExecutionTime(strA + strB + strC + strD)
    executionTime.printlnAverageExecutionTime(strA + strB + strC + strD)
    println("union")
    executionTime.printlnAverageExecutionTime(strA.union(strB.union(strC.union(strD))))
    executionTime.printlnAverageExecutionTime(strA.union(strB.union(strC.union(strD))))
    executionTime.printlnAverageExecutionTime(strA.union(strB.union(strC.union(strD))))
    println("concat")
    executionTime.printlnAverageExecutionTime(strA.concat(strB.concat(strC.concat(strD))))
    executionTime.printlnAverageExecutionTime(strA.concat(strB.concat(strC.concat(strD))))
    executionTime.printlnAverageExecutionTime(strA.concat(strB.concat(strC.concat(strD))))
    println()
    println("ScalaStringBuilder vs JavaStringBuilder vs JavaStringBuffer")
    println("ScalaStringBuilder")
    executionTime.printlnAverageExecutionTime(testScalaStringBuilder())
    executionTime.printlnAverageExecutionTime(testScalaStringBuilder())
    executionTime.printlnAverageExecutionTime(testScalaStringBuilder())
    println("JavaStringBuilder")
    executionTime.printlnAverageExecutionTime(testJavaStringBuilder())
    executionTime.printlnAverageExecutionTime(testJavaStringBuilder())
    executionTime.printlnAverageExecutionTime(testJavaStringBuilder())
    println("JavaStringBuffer")
    executionTime.printlnAverageExecutionTime(testJavaStringBuffer())
    executionTime.printlnAverageExecutionTime(testJavaStringBuffer())
    executionTime.printlnAverageExecutionTime(testJavaStringBuffer())
    println()
    println("StringBuilder vs StringJoiner vs String.join1 vs String.join2 (round 1)")
    println("StringBuilder")
    executionTime.printlnAverageExecutionTime(testStringBuilderForJoiningPrefixSuffix())
    executionTime.printlnAverageExecutionTime(testStringBuilderForJoiningPrefixSuffix())
    executionTime.printlnAverageExecutionTime(testStringBuilderForJoiningPrefixSuffix())
    println("StringJoiner")
    executionTime.printlnAverageExecutionTime(testStringJoinerPrefixSuffix())
    executionTime.printlnAverageExecutionTime(testStringJoinerPrefixSuffix())
    executionTime.printlnAverageExecutionTime(testStringJoinerPrefixSuffix())
    println("String.join1")
    executionTime.printlnAverageExecutionTime(testStringJoinPrefixSuffix1())
    executionTime.printlnAverageExecutionTime(testStringJoinPrefixSuffix1())
    executionTime.printlnAverageExecutionTime(testStringJoinPrefixSuffix1())
    println("String.join2")
    executionTime.printlnAverageExecutionTime(testStringJoinPrefixSuffix2())
    executionTime.printlnAverageExecutionTime(testStringJoinPrefixSuffix2())
    executionTime.printlnAverageExecutionTime(testStringJoinPrefixSuffix2())
    println()
    println("StringBuilder vs StringJoiner vs String.join1 vs String.join2 (round 2)")
    println("StringBuilder")
    executionTime.printlnAverageExecutionTime(testStringBuilderForJoining())
    executionTime.printlnAverageExecutionTime(testStringBuilderForJoining())
    executionTime.printlnAverageExecutionTime(testStringBuilderForJoining())
    println("StringJoiner")
    executionTime.printlnAverageExecutionTime(testStringJoiner())
    executionTime.printlnAverageExecutionTime(testStringJoiner())
    executionTime.printlnAverageExecutionTime(testStringJoiner())
    println("String.join1")
    executionTime.printlnAverageExecutionTime(testStringJoin1())
    executionTime.printlnAverageExecutionTime(testStringJoin1())
    executionTime.printlnAverageExecutionTime(testStringJoin1())
    println("String.join2")
    executionTime.printlnAverageExecutionTime(testStringJoin2())
    executionTime.printlnAverageExecutionTime(testStringJoin2())
    executionTime.printlnAverageExecutionTime(testStringJoin2())
    println()
    println("PrintWriter vs PrintStream")
    println("PrintWriter")
    executionTime.printlnAverageExecutionTime(testPrintWriter())
    executionTime.printlnAverageExecutionTime(testPrintWriter())
    executionTime.printlnAverageExecutionTime(testPrintWriter())
    println("PrintStream")
    executionTime.printlnAverageExecutionTime(testPrintStream())
    executionTime.printlnAverageExecutionTime(testPrintStream())
    executionTime.printlnAverageExecutionTime(testPrintStream())
  }

  private def testScalaStringBuilder(): Unit = {
    val builder: StringBuilder = new StringBuilder()
    array foreach {
      element =>
        builder.append(element)
    }
    builder.result
  }

  private def testJavaStringBuilder(): Unit = {
    val builder: java.lang.StringBuilder = new java.lang.StringBuilder()
    array foreach {
      element =>
        builder.append(element)
    }
    builder.toString
  }

  private def testJavaStringBuffer(): Unit = {
    val buffer: StringBuffer = new StringBuffer()
    array foreach {
      element =>
        buffer.append(element)
    }
    buffer.toString
  }

  private def testStringBuilderForJoiningPrefixSuffix(): Unit = {
    val builder: java.lang.StringBuilder = new java.lang.StringBuilder()
    builder.append('[')
    array foreach {
      element =>
        builder.append(',').append(element)
    }
    builder.append(']').deleteCharAt(0).toString
  }

  private def testStringJoinerPrefixSuffix(): Unit = {
    val joiner: StringJoiner = new StringJoiner(",", "[", "]")
    array foreach {
      element =>
        joiner.add(element)
    }
    joiner.toString
  }

  private def testStringJoinPrefixSuffix1(): Unit = {
    String.join(",", array: _*).mkString("[", "", "]")
  }

  private def testStringJoinPrefixSuffix2(): Unit = {
    String.join(",", scala.collection.JavaConversions.asJavaIterable(array.toIterable)).mkString("[", "", "]")
  }

  private def testStringBuilderForJoining(): Unit = {
    val builder: java.lang.StringBuilder = new java.lang.StringBuilder()
    array foreach {
      element =>
        builder.append(',').append(element)
    }
    builder.deleteCharAt(0).toString
  }

  private def testStringJoiner(): Unit = {
    val joiner: StringJoiner = new StringJoiner(",")
    array foreach {
      element =>
        joiner.add(element)
    }
    joiner.toString
  }

  private def testStringJoin1(): Unit = {
    String.join(",", array: _*)
  }

  private def testStringJoin2(): Unit = {
    String.join(",", scala.collection.JavaConversions.asJavaIterable(array.toIterable))
  }

  private def testPrintWriter(): Unit = {
    val stringWriter: StringWriter = new StringWriter()
    val printWriter: PrintWriter = new PrintWriter(stringWriter)

    printWriter.print(true)
    printWriter.println("abc")
    printWriter.printf("%d\n", 123.asInstanceOf[java.lang.Integer])

    printWriter.close()
    stringWriter.toString
    stringWriter.close()
  }

  private def testPrintStream(): Unit = {
    val byteArrayOutputStream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val printStream: PrintStream = new PrintStream(byteArrayOutputStream)

    printStream.print(true)
    printStream.println("abc")
    //noinspection ScalaMalformedFormatString
    printStream.printf("%d\n", 123.asInstanceOf[java.lang.Integer])

    printStream.close()
    byteArrayOutputStream.toString
    byteArrayOutputStream.close()
  }
}
