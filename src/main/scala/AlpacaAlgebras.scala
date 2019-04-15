import java.time.LocalDateTime

import model.Account
import model.order._

package object AlpacaAlgebras {

  trait AccountAlg[F[_]] {
    def getAccount: F[Account]
  }




  trait OrderAlg[F[_]] {
    def getAll(status: OrderStatus, limit: Int, after: LocalDateTime, until: LocalDateTime, direction: SortDirection): F[Either[LimitOutOfBounds, Seq[Order]]]
    def getAll(                     limit: Int, after: LocalDateTime, until: LocalDateTime, direction: SortDirection): F[Either[LimitOutOfBounds, Seq[Order]]]
    def getAll(status: OrderStatus,             after: LocalDateTime, until: LocalDateTime, direction: SortDirection): F[Seq[Order]]
    def getAll(                                 after: LocalDateTime, until: LocalDateTime, direction: SortDirection): F[Seq[Order]]

//    def place(symbol: String, qty:Int, side: OrderSide, orderType: OrderType, timeInForce: TimeInForce, ): Either[]
  }

}
