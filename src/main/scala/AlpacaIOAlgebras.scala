import AlpacaAlgebras.{AccountAlg, AssetAlg}
import cats.effect.{ContextShift, IO, Resource}
import model.{Account, AccountStatus, Asset}
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import org.http4s.{EntityDecoder, Header, Headers, Http4sLiteralSyntax, HttpVersion, Method, Request}

import scala.concurrent.ExecutionContext.Implicits.global


object AlpacaIOAlgebras {


  private val baseURI = uri"https://paper-api.alpaca.markets/v2"

  private implicit val cs: ContextShift[IO] = IO.contextShift(global)

  private object Please {

    private def request(endpoint: String)(method: Method) = Request[IO](
      uri = baseURI / endpoint,
      httpVersion = HttpVersion.`HTTP/2.0`,
      headers = Headers.of(
        Header("APCA-API-KEY-ID", Secrets.keyId),
        Header("APCA-API-SECRET-KEY", Secrets.key),
      ),
      method = method
    )

    def get: String => Request[IO] = request(_)(Method.GET)
  }



  class IOAccountAlg(implicit val clientResource: Resource[IO, Client[IO]]) extends AccountAlg[IO] {

    private implicit val accountEntityDecoder: EntityDecoder[IO, Account] = jsonOf
    private implicit val accountStatusEntityDecoder: EntityDecoder[IO, AccountStatus] = jsonOf


    override def account: IO[Account] = clientResource.use {
      _.expect[Account](Please.get("account"))
    }
  }

  class IOAssetAlg(implicit val clientResource: Resource[IO, Client[IO]]) extends AssetAlg[IO] {

    private implicit val assetEntityDecoder: EntityDecoder[IO, List[Asset]] = jsonOf

    override def assets: IO[List[Asset]] = clientResource.use{
      _.expect[List[Asset]](Please.get("assets"))
    }
  }
}