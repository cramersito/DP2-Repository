package rendimiento

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class SolicitarTransporteMaxRend extends Simulation {

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



  val scn = scenario("SolicitarTransporte")
    .exec(http("Home")
      .get("/")
      .headers(headers_0)
      .resources(http("request_1")
      .get("/")
      .headers(headers_1)))
    .pause(5)
    // Home
    .exec(http("Login")
      .get("/login")
      .headers(headers_0)
      .resources(http("request_3")
      .get("/login")
      .check(css("input[name=_csrf]", "value").saveAs("stoken"))
      .headers(headers_1)))
    .pause(17)
    // Login
    .exec(http("Authenticated")
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
    
    // Authenticated
    .exec(http("MyPets")
      .get("/pet/my-pets")
      .headers(headers_0)
      .resources(http("request_7")
      .get("/pet/my-pets")
      .headers(headers_1)))
    .pause(8)
    // MyPets
    .exec(http("SolicitarTransporteForm")
      .get("/transport/create")
      .headers(headers_0)
      .resources(http("request_9")
      .get("/transport/create")
      .check(css("input[name=_csrf]", "value").saveAs("stoken"))
      .headers(headers_1)))
    .pause(21)
    // SolicitarTransporteForm
    .exec(http("PostSolicitarTransporte")
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
    // PostSolicitarTransporte

   setUp(scn.inject(rampUsers(2000) during (60 seconds))).protocols(httpProtocol)
  .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )

   
 // setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}