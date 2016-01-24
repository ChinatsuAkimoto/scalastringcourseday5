package day5

import java.io.{PrintStream, ByteArrayOutputStream, PrintWriter, StringWriter}
import java.nio.charset._
import java.nio.{ByteBuffer, CharBuffer}
import java.text._
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.time.{LocalDate, ZoneOffset, ZonedDateTime}
import java.util.concurrent.{ExecutorService, Executors}
import java.util.{Locale, Calendar, Date, StringJoiner}

import akka.actor._
import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * @author ynupc
  *         Created on 2016/01/19
  */
class Day5TestSuite extends AssertionsForJUnit {

  @Test
  def testStringUnion(): Unit = {
    val str = "A"

    assert(str + "B" == "AB")
  }

  @Test
  def testStringConcat(): Unit = {
    val str = "A"

    assert(str.concat("B") == "AB")
  }

  @Test
  def testStringBuffer(): Unit = {
    var buffer: StringBuffer = new StringBuffer()

    assert(buffer.capacity == 16)
    assert(buffer.length == 0)

    buffer = new StringBuffer(3)

    assert(buffer.capacity == 3)
    assert(buffer.length == 0)

    val array: Array[String] = Array[String]("abc", "cde", "efg")
    for (i <- array.indices) {
      i match {
        case 0 =>
          buffer.append(array(i))
          assert(buffer.capacity == 3)
        case 1 =>
          buffer.append(array(i))
          assert(buffer.capacity == 8)
        case 2 =>
          buffer.append(array(i))
          assert(buffer.capacity == 18)
        case otherwise =>
          //Do nothing
      }
    }

    assert(buffer.capacity == 18)

    var str: String = buffer.toString
    assert(str == "abccdeefg")

    assert(buffer.length == 9)

    buffer.setLength(0)

    assert(buffer.capacity == 18)
    assert(buffer.length == 0)

    str = buffer.toString
    assert(str == "")
  }

  @Test
  def testStringBuilder1(): Unit = {
    var builder: StringBuilder = new StringBuilder()

    assert(builder.capacity == 16)
    assert(builder.length == 0)

    builder = new StringBuilder(3)

    assert(builder.capacity == 3)
    assert(builder.length == 0)

    val array: Array[String] = Array[String]("abc", "cde", "efg")
    for (element <- array) {
      builder.append(element)
    }
    var str: String = builder.result
    assert(str == "abccdeefg")

    assert(builder.length == 9)

    builder.setLength(0)

    assert(builder.capacity == 18)
    assert(builder.length == 0)

    str = builder.result
    assert(str == "")

    for (element <- array) {
      builder.append(element)
    }

    assert(builder.capacity == 18)
    assert(builder.length == 9)

    //clearメソッドはJavaにはないが、中身はsetLength(0)
    builder.clear

    assert(builder.capacity == 18)
    assert(builder.length == 0)

    for (element <- array) {
      builder.append(element)
    }

    builder.delete(0, builder.length)

    assert(builder.capacity == 18)
    assert(builder.length == 0)
  }

  @Test
  def testStringBuilder2(): Unit = {
    var builder: java.lang.StringBuilder = new java.lang.StringBuilder()

    assert(builder.capacity == 16)
    assert(builder.length == 0)

    builder = new java.lang.StringBuilder(3)

    assert(builder.capacity == 3)
    assert(builder.length == 0)

    val array: Array[String] = Array[String]("abc", "cde", "efg")
    for (element <- array) {
      builder.append(element)
    }
    var str: String = builder.toString
    assert(str == "abccdeefg")

    assert(builder.length == 9)

    builder.setLength(0)

    assert(builder.capacity == 18)
    assert(builder.length == 0)

    str = builder.toString
    assert(str == "")
  }


  @Test
  def testStringJoiner1(): Unit = {
    val array: Array[String] = Array[String]("abc", "cde", "efg")
    val joiner: StringJoiner = new StringJoiner(", ", "[", "]")
    array foreach {
      element =>
        joiner.add(element)
    }
    assert(joiner.toString == "[abc, cde, efg]")
  }

  @Test
  def testStringJoiner2(): Unit = {
    val array = Array[String]("abc", "cde", "efg")
    val joiner = new StringJoiner(", ")
    array foreach {
      element =>
        joiner.add(element)
    }
    assert(joiner.toString == "abc, cde, efg")
  }

  @Test
  def testStringJoin1(): Unit = {
    val array = Array[String]("abc", "cde", "efg")
    assert(String.join(", ", array: _*) == "abc, cde, efg")
    assert(String.join(", ", array: _*).mkString("[", "", "]") == "[abc, cde, efg]")
  }

  @Test
  def testStringJoin2(): Unit = {
    val array = Array[String]("abc", "cde", "efg")
    val iterable: java.lang.Iterable[String] = scala.collection.JavaConversions.asJavaIterable(array.toIterable)
    assert(String.join(", ", iterable) == "abc, cde, efg")
    assert(String.join(", ", iterable).mkString("[", "", "]") == "[abc, cde, efg]")
  }

  @Test
  def testPrintWriter(): Unit = {
    val stringWriter: StringWriter = new StringWriter()
    val printWriter: PrintWriter = new PrintWriter(stringWriter)

    printWriter.print(true)
    printWriter.println("abc")
    printWriter.printf("%d\n", 123.asInstanceOf[java.lang.Integer])

    printWriter.close()

    assert(stringWriter.toString ==
      """trueabc
        |123""".stripMargin.concat("\n"))
  }

  @Test
  def testPrintStream(): Unit = {
    val byteArrayOutputStream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val printStream: PrintStream = new PrintStream(byteArrayOutputStream)

    printStream.print(true)
    printStream.println("abc")
    //noinspection ScalaMalformedFormatString
    printStream.printf("%d\n", 123.asInstanceOf[java.lang.Integer])

    printStream.close()
    println(byteArrayOutputStream.toString)

    assert(byteArrayOutputStream.toString ==
      """trueabc
        |123""".stripMargin.concat("\n"))
  }

  private val utf8ByteArray1ForBufferTest: Array[Byte] =
    for (byte <- Array[Int](
      0xF0, 0xA0, 0xAE, 0xB7,//𠮷=U+20BB7=U+D842,U+DFB7
      0xE9, 0x87, 0x8E,//野=U+91CE
      0xE5, 0xAE, 0xB6,//家=U+5BB6
      0x00, 0x00, 0x00,
      0x00, 0x00, 0x00,
      0x00, 0x00, 0x00
    )) yield byte.toByte

  private val utf8ByteArray2ForBufferTest: Array[Byte] =
    for (byte <- Array[Int](
      0xF0, 0xA0, 0xAE, 0xB7,//𠮷=U+20BB7=U+D842,U+DFB7
      0xE9, 0x87, 0x8E,//野=U+91CE
      0xE5, 0xAE, 0xB6 //家=U+5BB6
    )) yield byte.toByte

  private val capacity = 16

  private val utf8ByteArray3ForBufferTest: Array[Byte] =
    {
      for (i <- 0 until capacity) yield {
        if (i < utf8ByteArray2ForBufferTest.length) {
          utf8ByteArray2ForBufferTest(i)
        } else {
          0: Byte
        }
      }
    }.toArray

  @Test
  def testBufferForEncoder1(): Unit = {
    val str = "𠮷野家"
    val encoder: CharsetEncoder = StandardCharsets.UTF_8.newEncoder.
      onMalformedInput(CodingErrorAction.REPORT).
      onUnmappableCharacter(CodingErrorAction.REPORT)
    val charBuffer: CharBuffer = CharBuffer.wrap(str)
    val byteBuffer: ByteBuffer = encoder.encode(charBuffer)
    val byteArray: Array[Byte] = byteBuffer.array
    assert(byteArray sameElements utf8ByteArray1ForBufferTest)
  }

  @Test
  def testBufferForEncoder2(): Unit = {
    val str = "𠮷野家"
    val encoder: CharsetEncoder = StandardCharsets.UTF_8.newEncoder.
      onMalformedInput(CodingErrorAction.REPORT).
      onUnmappableCharacter(CodingErrorAction.REPORT)
    val charBuffer: CharBuffer = CharBuffer.wrap(str)
    val byteBuffer: ByteBuffer = ByteBuffer.allocate(capacity)
    val coderResult: CoderResult = encoder.reset.encode(charBuffer, byteBuffer, true)
    assert(coderResult == CoderResult.UNDERFLOW)
    coderResult match {
      case CoderResult.UNDERFLOW =>
        val byteArray: Array[Byte] = byteBuffer.array
        assert(byteArray sameElements utf8ByteArray3ForBufferTest)
      case CoderResult.OVERFLOW =>
        //Do nothing
    }
  }

  @Test
  def testBufferForDecoder1(): Unit = {
    val byteArray: Array[Byte] = utf8ByteArray2ForBufferTest
    val decoder: CharsetDecoder = StandardCharsets.UTF_8.newDecoder.
      onMalformedInput(CodingErrorAction.REPORT).
      onUnmappableCharacter(CodingErrorAction.REPORT)
    val byteBuffer: ByteBuffer = ByteBuffer.wrap(byteArray)
    val charBuffer: CharBuffer = decoder.decode(byteBuffer)
    val str = charBuffer.toString
    assert(str == "𠮷野家")
  }

  @Test
  def testBufferForDecoder2(): Unit = {
    val byteArray: Array[Byte] = utf8ByteArray2ForBufferTest
    val decoder: CharsetDecoder = StandardCharsets.UTF_8.newDecoder.
      onMalformedInput(CodingErrorAction.REPORT).
      onUnmappableCharacter(CodingErrorAction.REPORT)
    val byteBuffer: ByteBuffer = ByteBuffer.wrap(byteArray)
    val charBuffer: CharBuffer = CharBuffer.allocate(capacity)
    val coderResult: CoderResult = decoder.reset.decode(byteBuffer, charBuffer, true)
    assert(coderResult == CoderResult.UNDERFLOW)
    coderResult match {
      case CoderResult.UNDERFLOW =>
        val str = charBuffer.flip.toString
        assert(str == "𠮷野家")
      case CoderResult.OVERFLOW =>
        //Do nothing
    }
  }

  @Test
  def testFormat1(): Unit = {
    assert("%d%%".format(100) == "100%")
    //noinspection ScalaMalformedFormatString
    assert(String.format("%d%%", 100.asInstanceOf[java.lang.Integer]) == "100%")
  }

  @Test
  def testFormat2(): Unit = {
    //%
    assert("%%".format() == "%")
    //Char
    assert("%c".format('x') == "x")
    //String
    assert("%s".format("xyz") == "xyz")
    //10進数
    assert("%d".format(123) == "123")
    assert("%d".format(-123) == "-123")
    //正数に+付き10進数
    assert("%+d".format(123) == "+123")
    assert("%+d".format(-123) == "-123")
    //正数にスペース付き10進数
    assert("% d".format(123) == " 123")
    assert("% d".format(-123) == "-123")
    //負数に()付き10進数
    assert("%(d".format(123) == "123")
    assert("%(d".format(-123) == "(123)")
    //3桁ごとカンマ付き10進数
    assert("%,d".format(12345) == "12,345")
    //0埋め
    assert("%05d".format(123) == "00123") //prepend
    assert(123.toString.padTo(5, '0') == "12300") //padToはappend
    //16進数
    assert("%x".format(123) == "7b")
    //16進数代替フォーム
    assert("%#x".format(123) == "0x7b")
    //8進数
    assert("%o".format(123) == "173")
    //8進数代替フォーム
    assert("%#o".format(123) == "0173")
    //右詰
    assert("[%4d]".format(123) == "[ 123]")
    //左詰
    assert("[%-4d]".format(123) == "[123 ]")
    //最大表示幅指定（幅を超えたものは切り捨て）
    assert("[%.4s]".format("xyzab") == "[xyza]")
    assert("[%5.4s]".format("xyzab") == "[ xyza]")
    assert("[%-5.4s]".format("xyzab") == "[xyza ]")
    //直前と同じ値（直前と同じものを引数に入れるくらいなら、これを使用した方が効率的）
    //noinspection ScalaMalformedFormatString
    assert("%d:%<d:%d:%<d".format(1, 22) == "1:1:22:22")
    //引数のインデックス指定（同じものを連続せずに何度も引数に入れるくらいなら、これでまとめる方が効率的）
    assert("%d:%d:%d".format(1, 22, 333) == "1:22:333")
    assert("%1$d:%2$d:%3$d".format(1, 22, 333) == "1:22:333")
    assert("%3$d:%1$d:%2$d".format(1, 22, 333) == "333:1:22")
    assert("%3$d:%1$d:%d:%d:%3$d".format(1, 22, 333) == "333:1:1:22:333")
    //真偽値（小文字）
    //nullの場合はfalse
    //プリミティブ型booleanでもラッパークラスのBooleanでもない場合はtrue
    assert("%b".format(true) == "true")
    assert("%b".format(java.lang.Boolean.TRUE) == "true")
    assert("%b".format(0) == "true")
    assert("%b".format(false) == "false")
    assert("%b".format(java.lang.Boolean.FALSE) == "false")
    assert("%b".format(null) == "false")
    //真偽値（大文字）
    //nullの場合はfalse
    //プリミティブ型booleanでもラッパークラスのBooleanでもない場合はtrue
    assert("%B".format(true) == "TRUE")
    assert("%B".format(java.lang.Boolean.TRUE) == "TRUE")
    assert("%B".format(0) == "TRUE")
    assert("%B".format(false) == "FALSE")
    assert("%B".format(java.lang.Boolean.FALSE) == "FALSE")
    assert("%B".format(null) == "FALSE")
    //浮動小数
    assert("%e".format(math.Pi) == "3.141593e+00")
    assert("%f".format(math.Pi) == "3.141593")
    assert("%g".format(math.Pi) == "3.14159")
    assert("%a".format(math.Pi) == "0x1.921fb54442d18p1")
    //OS非依存の改行文字
    //Unix: \n
    //Windows: \r\n
    printf("%n")
    //日付・時刻
    val date = new Date()
    printf("%1$tY年%1$tm月%1$td日%tA\n", date)
    printf("%1$tY年%1$tm月%1$td日%tA%n".formatLocal(java.util.Locale.US, date))
    println("%1$tY年%1$tm月%1$td日%tA".formatLocal(java.util.Locale.JAPAN, date))
    val calendar = Calendar.getInstance
    printf("%1$tY年%1$tm月%1$td日%tA\n", calendar)
    printf("%1$tY年%1$tm月%1$td日%tA%n".formatLocal(java.util.Locale.US, calendar))
    println("%1$tY年%1$tm月%1$td日%tA".formatLocal(java.util.Locale.JAPAN, calendar))
    val zonedDateTime = ZonedDateTime.now
    //noinspection ScalaMalformedFormatString
    printf("%1$tY年%1$tm月%1$td日%tA\n", zonedDateTime)
    printf("%1$tY年%1$tm月%1$td日%tA%n".formatLocal(java.util.Locale.US, zonedDateTime))
    println("%1$tY年%1$tm月%1$td日%tA".formatLocal(java.util.Locale.JAPAN, zonedDateTime))
    //ハッシュコード（16進数）
    printf("%h\n", new Object())
  }

  @Test
  def testDateTimeFormatter(): Unit = {
    val zonedDateTime: ZonedDateTime = ZonedDateTime.now
    val tdf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    println(tdf.format(zonedDateTime))
    println(zonedDateTime.format(tdf))

    val localDate1: LocalDate = LocalDate.parse("2016/01/01", tdf)
    val date1: Date = Date.from(localDate1.atStartOfDay(ZoneOffset.UTC).toInstant)

    val ta: TemporalAccessor = tdf.parse("2016/01/01")
    val localDate2: LocalDate = LocalDate.from(ta)
    val date2: Date = Date.from(localDate2.atStartOfDay(ZoneOffset.of("+09:00")).toInstant)

    assert("%1$tY年%1$tm月%1$td日".format(date1) == "2016年01月01日")
    assert("%1$tY年%1$tm月%1$td日".format(date2) == "2016年01月01日")
  }

  @Test
  def testMessageFormat(): Unit = {
    val messageFormat: MessageFormat = new MessageFormat("今日は{0,date,yyyy年MM月dd日}、時刻は{0,time}。天気は{1}です。")
    println(messageFormat.format(Array[Object](new Date(0L), "晴れ")))
    val parsed = messageFormat.parse("今日は1970年01月01日、時刻は9:00:00。天気は晴れです。")

    assert(parsed(1) == "晴れ")
  }

  @Test
  def testNumberFormat(): Unit = {
    val numberFormat1: NumberFormat = NumberFormat.getInstance(Locale.JAPAN)

    assert(numberFormat1.getCurrency.getCurrencyCode == "JPY")

    assert(numberFormat1.format(100L) == "100")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat1.parse("100") == 100)
    assert(numberFormat1.format(0.5772156649D) == "0.577")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat1.parse("0.577") == 0.577)

    val numberFormat2: NumberFormat = NumberFormat.getIntegerInstance(Locale.JAPAN)
    assert(numberFormat2.format(100L) == "100")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat2.parse("100") == 100)
    assert(numberFormat2.format(0.5772156649D) == "1")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat2.parse("0.577") == 0)

    val numberFormat3: NumberFormat = NumberFormat.getCurrencyInstance(Locale.JAPAN)

    assert(numberFormat3.format(100L) == "￥100")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat3.parse("￥100") == 100)
    assert(numberFormat3.format(0.5772156649D) == "￥1")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat3.parse("￥0.577") == 0.577)

    val numberFormat4: NumberFormat = NumberFormat.getPercentInstance(Locale.JAPAN)
    assert(numberFormat4.format(100L) == "10,000%")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat4.parse("10,000%") == 100)
    assert(numberFormat4.format(0.5772156649D) == "58%")
    //noinspection ComparingUnrelatedTypes
    assert(numberFormat4.parse("57.7%") == 0.5770000000000001)//buggy???
  }

  @Test
  def testDecimalFormat(): Unit = {
    val decimalFormat: DecimalFormat = new DecimalFormat()
    assert(decimalFormat.getCurrency.getCurrencyCode == "JPY")
    assert(decimalFormat.format(100L) == "100")
    //noinspection ComparingUnrelatedTypes
    assert(decimalFormat.parse("100") == 100)
    assert(decimalFormat.format(0.5772156649D) == "0.577")
    //noinspection ComparingUnrelatedTypes
    assert(decimalFormat.parse("0.577") == 0.577)
  }

  @Test
  def testChoiceFormat1(): Unit = {
    val choiceFormat: ChoiceFormat =
      new ChoiceFormat(
        Array[Double](-1D, 0D),
        Array[String]("負の数", "正の数"))

    //num < -1
    assert(choiceFormat.format(-2) == "負の数")
    //-1<= num < 0
    assert(choiceFormat.format(-0.5D) == "負の数")
    //0 <= num
    assert(choiceFormat.format(0D) == "正の数")
    assert(choiceFormat.format(1D) == "正の数")
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("負の数") == -1D)
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("正の数") == 0D)
  }

  @Test
  def testChoiceFormat2(): Unit = {
    val choiceFormat: ChoiceFormat =
      new ChoiceFormat(
        Array[Double](-1D, 0D, ChoiceFormat.nextDouble(0D)),
        Array[String]("負の数", "0", "正の数"))
    //num < -1
    assert(choiceFormat.format(-2) == "負の数")
    //-1<= num < 0
    assert(choiceFormat.format(-0.5D) == "負の数")
    //num == 0
    assert(choiceFormat.format(0D) == "0")
    //0 < num
    assert(choiceFormat.format(1D) == "正の数")
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("負の数") == -1D)
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("0") == 0D)
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("正の数") == ChoiceFormat.nextDouble(0D))
  }

  @Test
  def testChoiceFormat3(): Unit = {
    val choiceFormat: ChoiceFormat = new ChoiceFormat("-1#負の数| 0#0| 0<正の数")
    //num < -1
    assert(choiceFormat.format(-2) == "負の数")
    //-1<= num < 0
    assert(choiceFormat.format(-0.5D) == "負の数")
    //num == 0
    assert(choiceFormat.format(0D) == "0")
    //0 < num
    assert(choiceFormat.format(1D) == "正の数")
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("負の数") == -1D)
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("0") == 0D)
    //noinspection ComparingUnrelatedTypes
    assert(choiceFormat.parse("正の数") == ChoiceFormat.nextDouble(0D))
  }

  @Test
  def testDateFormat(): Unit = {
    val date: Date = new Date(Long.MinValue)
    val dateFormat1: DateFormat = DateFormat.getDateInstance
    println(dateFormat1.format(date) == "292269055/12/03")
    println(dateFormat1.parse("292269055/12/03"))
    val dateFormat2: DateFormat = DateFormat.getTimeInstance
    println(dateFormat2.format(date) == "1:47:04")
    println(dateFormat2.parse("1:47:04"))
    val dateFormat3: DateFormat = DateFormat.getDateTimeInstance
    println(dateFormat3.format(date) == "292269055/12/03 1:47:04")
    println(dateFormat3.parse("292269055/12/03 1:47:04"))
  }

  @Test
  def testSimpleDateFormat(): Unit = {
    val date: Date = new Date(Long.MaxValue)
    val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat("Y年M月D日（E）")
    println(simpleDateFormat.format(date))
    println(simpleDateFormat.parse("292278994年8月229日（日）"))
  }

  private class ObjectExample(private var data: Int) {
    def increment(): Unit = {
      synchronized[Unit] {
        data += 1
      }
    }

    def getData: Int = {
      synchronized[Int] {
        data
      }
    }
  }

  private val list: List[Int] = (0 to 3).toList
  private val objectExample: ObjectExample = new ObjectExample(-1)

  private class ThreadExample(name: String) extends Runnable {
    def run() = {
      Thread.sleep(1000L)
      objectExample.increment()
      val data: Int = objectExample.getData
      printf("%s-data=%d%n", name, data)
    }
  }

  /**
    * Thread
    */
  @Test
  def multiThread1(): Unit = {
    for (i <- list.par) {
      val thread: Thread = new Thread(new ThreadExample(s"example1-threadNumber$i"))
      thread.start()
    }
  }

  /**
    * java.util.concurrent
    */
  @Test
  def multiThread2(): Unit = {
    for (i <- list.par) {
      val executor: ExecutorService = Executors.newSingleThreadExecutor()
      executor.execute(new ThreadExample(s"example2-threadNumber$i"))
      executor.shutdown()
    }
  }

  private class ActorExample extends Actor {
    override def receive: Receive = {
      case name =>
        Thread.sleep(1000L)
        objectExample.increment()
        val data: Int = objectExample.getData
        printf("%s-data=%d%n", name, data)
        context.system.terminate()
    }
  }

  /**
    * akka.actor
    */
  @Test
  def multiThread3(): Unit = {
    val system: ActorSystem = ActorSystem("actor-example")
    for (i <- list.par) {
      val actor: ActorRef = system.actorOf(Props(new ActorExample()), s"actorExample$i")
      actor ! s"example3-actorNumber$i"
    }
    Await.result(system.whenTerminated, Duration.Inf)
  }
}
