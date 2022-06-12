package $organization$

import org.apache.flink.test.util.MiniClusterWithClientResource
import weaver._
import cats.effect.{IO, Resource}
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration

trait FlinkLocalCluster extends IOSuite {
  override type Res = MiniClusterWithClientResource
  override def sharedResource : Resource[IO, Res] = Resource.make(
    IO {
      val cluster = new MiniClusterWithClientResource(
        new MiniClusterResourceConfiguration.Builder()
          .setNumberSlotsPerTaskManager(2)
          .setNumberTaskManagers(1)
          .build())
      cluster.before()
      cluster
    }
  )(client => IO(client.after()))
}