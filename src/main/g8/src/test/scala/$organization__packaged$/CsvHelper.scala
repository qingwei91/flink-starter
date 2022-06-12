package $organization$

import com.github.tototoshi.csv.CSVReader
import org.apache.flink.api.common.serialization.Encoder
import org.apache.flink.connector.file.sink.FileSink
import org.apache.flink.core.fs.{Path => FlinkPath}
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.BasePathBucketAssigner

import java.nio.file.{Files, Path}
import scala.collection.JavaConverters._

object CsvHelpers {
  def readAllFilesIn(dir: Path): List[List[String]] = {
    val allOutputFiles = Files
      .walk(dir)
      .iterator()
      .asScala
      .filter(path => Files.isRegularFile(path))

    var data = List.empty[List[String]]

    for (f <- allOutputFiles) {
      val reader = CSVReader.open(f.toFile)
      try {
        data = data ++ reader.all()
      } finally {
        reader.close()
      }
    }

    data
  }

  def sinkBackedByTempFile[T](encoder: Encoder[T]): (Path, FileSink[T]) = {
    val tempDir = Files.createTempDirectory(null)

    val csvSink =
      FileSink
        .forRowFormat[T](FlinkPath.fromLocalFile(tempDir.toFile), encoder)
        .withBucketAssigner(new BasePathBucketAssigner[T]())
        .build()
    tempDir -> csvSink
  }
}
