package rendimiento

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Transportar2EscenariosPerfomance extends Simulation {

  val httpProtocol = http
    .baseUrl("http://www.dp2.com")
	.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.png"""), WhiteList())
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("es-ES,es;q=0.9")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

  val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
    "Proxy-Connection" -> "keep-alive",
    "Upgrade-Insecure-Requests" -> "1")

  val headers_1 = Map("Proxy-Connection" -> "keep-alive")

  val headers_4 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
    "Origin" -> "http://www.dp2.com",
    "Proxy-Connection" -> "keep-alive",
    "Upgrade-Insecure-Requests" -> "1")


    val headers_2 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Home {
		val home = exec(http("Home")
      .get("/")
      .headers(headers_0)
      .resources(http("request_1")
      .get("/")
      .headers(headers_1)))
    .pause(5)

  }
object Login {
		val login = exec(http("Login")
      .get("/login")
      .headers(headers_0)
      .resources(http("request_3")
      .get("/login")
      .check(css("input[name=_csrf]", "value").saveAs("stoken"))
      .headers(headers_1)))
    .pause(17)

}




object AuthenticatedAsClerk {
		val authenticated = exec(http("Authenticated")
			.post("/login")
			.headers(headers_5)
			.formParam("_csrf", "${stoken}")
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX")
			.formParam("username", "clerk1")
			.formParam("password", "clerk1")
			.resources(http("request_6")
			.get("/home")
			.headers(headers_1)))
		.pause(20)
    

}
object TransportesPendientes {
		val transportesPendientes = exec(http("TransportesPendientes")
			.get("/transport/list")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/transport/list")
			.headers(headers_1)))
		.pause(7)
    

}

object TransportarForm {
		val transportarForm =		exec(http("TransportarForm")
			.get("/transport/edit?transportId=503")
			.headers(headers_0)
			.resources(http("request_10")
			.get("/transport/edit?transportId=503")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			.headers(headers_1)))
		.pause(21)
    

}
object Transportado {
		val transportado =		exec(http("Transportado")
			.post("/transport/edit/transportar")
			.headers(headers_5)
			.formParam("_csrf", "${stoken}")
			.formParam("id", "503")
			.formParam("version", "0")
			.formParam("status", "TRANSPORTED")
			.formParam("origin", "Sevilla")
			.formParam("destination", "Carmona")
			.formParam("company", "SEUR")
			.resources(http("request_12")
			.get("/transport/list")
			.headers(headers_1)))
		.pause(9)
    

}


object TransportarNeg {
		val transportarNeg =		exec(http("transportarNeg")
			.post("/transport/edit/transportar")
			.headers(headers_5)
			.formParam("_csrf", "${stoken}")
			.formParam("id", "503")
			.formParam("version", "0")
			.formParam("status", "TRANSPORTED")
			.formParam("origin", "Sevilla")
			.formParam("destination", "Carmona")
			.formParam("company", "")
			.resources(http("request_12")
			.get("/transport/list")
			.headers(headers_1)))
		.pause(9)
    

}

object CierreSesion {
		val cierreSesion =		exec(http("CierreSesion")
			.post("/logout")
			.headers(headers_4)
			.formParam("_csrf", "${stoken}")
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX")
			.resources(http("request_15")
			.get("/home")
			.headers(headers_1)))
		.pause(5)
}

   val transportarPos = scenario("transportarPos").exec(Home.home,
									  Login.login,
                    AuthenticatedAsClerk.authenticated,
                    TransportesPendientes.transportesPendientes,
                    TransportarForm.transportarForm,
                    Transportado.transportado,
                    CierreSesion.cierreSesion
									 )
    val transportarNeg = scenario("transportarNeg").exec(Home.home,
									  Login.login,
                    AuthenticatedAsClerk.authenticated,
                    TransportesPendientes.transportesPendientes,
                    TransportarForm.transportarForm,
                    TransportarNeg.transportarNeg,
                    CierreSesion.cierreSesion
									 )
  setUp(
  transportarNeg.inject(rampUsers(280) during (100 seconds)),
  transportarPos.inject(rampUsers(280) during (100 seconds))
  ).protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}