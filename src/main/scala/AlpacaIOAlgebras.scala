import AlpacaAlgebras.{AccountAlg, AssetAlg}
import cats.effect.{ContextShift, IO, Resource}
import model.{Account, Asset}
import org.http4s.client.Client
import org.http4s.{Header, Headers, Http4sLiteralSyntax, HttpVersion, Method, Request}

/*
 "If you import CirceEntityCodec._, you'll get the implicit EntityDecoder from an implicit Decoder, but that's only a good
 idea if you're 100% a JSON API. You can end up with some brain teasers on things like String when you do that." - Ross A. Baker
 */

import org.http4s.circe.CirceEntityCodec._


import scala.concurrent.ExecutionContext.Implicits.global


object AlpacaIOAlgebras {

  private val baseURI = uri"https://paper-api.alpaca.markets/v2"

  private implicit val cs: ContextShift[IO] = IO.contextShift(global)

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

  class IOAccountAlg(implicit val clientResource: Resource[IO, Client[IO]]) extends AccountAlg[IO] {
    override def account: IO[Account] = clientResource.use(_.expect[Account](get("account")))
  }

  class IOAssetAlg(implicit val clientResource: Resource[IO, Client[IO]]) extends AssetAlg[IO] {
    override def assets: IO[List[Asset]] = clientResource.use(_.expect[List[Asset]](get("assets")))
  }

}