package model

import java.time.Instant

import io.circe.Decoder.Result
import io.circe.{Decoder, HCursor}

case class Account(
  id:                   String,
  status:               AccountStatus,
  currency:             String,
  buyingPower:          Double,
  cash:                 Double,
  cashWithdrawable:     Double,
  portfolioValue:       Double,
  patternDayTrader:     Boolean,
  tradingBlocked:       Boolean,
  transfersBlocked:     Boolean,
  accountBlocked:       Boolean,
  createdAt:            Instant,
  tradeSuspendedByUser: Boolean
)

object Account {

  implicit val decodeAccount: Decoder[Account] = new Decoder[Account] {
    override def apply(c: HCursor): Result[Account] = for {
      id                   <- c.downField("id")                     .as[String]
      status               <- c.downField("status")                 .as[AccountStatus]
      currency             <- c.downField("currency")               .as[String]
      buyingPower          <- c.downField("buying_power")           .as[String].map { _.toDouble }
      cash                 <- c.downField("cash")                   .as[String].map { _.toDouble }
      cashWithdrawable     <- c.downField("cash_withdrawable")      .as[String].map { _.toDouble }
      portfolioValue       <- c.downField("portfolio_value")        .as[String].map { _.toDouble }
      patternDayTrader     <- c.downField("pattern_day_trader")     .as[Boolean]
      tradingBlocked       <- c.downField("trading_blocked")        .as[Boolean]
      transfersBlocked     <- c.downField("transfers_blocked")      .as[Boolean]
      accountBlocked       <- c.downField("account_blocked")        .as[Boolean]
      createdAt            <- c.downField("created_at")             .as[String].map { Instant.parse }
      tradeSuspendedByUser <- c.downField("trade_suspended_by_user").as[Boolean]
    } yield Account(
      id, status, currency,  buyingPower, cash, cashWithdrawable, portfolioValue,
      patternDayTrader, tradingBlocked, transfersBlocked, accountBlocked, createdAt, tradeSuspendedByUser
    )
  }
}

