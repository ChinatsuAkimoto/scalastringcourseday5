import java.io.{PrintStream, ByteArrayOutputStream, StringWriter, PrintWriter}
import java.text.{DateFormat, SimpleDateFormat}
import java.time.{LocalDateTime, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.util.{Calendar, Date, StringJoiner}

/**
  * @author ynupc
  *         Created on 2016/01/26
  */
object Day5Main {
  private val array: Array[String] = Array[String]("abc", "cde", "efg")
  private val dateTimeFormat: String = "yyyy/MM/dd HH:mm:ss"
  private val dateTime: String = "2045/12/03 01:47:04"
  private val date: Date = new Date()
  private val calendar: Calendar = Calendar.getInstance()
  private val zonedDateTime: ZonedDateTime = ZonedDateTime.now
  private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat)
  private val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat(dateTimeFormat)
  private val dateFormat: DateFormat = DateFormat.getInstance()

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
    val str: String = "ScalaStringBuilder.clear vs JavaStringBuilder.setLength(0) vs JavaStringBuilder.delete(0, length) vs JavaStringBuffer.setLength(0) vs JavaStringBuffer.delete(0, length)"
    println(str)
    println("ScalaStringBuilder.clear")
    val scalaStringBuilder = new StringBuilder()
    scalaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(scalaStringBuilder.clear)
    scalaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(scalaStringBuilder.clear)
    scalaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(scalaStringBuilder.clear)
    println("JavaStringBuilder.setLength(0)")
    val javaStringBuilder = new java.lang.StringBuilder()
    javaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuilder.setLength(0))
    javaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuilder.setLength(0))
    javaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuilder.setLength(0))
    println("JavaStringBuilder.delete(0, length)")
    javaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuilder.delete(0, str.length))
    javaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuilder.delete(0, str.length))
    javaStringBuilder.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuilder.delete(0, str.length))
    println("JavaStringBuffer.setLength(0)")
    val javaStringBuffer = new java.lang.StringBuffer()
    javaStringBuffer.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuffer.setLength(0))
    javaStringBuffer.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuffer.setLength(0))
    javaStringBuffer.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuffer.setLength(0))
    println("JavaStringBuffer.delete(0, length)")
    javaStringBuffer.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuffer.delete(0, str.length))
    javaStringBuffer.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuffer.delete(0, str.length))
    javaStringBuffer.append(str)
    executionTime.printlnAverageExecutionTime(javaStringBuffer.delete(0, str.length))
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
    println()
    println("DateTimeFormatter vs SimpleDateFormat vs DateFormat (round 1: instantiation)")
    println("DateTimeFormatter")
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterInstantiation())
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterInstantiation())
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterInstantiation())
    println("SimpleDateFormat")
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatInstantiation())
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatInstantiation())
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatInstantiation())
    println("DateFormat")
    executionTime.printlnAverageExecutionTime(testDateFormatInstantiation())
    executionTime.printlnAverageExecutionTime(testDateFormatInstantiation())
    executionTime.printlnAverageExecutionTime(testDateFormatInstantiation())
    println()
    println("DateTimeFormatter vs SimpleDateFormat vs DateFormat vs String (round 2: format)")
    println("DateTimeFormatter")
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterFormat())
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterFormat())
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterFormat())
    println("SimpleDateFormat")
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatFormat())
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatFormat())
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatFormat())
    println("DateFormat")
    executionTime.printlnAverageExecutionTime(testDateFormatFormat())
    executionTime.printlnAverageExecutionTime(testDateFormatFormat())
    executionTime.printlnAverageExecutionTime(testDateFormatFormat())
    println("String")
    executionTime.printlnAverageExecutionTime(testStringFormat())
    executionTime.printlnAverageExecutionTime(testStringFormat())
    executionTime.printlnAverageExecutionTime(testStringFormat())
    println()
    println("DateTimeFormatter vs SimpleDateFormat vs DateFormat vs LocalDateTime (round 3: parse)")
    println("DateTimeFormatter")
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterParse())
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterParse())
    executionTime.printlnAverageExecutionTime(testDateTimeFormatterParse())
    println("SimpleDateFormat")
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatParse())
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatParse())
    executionTime.printlnAverageExecutionTime(testSimpleDateFormatParse())
    println("DateFormat")
    executionTime.printlnAverageExecutionTime(testDateFormatParse())
    executionTime.printlnAverageExecutionTime(testDateFormatParse())
    executionTime.printlnAverageExecutionTime(testDateFormatParse())
    println("LocalDateTime")
    executionTime.printlnAverageExecutionTime(testLocalDateTimeParse())
    executionTime.printlnAverageExecutionTime(testLocalDateTimeParse())
    executionTime.printlnAverageExecutionTime(testLocalDateTimeParse())
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

  private def testDateTimeFormatterInstantiation(): Unit = {
    DateTimeFormatter.ofPattern(dateTimeFormat)
  }

  private def testSimpleDateFormatInstantiation(): Unit = {
    new SimpleDateFormat(dateTimeFormat)
  }

  private def testDateFormatInstantiation(): Unit = {
    DateFormat.getDateTimeInstance()
  }

  private def testDateTimeFormatterFormat(): Unit = {
    dateTimeFormatter.format(zonedDateTime)
  }

  private def testSimpleDateFormatFormat(): Unit = {
    simpleDateFormat.format(date)
  }

  private def testDateFormatFormat(): Unit = {
    dateFormat.format(date)
  }

  private def testStringFormat(): Unit = {
    dateTimeFormat.format(date)
  }

  private def testDateTimeFormatterParse(): Unit = {
    dateTimeFormatter.parse(dateTime)
  }

  private def testSimpleDateFormatParse(): Unit = {
    simpleDateFormat.parse(dateTime)
  }

  private def testDateFormatParse(): Unit = {
    dateFormat.parse(dateTime)
  }

  private def testLocalDateTimeParse(): Unit = {
    LocalDateTime.parse(dateTime, dateTimeFormatter)
  }
}
