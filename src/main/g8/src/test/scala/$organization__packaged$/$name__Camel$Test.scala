package $organization$

import cats.effect._
import org.apache.flink.api.common.serialization.SimpleStringEncoder

import org.apache.flink.streaming.api.scala._

object $name;format="Camel"$Test extends FlinkLocalCluster {
  test("Example flink job test") { cluster =>
    IO {
      val env = StreamExecutionEnvironment.getExecutionEnvironment

      val (tempDir, csvSink) = CsvHelpers.sinkBackedByTempFile(new SimpleStringEncoder[String]())

      env
        .fromElements(10, 20, 23, 10)
        .map(_ + 1)
        .map(_.toString)
        .sinkTo(csvSink)

      env.execute()

      val result = CsvHelpers.readAllFilesIn(tempDir)
      assert(result.size == 4)
    }
  }

}