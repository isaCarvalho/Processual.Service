import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Assert
import org.junit.Test

class ApplicationTest {

    @Test
    fun home() {
        withTestApplication(Application::mainModule) {
            val call = handleRequest(HttpMethod.Get, "")
            Assert.assertEquals(HttpStatusCode.OK, call.response.status())
        }
    }
}