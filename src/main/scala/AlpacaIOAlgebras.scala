import AlpacaAlgebras.AccountAlg
import cats.effect.{ContextShift, IO}
import io.circe.generic.AutoDerivation
import model.account.Account
import org.http4s.client.blaze._
import org.http4s.{Header, Headers, Request, Uri}

import scala.concurrent.ExecutionContext.Implicits.global


package object AlpacaIOAlgebras extends AutoDerivation {

  private implicit val cs: ContextShift[IO] = IO.contextShift(global)


  private val baseURI = Uri(path = "https://api.alpaca.markets")


  class IOAccountAlg extends AccountAlg[IO] {
    override def getAccount: IO[Account] = BlazeClientBuilder[IO](global).resource.use { client =>
      val req = Request[IO](
        uri = baseURI / "/v1/account",
        headers = Headers(
          Header("APCA-API-KEY-ID", ""),
          Header("APCA-API-SECRET-KEY", "")
        )
      )
      client.expect[Account](req)
    }

    def apply: IOAccountAlg = new IOAccountAlg()
  }


}
