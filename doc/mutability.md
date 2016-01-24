#1.　ミュータビリティ
<h3>1.1　ミュータビリティ</h3>
<img src="../image/string_course.002.jpeg" width="500px"><br>
ミュータビリティとは可変性・不変性を示す言葉です。ミュータブルは可変性を意味し、イミュータブルは固定性を意味します。変数がインスタンス化後に変更可能なクラスをミュータブルクラス、変更不可能なクラスをイミュータブルクラスと呼びます。コレクションのクラスではミュータブルは可変長、イミュータブルは固定長を意味します。<br>
***
<h3>1.2　スレッドセーフティ</h3>
<img src="../image/string_course.003.jpeg" width="500px"><br>
スレッドセーフティとはマルチスレッド下でも問題なく動作するかどうかを示す言葉です。
マルチスレッド下ではシングルスレッドで発生しない様々な問題が発生します。
例えば、スレッドAとスレッドBが同一クラスを操作するとスレッドAが操作している変数vを、その操作が終わっていないにも関わらず、スレッドBが操作してしまい、スレッドAが想定している結果と違う値の変数vになってしまう場合があります。
この時、スレッドAが操作している時に変数vをスレッドBが操作できないように制御（排他制御）されている状態はスレッドセーフであり、スレッドAが操作している時に変数vをスレッドBが操作できる状態はスレッドアンセーフです。<br>
排他制御の方法として、変数をコンストラクタでの初期化以外変更できないようにする方法（テレスコーピングコンストラクタパターン）や変数を全てprivateにし（テレスコーピングコンストラクタパターン以外、JavaBeansパターンやビルダーパターンなど）変数を操作するメソッド全てにsynchronizedをつける方法がある。<br>
テレスコーピングコンストラクタパターン・JavaBeansパターン・ビルダーパターンについては、<a href="#コラムテレスコーピングコンストラクタパターンjavabeansパターンビルダーパターン">コラム：テレスコーピングコンストラクタパターン・JavaBeansパターン・ビルダーパターン</a>を参照ください。マルチスレッドプログラミングについては、<a href="#コラムマルチスレッドプログラミング">コラム：マルチスレッドプログラミング</a>を参照ください。
***
<h3>1.3　文字列クラスのミュータビリティとスレッドセーフティ</h3>
<img src="../image/string_course.004.jpeg" width="500px"><br>
Stringはイミュータブルクラスなので、インスタンス化後に操作可能な変数がないためスレッドセーフです。
StringBuilderとStringBufferはミュータブルクラスです。StringBuilderはスレッドアンセーフですが、StringBufferはスレッドセーフです。
一見するとイミュータブルをミュータブル、スレッドアンセーフよりスレッドセーフの方が優れていて、常にミュータブルでスレッドセーフなStringBufferクラスだけを存在すればいいのではと思うかもしれません。しかし、イミュータブルをミュータブルにするとクラスに載せるメソッドが増えメモリを多く使います。スレッドアンセーフからスレッドセーフにすると排他制御のためのオーバヘッドが発生して同じ処理でもスレッドセーフの方が処理速度が落ちます。このようなトレードオフ（一方を追求すると一方を犠牲にせざるをえない状態）が発生するので、目的に合わせて使用するクラスを選択しましょう。
***
<h3>1.4　String</h3>
<img src="../image/string_course.005.jpeg" width="500px"><br>
Stringはイミュータブル（固定長）でスレッドセーフな文字列クラスです。
＋メソッドやunionメソッドで文字列を結合するときはSeqLikeで<a href="http://www.scala-lang.org/api/current/index.html#scala.collection.generic.CanBuildFrom" target="_blank">CanBuildFrom</a>を用いてミュータブルなビルダークラスmutable.Builderを生成して文字列を結合し、新しいインスタンスを生成しています。(JavaではStringBuilderが内部処理で使用されます。)
新しいインスタンスを生成する点はconcatメソッドでも同様です。
```scala
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
```
***
<h3>1.5　StringBuffer/StringBuilder</h3>
<img src="../image/string_course.006.jpeg" width="500px"><br>
<img src="../image/string_course.007.jpeg" width="500px"><br>
<img src="../image/string_course.008.jpeg" width="500px"><br>
```java
    /**
     * Removes the characters in a substring of this sequence.
     * The substring begins at the specified <code>start</code> and extends to
     * the character at index <code>end - 1</code> or to the end of the
     * sequence if no such character exists. If
     * <code>start</code> is equal to <code>end</code>, no changes are made.
     *
     * @param      start  The beginning index, inclusive.
     * @param      end    The ending index, exclusive.
     * @return     This object.
     * @throws     StringIndexOutOfBoundsException  if <code>start</code>
     *             is negative, greater than <code>length()</code>, or
     *             greater than <code>end</code>.
     */
    public AbstractStringBuilder delete(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
            System.arraycopy(value, start+len, value, start, count-end);
            count -= len;
        }
        return this;
    }
```

```java
    /**
     * Sets the length of the character sequence.
     * The sequence is changed to a new character sequence
     * whose length is specified by the argument. For every nonnegative
     * index <i>k</i> less than <code>newLength</code>, the character at
     * index <i>k</i> in the new character sequence is the same as the
     * character at index <i>k</i> in the old sequence if <i>k</i> is less
     * than the length of the old character sequence; otherwise, it is the
     * null character <code>'&#92;u0000'</code>.
     *
     * In other words, if the <code>newLength</code> argument is less than
     * the current length, the length is changed to the specified length.
     * <p>
     * If the <code>newLength</code> argument is greater than or equal
     * to the current length, sufficient null characters
     * (<code>'&#92;u0000'</code>) are appended so that
     * length becomes the <code>newLength</code> argument.
     * <p>
     * The <code>newLength</code> argument must be greater than or equal
     * to <code>0</code>.
     *
     * @param      newLength   the new length
     * @throws     IndexOutOfBoundsException  if the
     *               <code>newLength</code> argument is negative.
     */
    public void setLength(int newLength) {
        if (newLength < 0)
            throw new StringIndexOutOfBoundsException(newLength);
        if (newLength > value.length)
            expandCapacity(newLength);

        if (count < newLength) {
            for (; count < newLength; count++)
                value[count] = '\0';
        } else {
            count = newLength;
        }
    }
```
<img src="../image/string_course.009.jpeg" width="500px"><br>
<img src="../image/string_course.010.jpeg" width="500px"><br>
```scala
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
```
***
<h3>1.6　StringJoiner</h3>
<img src="../image/string_course.011.jpeg" width="500px"><br>
StringJoinerはデリミタ（区切り文字）と任意で接頭辞・接尾辞を設定して、文字列を結合するクラスです。
addメソッドで文字列を追加し、toStringでStringを出力するビルダークラスです。
例えば、<a href="https://ja.wikipedia.org/wiki/Comma-Separated_Values" target="_blank">CSV、SSV、TSV</a>などを作成するときに有用です。
StringJoinerの中身はStringBuilderです。
```scala
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
```
***
<h3>1.7　String.joinメソッド</h3>
<img src="../image/string_course.012.jpeg" width="500px"><br>
String.joinの中身はStringJoinerですが、接頭辞・接尾辞を与えることはできません。
mkStringメソッドを組み合わせて使用することで、StringJoiner相当のことができます。<br>
String.joinよりStringJoinerの方が高速であり、StringJoinerよりStringBuilderの方が高速だという報告が掲載されたブログの記事があります（<a href="http://d.hatena.ne.jp/nowokay/20140409" target="_blank">Java8時代の文字列連結まとめ</a>）。
Stringをコードポイントに変換する際にStringのcodePointAt/codePointBeforeメソッドよりCharacterクラスのcodePointAt/codePointBeforeメソッドの方が、Stringで直接扱うよりChar配列に変換して扱う方が、処理速度が高速化するように（<a href="https://www.ibm.com/developerworks/jp/java/library/j-unicode/" target="_blank">Java 言語による Unicode サロゲート・プログラミング</a>）、lower levelで処理を書けば高速化できることは<a href="https://github.com/ynupc/scalastringcourseday3/blob/master/doc/implementation.md" target="_blank">Day 3</a>で既に学習しました。lower levelで処理を書けば少ないメモリ消費で処理できるでしょう。このようなことは、これまでに学んできたことから我々はjoinの処理速度についても試す前に想定できるはずです（lower levelになる分、プログラムが長大化し読みづらくなる、変数が増えてバグが発生する可能性が増えるといった短所もあります）。しかし、想定通りの挙動であるかを実際に確認する作業は大切なことです。<br>
String.joinはJavaの可変長引数メソッドとIterableを引数とするメソッドの２つありますが、どちらもScalaで使用する場合はJavaとの互換性を考慮する必要があります。可変長引数に関するのJavaとScalaの間の互換性については、<a href="#コラム可変長引数に関するのjavascala互換性">コラム：可変長引数に関するのJava/Scala互換性</a>、コレクションに関するJavaとScalaの間の互換性については、<a href="#コラムコレクションに関するjavascala互換性">コラム：コレクションに関するJava/Scala互換性</a>を参照ください。
```scala
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
```
***
<h3>1.8　PrintWriter (StringWriter)/PrintStream (ByteArrayOutputStream)</h3>
PrintWriterやPrintStreamのprint, println, printfメソッドを使って文字列を生成する方法があります。<br>
参考文献：<br>
<a href="http://www.ne.jp/asahi/hishidama/home/tech/java/string.html#PrintWriter" target="_blank">PrintWriter/PrintStream</a>
```scala
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
```
***
<h3>1.9　java.nio.Buffer</h3>
<img src="../image/string_course.013.jpeg" width="500px"><br>
java.nio.Bufferは主にBufferを継承したByteBuffer・CharBufferがエンコーダ・デコーダの内部で使用されます。
StringBuilder・StringBufferではcapacityを超えてCharを追加しようとすると自動的にcapacityを増やしてくれますが、ByteBuffer・CharBufferではcapacityは増えずにオーバフローします。どの位置から読み込む/書き込むかというpositionとどの位置まで読み込める/書き込めるかというlimitを変化させながらデータを読み込んだり書き込んだりします。
```scala
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
```
***
<h3>コラム：テレスコーピングコンストラクタパターン・JavaBeansパターン・ビルダーパターン</h3>
生成に関するデザインパターンにはテレスコーピングコンストラクタパターン・JavaBeansパターン・ビルダーパターンなどがあります。
StringBufferやStringBuilderはビルダーパターンで設計されています。
<h4>（１）テレスコーピングコンストラクタパターン</h4>
生成に必須なパラメタを引数に与えるコンストラクタの他に、必須なパラメタに加え任意のパラメタを引数に与えるコンストラクタをクラスに持たせます。
任意のパラメタ数が増えれば増えるだけコンストラクタ数が増加するため、良くないパターン（アンチパターン）と考えられる場合もありますが、クラスが持つ変数をコンストラクタの中でのみいじれるようにすることでスレッドセーフなイミュータブルクラスを作成することができます。
テレスコーピングとは、望遠鏡の筒のようにはめ込むという意味です。
パラメタをコンストラクタにはめ込む（テレスコープする）のでテレスコーピングコンストラクタパターンと呼ばれているのだと思います。
<h4>（２）JavaBeansパターン</h4>
引数なしのデフォルトコンストラクタにより生成し、setter/getterメソッドによって値の変更・取得を行います。
Scalaには<a href="http://www.scala-lang.org/files/archive/api/current/index.html#scala.beans.BeanProperty" target="_blank">@BeanProperty</a>や<a href="http://www.scala-lang.org/files/archive/api/current/index.html#scala.beans.BooleanBeanProperty" target="_blank">@BooleanBeanProperty</a>アノテーションでクラス内のvar変数fieldNameに注釈付けることでsetter/getterメソッド（setFieldName/getFieldName、@BooleanBeanPropertyの場合はgetFieldNameの代わりにisFieldName）を自動挿入します。
JavaBeansパターンではsetterメソッドが必要なため必ずミュータブルクラスになります。
JavaBeansとは、Javaで書かれた移植可能なプラットフォームに依存しないコンポーネントモデルであり、<a href="http://download.oracle.com/otndocs/jcp/7224-javabeans-1.01-fr-spec-oth-JSpec/" target="_blank">JavaBean仕様</a>に従うもののことを指します。

<h4>（３）ビルダーパターン</h4>
まず、生成に必須なパラメタを引数に与えるコンストラクタによりビルダークラスを生成します。
次に、ビルダークラスにsetterメソッドで任意のパラメタを設定します。
最後に、ビルダークラスのbuildメソッドにより、生成したいインスタンスを生成します。
StringBuffer/StringBuilderはビルダーパターンで設計されています。
StringBuffer/StringBuilderがビルダークラスで、buildメソッドはtoStringメソッド、生成したいインスタンスはStringです。
***
<h3>コラム：マルチスレッドプログラミング</h3>
マルチスレッドプログラミングにおいては、同じクラスを複数のスレッドが操作して目的通りに動作しないデータ競合が起こったり、デッドロックが起こってしまうといった危険がありスレッドセーフティを意識してプログラミングすることが大切です。そのためにどのように排他制御・非同期して、どのように同期するかについては様々なことを学ぶ必要があります。サンプルコードには変数の操作がsynchronizedされたクラスと３種類のマルチスレッド（１）Thread、（２）java.util.concurrent、（３）Actorの実装例を載せます。なお、サンプルコードではList型のコレクションクラスlistをfor文で回すとき、parメソッドによって処理を複数のプロセッサに分散化しています。<br>
参考文献：
<ul>
  <li><a href="https://twitter.github.io/scala_school/concurrency.html" target="_blank">Concurrency in Scala</a></li>
  <li><a href="http://doc.akka.io/docs/akka/2.4.1/scala/actors.html" target=_blank">Actors</a></li>
</ul>
```scala
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
```
***
<h3>コラム：可変長引数に関するのJava/Scala互換性</h3>
Javaの可変長引数は配列型に変換され、Scalaの可変長引数はSeq型に変換されます。
Javaの可変長引数を持つメソッドにScalaからArrayで値を渡したい場合は、Array型の引数arrayの後ろに```array : _*```のように型アノテーションをつける必要があります。
Scalaの可変長引数を持つメソッドをJavaから使用する場合はJavaで使用するときと同様に配列で値を渡せます。
***
<h3>コラム：コレクションに関するJava/Scala互換性</h3>
ScalaからJavaのコレクションを呼び出したいとき、もしくはJavaからScalaのコレクションを呼び出したいとき、<a href="http://www.scala-lang.org/api/current/index.html#scala.collection.JavaConversions$" target="_blank">scala.collection.JavaConversions</a>を使用することでScalaとJavaのコレクションの相互変換のメソッドが置かれています。<br>
参考文献：<br>
<a href="http://docs.scala-lang.org/ja/overviews/collections/conversions-between-java-and-scala-collections.html" target="_blank">Java と Scala 間のコレクションの変換</a>
