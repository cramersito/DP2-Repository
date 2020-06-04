package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DosEscenarios2 extends Simulation {

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
		.pause(7)
		
		}
		
	object IniciarSesion{
		val iniciarSesion = exec(http("IniciarSesion")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(38)
	
	
	} 
	
	object IniciadoClerk{
		val iniciadoClerk = exec(http("IniciadoClerk")
			.post("/login")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}")
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX")
			.formParam("username", "clerk1")
			.formParam("password", "clerk1"))
		.pause(7)
	
	}
	
	
	
	object CrearCertificado{
		val crearCertificado = exec(http("CrearCertificado")
			.get("/certificate/create")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(35)
	
	
	}
	
	object CertificadoCreado{
		val certificadoCreado = exec(http("CrearCertificado")
			.post("/certificate/create")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}")
			.formParam("id", "0")
			.formParam("version", "0")
			.formParam("entity", "testing")
			.formParam("description", "testing"))
		.pause(29)
		
	
	}
	
	object CursoCreado{
		val cursoCreado = exec(http("CursoCreado")
			.post("/course/createCourse")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}"	)
			.formParam("id", "0")
			.formParam("version", "0")
			.formParam("clerk", "200")
			.formParam("certificate", "6122")
			.formParam("description", "testing")
			.formParam("price", "12")
			.formParam("startDate", "15/06/2020")
			.formParam("endDate", "30/06/2020"))
		.pause(12)
	
	
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
			.formParam("_csrf", "${stoken}"	)
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX"))
		.pause(18)
		
	}
	
	object IniciadoOwner{
		val iniciadoOwner = exec(http("IniciadoOwner")
			.post("/login")
			.headers(headers_2)
			.formParam("_csrf", "${stoken}"	)
			.formParam("email", "yzwE9/RxW/SrFWAwa6ssgZr6+DuITA0+Wvdgy1uHeqplluC2DtDn3OYClUUkKKI3X93iTkQlejwn26F2CmL/oq8aUp6aphezebgC2mHt1WuO3YpumP+xuLg4W7mgOxeX")
			.formParam("username", "owner2")
			.formParam("password", "owner2"))
		.pause(4)
	
	}
	
	
	object CursosDisponibles{
		val cursosDisponibles = exec(http("CursosDisponibles")
			.get("/course/enroll/list")
			.headers(headers_0))
		.pause(9)
	
	}
	
	
	val publicarCurso = scenario("Publicar").exec(Home.home,
											IniciarSesion.iniciarSesion,
											IniciadoClerk.iniciadoClerk,
											CrearCertificado.crearCertificado,
											CertificadoCreado.certificadoCreado,
											CursoCreado.cursoCreado,
											CerrarSesion.cerrarSesion,
											CerradoSesion.cerradoSesion)
	
	val verCursos = scenario("ListadoCursos").exec(Home.home,
												IniciarSesion.iniciarSesion,
												IniciadoOwner.iniciadoOwner,
												CursosDisponibles.cursosDisponibles,
												CerrarSesion.cerrarSesion,
												CerradoSesion.cerradoSesion)
	
	
	
	setUp(
		publicarCurso.inject(rampUsers(550) during (100 seconds)),
		verCursos.inject(rampUsers(550) during (100 seconds))
	).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),    
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
     )
}