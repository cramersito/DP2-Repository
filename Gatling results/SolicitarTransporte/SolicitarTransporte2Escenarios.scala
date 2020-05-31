package rendimiento

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class SolicitarTransporteMaxRendPerfomance extends Simulation {

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




object AuthenticatedAsOwner {
		val authenticated = exec(http("AuthenticatedAsOwner")
      .post("/login")
      .headers(headers_4)
      .formParam("_csrf", "${stoken}")
      .formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX")
      .formParam("username", "owner1")
      .formParam("password", "owner1")
      .resources(http("request_5")
      .get("/home")
      .headers(headers_1)))
    .pause(12)
    

}

object MyPets {
		val myPets = exec(http("MyPets")
      .get("/pet/my-pets")
      .headers(headers_0)
      .resources(http("request_7")
      .get("/pet/my-pets")
      .headers(headers_1)))
    .pause(8)

}


object SolicitarTransporteForm {
		val solicitarTransporteForm = exec(http("SolicitarTransporteForm")
      .get("/transport/create")
      .headers(headers_0)
      .resources(http("request_9")
      .get("/transport/create")
      .check(css("input[name=_csrf]", "value").saveAs("stoken"))
      .headers(headers_1)))
    .pause(21)

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


object PostSolicitarTransportePos {
		val postSolicitarTransportePos =  exec(http("PostSolicitarTransportePos")
      .post("/transport/edit/solicitarTransporte")
      .headers(headers_4)
      .formParam("_csrf", "${stoken}")
      .formParam("id", "0")
      .formParam("version", "0")
      .formParam("status", "PENDING")
      .formParam("origin", "SITIO3")
      .formParam("destination", "Carhmona")
      .formParam("mascotas", "80")
      .resources(http("request_11")
      .get("/pet/my-pets")
      .headers(headers_1)))
    .pause(8)

}

object PostSolicitarTransporteNeg {
		val postSolicitarTransporteNeg =  exec(http("PostSolicitarTransporteNeg")
      .post("/transport/edit/solicitarTransporte")
      .headers(headers_4)
      .formParam("_csrf", "${stoken}")
      .formParam("id", "0")
      .formParam("version", "0")
      .formParam("status", "PENDING")
      .formParam("origin", "")
      .formParam("destination", "")
      .formParam("mascotas", "80")
      .resources(http("request_11")
      .get("/pet/my-pets")
      .headers(headers_1)))
    .pause(8)

}

   val solicitarTransportePos = scenario("solicitarTransportePos").exec(Home.home,
									  Login.login,
                    AuthenticatedAsOwner.authenticated,
                    MyPets.myPets,
                    SolicitarTransporteForm.solicitarTransporteForm,
                    PostSolicitarTransportePos.postSolicitarTransportePos,
                    CierreSesion.cierreSesion
									 )
    
   val solicitarTransporteNeg = scenario("solicitarTransporteNeg").exec(Home.home,
									  Login.login,
                    AuthenticatedAsOwner.authenticated,
                    MyPets.myPets,
                    SolicitarTransporteForm.solicitarTransporteForm,
                    PostSolicitarTransporteNeg.postSolicitarTransporteNeg,
                    CierreSesion.cierreSesion
									 )

   setUp(
  solicitarTransportePos.inject(rampUsers(280) during (100 seconds)),
  solicitarTransporteNeg.inject(rampUsers(280) during (100 seconds))
   ).protocols(httpProtocol)
  .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )


}