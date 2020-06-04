package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class InscribirCurso extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")



	val scn = scenario("InscribirCurso")
		.exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(5)
		// Home
		.exec(http("IniciarSesion")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			.headers(headers_0))
		.pause(12)
		// IniciarSesion
		.exec(http("IniciadoSesion")
			.post("/login")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}")
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX")
			.formParam("username", "owner1")
			.formParam("password", "owner1"))
		.pause(10)
		// IniciadoSesion
		.exec(http("VerCursos")
			.get("/course/enroll/list")
			.headers(headers_0))
		.pause(10)
		// VerCursos
		.exec(http("MisCursosInscritos")
			.get("/owner/enroll?courseId=405")
			.headers(headers_0))
		.pause(10)
		// MisCursosInscritos

		
	setUp(scn.inject(rampUsers(200000) during (100 seconds))).protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}