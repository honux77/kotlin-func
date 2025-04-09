package honux

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.asServer
import org.http4k.server.Jetty
import org.slf4j.LoggerFactory

val html = """
<html>
    <body><h1>Hello Functional Web!</h1></body>
</html>"""

val logger = LoggerFactory.getLogger("MainKt")

val handler:HttpHandler = { Response(Status.OK).body(html)}

fun main() {
    logger.info("Starting server on port 8080")
    handler.asServer(Jetty(8080)).start()
}