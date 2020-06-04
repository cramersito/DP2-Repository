package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DosEscenarios extends Simulation {

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
	
	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}

	object IniciarSesion{
		val iniciarSesion = exec(http("IniciarSesion")
			.get("/login")			
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(22)
	
	}
	
	
	object IniciadoOwner{
		val iniciadoOwner = exec(http("IniciadoOwner")
			.post("/login")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}")
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX")
			.formParam("username", "owner2")
			.formParam("password", "owner2"))
		.pause(10)
	
	}

	object VerCursos{
		val verCursos = exec(http("VerCursos")
			.get("/course/enroll/list")
			.headers(headers_0))
		.pause(12)
	
	}
	
	object Inscrito{
		val inscrito = exec(http("Inscrito")
			.get("/owner/enroll?courseId=401")
			.headers(headers_0))
		.pause(17)	
	
	}
	
	object CerrarSesion{
		val cerrarSesion = exec(http("CerrarSesion")
			.get("/logout")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
	
	
	}
	
	object CerradoSesion{
		val cerradoSesion = exec(http("CerradoSesion")
			.post("/logout")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}")
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX"))
	
	}
	
	val ListadoCursos = scenario("Listado").exec(Home.home,
											IniciarSesion.iniciarSesion,
											IniciadoOwner.iniciadoOwner,
											VerCursos.verCursos,
											CerrarSesion.cerrarSesion,
											CerradoSesion.cerradoSesion)
											
	val InscribirseCurso = scenario("Inscribirse").exec(Home.home,
													IniciarSesion.iniciarSesion,
													IniciadoOwner.iniciadoOwner,
													VerCursos.verCursos,
													Inscrito.inscrito,
													CerrarSesion.cerrarSesion,
													CerradoSesion.cerradoSesion)

	
	setUp(
		ListadoCursos.inject(rampUsers(650) during (100 seconds)),
		InscribirseCurso.inject(rampUsers(650) during (100 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}