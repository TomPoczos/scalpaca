package model

import java.time.LocalDateTime

package object order {



  sealed class OrderType
  case object MarketOrder extends OrderType
  case object LimitOrder extends OrderType
  case object StopOrder extends OrderType
  case object StopLimitOrder extends OrderType

  sealed class OrderSide
  case object Buy extends OrderSide
  case object Sell extends OrderSide

  sealed class TimeInForce
  case object Day extends TimeInForce
  case object GTC extends TimeInForce
  case object OPG extends TimeInForce

  sealed class OrderStatus
  case object New extends OrderStatus
  case object PartiallyFilled extends OrderStatus
  case object Filled extends OrderStatus
  case object DoneForDay extends OrderStatus
  case object Cancelled extends OrderStatus
  case object Expired extends OrderStatus


  case class Order(
                    id: String,
                    client_order_id: String,
                    created_at: LocalDateTime,
                    updated_at: LocalDateTime,
                    submitted_at: LocalDateTime,
                    filled_at: LocalDateTime,
                    expired_at: LocalDateTime,
                    canceled_at: LocalDateTime,
                    failed_at: LocalDateTime,
                    asset_id: String,
                    symbol: String,
                    exchange: String,
                    asset_class: String,
                    qty: Int,
                    filled_qty: Int,
                    orderType: OrderType,
                    side: OrderSide,
                    time_in_force: TimeInForce,
                    limit_price: Option[Double],
                    stop_price: Option[Double],
                    filled_avg_price: Double,
                    status: OrderStatus
                  )

  sealed class SortDirection
  case object Asc extends SortDirection
  case object Desc extends SortDirection


  sealed class LimitOutOfBounds

  class Limit private(val theLimit: Double)

  object Limit {
    def apply(theLimit: Double): Either[LimitOutOfBounds, Limit] =
      if (theLimit < 1 || theLimit > 500) Left(new LimitOutOfBounds) else Right(new Limit(theLimit))
  }
}
