package $organization$

import org.apache.flink.streaming.api.scala._

object $name;format="Camel"$ {
  def main(args: Array[String]): Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    /**
     * Your stream here...
     */
    env.execute()
  }
}