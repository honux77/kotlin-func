package honux

import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

val html = """
<html>
    <body><h1>Hello Functional Web!</h1></body>
</html>"""

val logger = LoggerFactory.getLogger("MainKt")

fun showList(req: Request): Response {
    val user: String? = req.path("user")
    val list: String? = req.path("list")
    val htmlPage = """
        <html>
            <body>
            <h1>Zettai</h1>
            <p>Here is the list <b>$list</b> of <b>$user</b></p>
            </body>
        </html>
        """.trimIndent()
    return Response(Status.OK).body(htmlPage)
}

fun main() {
    logger.info("Starting Zettai server on port 8080")
    val app:HttpHandler = routes(
        "/todo/{user}/{list}" bind Method.GET to ::showList
    )
    app.asServer(Jetty(8080)).start()
}