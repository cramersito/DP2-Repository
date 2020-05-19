package rendimiento

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class TransportesAcabados extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.png"""), WhiteList())
		.acceptEncodingHeader("gzip, deflate")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "*/*",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"A-IM" -> "x-bm,gzip",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Accept-Language" -> "es-ES,es;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")


	val scn = scenario("Transportar")
		.exec(http("Home")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/")
			.headers(headers_1)))
		.pause(3)
		// Home
		.exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_4")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			.headers(headers_1)))
		.pause(13)
		// Login
		.exec(http("Authenticated")
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
		// Authenticated
		.exec(http("TransportesAcabados")
			.get("/transport/transporteds")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/transport/transporteds")
			.headers(headers_1)))
		.pause(7)
		// TransportesAcabados

		
	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}