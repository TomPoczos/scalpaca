package model

import cats.syntax.functor._
import io.circe.Decoder.Result
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, HCursor}

case class Asset(
  id: String,
  assetClass: String,
  exchange: Exchange,
  symbol: String,
  status: AssetStatus,
  tradable: Boolean,
  marginable: Boolean,
  shortable: Boolean,
  easyToBorrow: Boolean,
)

object Asset {
  implicit val assetDecoder: Decoder[Asset] = new Decoder[Asset] {
    override def apply(c: HCursor): Result[Asset] = for {
        id           <- c.downField("id")            .as[String]
        assetClass   <- c.downField("class")         .as[String]
        exchange     <- c.downField("exchange")      .as[Exchange]
        symbol       <- c.downField("symbol")        .as[String]
        status       <- c.downField("status")        .as[AssetStatus]
        tradable     <- c.downField("tradable")      .as[Boolean]
        marginable   <- c.downField("marginable")    .as[Boolean]
        shortable    <- c.downField("shortable")     .as[Boolean]
        easyToBorrow <- c.downField("easy_to_borrow").as[Boolean]
    } yield Asset(id, assetClass, exchange, symbol, status, tradable, marginable, shortable, easyToBorrow)
  }
}

sealed trait Exchange
case object Amex      extends Exchange
case object Arca      extends Exchange
case object Bats      extends Exchange
case object Nyse      extends Exchange
case object Nasdaq    extends Exchange
case object NyseaArca extends Exchange

object Exchange {
  implicit val exchangeEncoder: Encoder[Exchange] =
    Encoder.instance {
      exchange => exchange match {
        case Amex      => exchange.asJson
        case Arca      => exchange.asJson
        case Bats      => exchange.asJson
        case Nyse      => exchange.asJson
        case Nasdaq    => exchange.asJson
        case NyseaArca => exchange.asJson
      }
    }

  implicit val exchangeDecoder: Decoder[Exchange] =
    List[Decoder[Exchange]](
      Decoder[Amex.type].widen,
      Decoder[Arca.type].widen,
      Decoder[Bats.type].widen,
      Decoder[Nyse.type].widen,
      Decoder[Nasdaq.type].widen,
      Decoder[NyseaArca.type].widen,
    ).reduceLeft(_ or _)
}

sealed trait AssetStatus
case object ActiveAsset extends AssetStatus
case object InactiveAsset extends AssetStatus

object AssetStatus {
  implicit val assetStatusEncoder: Encoder[AssetStatus] =
    Encoder.instance {
      status => status match {
        case ActiveAsset => status.asJson
        case InactiveAsset => status.asJson
      }
    }

  implicit val assetStatusDecoder: Decoder[AssetStatus] =
    List[Decoder[AssetStatus]](
      Decoder[ActiveAsset.type].widen,
      Decoder[InactiveAsset.type].widen,
    ).reduceLeft(_ or _)
}