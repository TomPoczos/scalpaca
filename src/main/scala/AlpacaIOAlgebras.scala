import AlpacaAlgebras.AccountAlg
import cats.effect.{ContextShift, IO}
import model.Account
import org.http4s.client.blaze._
import org.http4s.{Header, Headers, Method, Request, Uri}

import scala.concurrent.ExecutionContext.Implicits.global


object AlpacaIOAlgebras {

  private implicit val cs: ContextShift[IO] = IO.contextShift(global)


  private val baseURI = Uri(path = "https://www.paper-api.alpaca.markets/v1/account")


  import model.AccountStatus

  class IOAccountAlg extends AccountAlg[IO] {
    override def getAccount: IO[Account] = BlazeClientBuilder[IO](global).resource.use { client =>
      val req = Request[IO](
        uri = baseURI,
//        uri = baseURI / "v1/account",
        headers = Headers(
          Header("APCA-API-KEY-ID", "PKXY07MRIZVCOS1317TP"),
          Header("APCA-API-SECRET-KEY", "q0vgOYvN2v1iV0OthncF/G8DpIk5Kx6cVItBlZFa")
        ),

        method = Method.GET

      )
      client.expect[Account](req)
    }
  }


}