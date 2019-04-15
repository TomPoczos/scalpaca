package model

import java.time.LocalDateTime

import cats.effect.IO
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

case class Account(
                    id: String,
                    status: AccountStatus,
                    currency: String,
                    buyingPower: Double,
                    cash: Double,
                    cashWithdrawable: Double,
                    portfolioValue: Double,
                    patternDayTrader: Boolean,
                    tradingBlocked: Boolean,
                    transfersBlocked: Boolean,
                    accountBlocked: Boolean,
                    createdAt: LocalDateTime
                  )

object Account {
  implicit val AccountDecoder: EntityDecoder[IO, Account] = jsonOf
}

