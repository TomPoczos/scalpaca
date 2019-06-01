import AlpacaAlgebras.AccountAlg
import cats.effect.{ContextShift, IO, Resource}
import model.{Account, AccountStatus}
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.http4s.{EntityDecoder, Header, Headers, Http4sLiteralSyntax, HttpVersion, Request}

import scala.concurrent.ExecutionContext.Implicits.global


object AlpacaIOAlgebras {


  private val baseURI = uri"https://paper-api.alpaca.markets"

  private implicit val cs: ContextShift[IO] = IO.contextShift(global)

  private implicit val AccountEntityDecoder: EntityDecoder[IO, Account] = jsonOf
  private implicit val AccountStatusEntityDecoder: EntityDecoder[IO, AccountStatus] = jsonOf


  class IOAccountAlg(val clientResource: Resource[IO, Client[IO]]) extends AccountAlg[IO] {
    
    override def getAccount: IO[Account] = clientResource.use { client =>
      client.expect[Account](
        Request[IO](
          uri = baseURI / "v1/account",
          httpVersion = HttpVersion.`HTTP/2.0`,
          headers = Headers.of(
            Header("APCA-API-KEY-ID", Secrets.keyId),
            Header("APCA-API-SECRET-KEY", Secrets.key),
          )
        )
      )
    }
  }
}