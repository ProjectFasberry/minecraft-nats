import io.nats.client.Connection
import java.nio.charset.StandardCharsets

object NatsHelper {
  fun reply(conn: Connection, replyTo: String?, response: String) {
    conn.publish(replyTo, response.toByteArray(StandardCharsets.UTF_8))
  }

  fun publish(conn: Connection, publishTo: String?, response: String) {
    conn.publish(publishTo, response.toByteArray(StandardCharsets.UTF_8))
  }
}