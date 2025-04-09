import org.http4k.client.JettyClient
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test;
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SeeATodoListAT {
    @Test
    fun `List owners can see their lists`() {
        val user = "frank"
        val listName = "shopping"
        val foodToBuy = listOf("carrots", "apples", "milk")
        startTheApplication(user, listName, foodToBuy)
        val list = getToDoList(user, listName)
        expectThat(list.listName.name).isEqualTo(listName)
        expectThat(list.items.map { it.description }).isEqualTo(foodToBuy)
    }
}

fun getToDoList(user: String, listName: String): ToDoList {
    val client = JettyClient()
    val request = Request(
        Method.GET,
        "http://localhost:8080/todo/$user/$listName"
    )
    val response = client(request)
    return if (response.status == Status.OK)
        parseResponse(response.bodyString())
    else
        fail(response.toMessage())
}

fun parseResponse(html: String): ToDoList = TODO("parse the response")
fun startTheApplication(
        user: String, listName: String, items: List<String>) {
    val server = Zettai().asServer(Jetty(8081))
}

class Zettai(): HttpHandler {
    val routes = routes(
        "/todo/{user}/{list}" bind Method.GET to ::showList
    )
    override fun invoke(request: Request): Response = routes(request)
    private fun showList(req: Request): Response {
        val user = req.path("user").orEmpty()
        val list = req.path("list").orEmpty()
        val htmlPage = """
<html>
    <body>
    <h1>Zettai</h1>
    <p>Here is the list <b>$list</b> of user <b>$user</b></p>
    </body>
</html>"""
        return Response(Status.OK).body(htmlPage)
    }
}

data class ToDoList(val listName: ListName, val items: List<ToDoItem>)
data class ListName(val name: String)
data class User(val name: String)
data class ToDoItem(val description: String)
enum class ToDoStatus{ Todo, InProgress, Done, Blocked }

