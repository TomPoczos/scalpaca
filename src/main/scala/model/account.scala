package model

import java.time.Instant

import io.circe.Decoder.Result
import io.circe.{Decoder, HCursor}

case class Account(
  accountBlocked:                Boolean,
  buyingPower:                   Double,
  cash:                          Double,
  createdAt:                     Instant,
  currency:                      String,
  daytradeCount:                 Int,
  daytradingBuyingPower:         Double,
  equity:                        Double,
  id:                            String,
  initialMargin:                 Double,
  lastEquity:                    Double,
  lastMaintenanceMargin:         Double,
  longMarketValue:               Double,
  maintenanceMargin:             Double,
  multiplier:                    Int,
  patternDayTrader:              Boolean,
  portfolioValue:                Double,
  regtBuyingPower:               Double,
  shortMarketValue:              Double,
  shortingEnabled:               Boolean,
  specialMemorandumAccountValue: Double,
  status:                        AccountStatus,
  tradeSuspendedByUser:          Boolean,
  tradingBlocked:                Boolean,
  transfersBlocked:              Boolean,
)

object Account {

  implicit val decodeAccount: Decoder[Account] = new Decoder[Account] {
    override def apply(c: HCursor): Result[Account] = for {
      accountBlocked                <- c.downField("account_blocked")        .as[Boolean]
      buyingPower                   <- c.downField("buying_power")           .as[String].map { _.toDouble }
      cash                          <- c.downField("cash")                   .as[String].map { _.toDouble }
      createdAt                     <- c.downField("created_at")             .as[String].map { Instant.parse }
      currency                      <- c.downField("currency")               .as[String]
      daytradeCount                 <- c.downField("daytrade_count")         .as[Int]
      daytradingBuyingPower         <- c.downField("daytrading_buying_power").as[String].map { _.toDouble }
      equity                        <- c.downField("equity")                 .as[String].map { _.toDouble }
      id                            <- c.downField("id")                     .as[String]
      initialMargin                 <- c.downField("initial_margin")         .as[String].map { _.toDouble }
      lastEquity                    <- c.downField("last_equity")            .as[String].map { _.toDouble }
      lastMaintenanceMargin         <- c.downField("last_maintenance_margin").as[String].map { _.toDouble }
      longMarketValue               <- c.downField("long_market_value")      .as[String].map { _.toDouble }
      maintenanceMargin             <- c.downField("maintenance_margin")     .as[String].map { _.toDouble }
      multiplier                    <- c.downField("multiplier")             .as[String].map { _.toInt }
      patternDayTrader              <- c.downField("pattern_day_trader")     .as[Boolean]
      portfolioValue                <- c.downField("portfolio_value")        .as[String].map { _.toDouble }
      regtBuyingPower               <- c.downField("regt_buying_power")      .as[String].map { _.toDouble }
      shortMarketValue              <- c.downField("short_market_value")     .as[String].map { _.toDouble }
      shortingEnabled               <- c.downField("shorting_enabled")       .as[Boolean]
      specialMemorandumAccountValue <- c.downField("sma")                    .as[String].map { _.toDouble }
      status                        <- c.downField("status")                 .as[AccountStatus]
      tradeSuspendedByUser          <- c.downField("trade_suspended_by_user").as[Boolean]
      tradingBlocked                <- c.downField("trading_blocked")        .as[Boolean]
      transfersBlocked              <- c.downField("transfers_blocked")      .as[Boolean]
    } yield Account(
      accountBlocked, buyingPower, cash, createdAt, currency, daytradeCount, daytradingBuyingPower, equity, id,
      initialMargin, lastEquity, lastMaintenanceMargin, longMarketValue, maintenanceMargin, multiplier,
      patternDayTrader, portfolioValue, regtBuyingPower, shortMarketValue, shortingEnabled,
      specialMemorandumAccountValue, status, tradeSuspendedByUser, tradingBlocked, transfersBlocked
    )
  }
}

