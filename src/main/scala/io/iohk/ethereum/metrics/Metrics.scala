package io.iohk.ethereum.metrics

import java.util.concurrent.ConcurrentMap

import com.google.common.collect.Maps

object Metrics {
  /**
   * Signifies that Mantis has started.
   */
  final val StartEvent = "start.event"

  /**
   * Signifies that Mantis has stopped.
   */
  final val StopEvent = "stop.event"

  /**
   * Measures the block number of the last imported block in the Ledger.
   */
  final val LedgerImportBlockNumber = "ledger.import.block.number"

  /**
   * Total number of transactions imported in the Ledger.
   */
  final val LedgerImportTotalTransactionsNumber = "ledger.import.total.transactions.number"

  /**
   * Counts the rate at which transactions are imported in the Ledger.
   */
  final val LedgerImportTransactionsCounter = "ledger.import.transactions.counter"

  /**
   * Signifies a leadership change. This is emitted from the new leader.
   */
  final val RaftLeaderEvent = "raft.leader.event"

  /**
   * How many times this node became a leader
   */
  final val RaftLeadershipsNumber = "raft.leaderships.number"

  /**
   * How many blocks forged by the leader.
   */
  final val RaftLeaderForgedBlocksNumber = "raft.leader.forged.blocks.number"

  /**
   * How many JSON-RPC endpoint calls have come our way.
   */
  final val JsonRpcEndpointTotalCallsNumber = "json.rpc.endpoint.total.calls.number"

  /**
   * How many JSON-RPC endpoint calls had a successful response.
   */
  final val JsonRpcEndpointSuccessCallsNumber = "json.rpc.endpoint.success.calls.number"

  /**
   * Ratio of `number of JSON-RPC endpoint calls that had a successful response` to `total number of JSON-RPC endpoint calls`.
   */
  final val JsonRpcEndpointSuccessCallsRatio = "json.rpc.endpoint.success.calls.ratio"

  /**
   * Counts the rate at which the JSON RPC endpoint is called.
   */
  final val JsonRpcEndpointCounter = "json.rpc.endpoint.counter"

  // These are not metrics per se but are used in order to dynamically create metric names.
  object Fragment {
    private[this] type MethodName = String
    private[this] type MetricName = String
    private[this] type MethodMetricMap = ConcurrentMap[MethodName, MetricName]

    @inline
    private[this] def getMetric(map: MethodMetricMap, prefix: String, suffix: String, key: String): String = {
      map.get(key) match {
        case null ⇒
          val metric = prefix + key + suffix
          map.putIfAbsent(key, metric)
          metric

        case metric ⇒
          metric
      }
    }

    private[this] def newMetricMap: ConcurrentMap[String, String] = Maps.newConcurrentMap[String, String]()

    private[this] final val MethodCounterMap = newMetricMap
    private[this] final val JsonRpcMethodCounterPrefix = "json.rpc.method."
    private[this] final val JsonRpcMethodCounterSuffix = ".counter"

    /**
     * Creates the metric (name) that:
     *    counts the rate at which a specific endpoint method is called.
     */
    def mkJsonRpcMethodCounter(method: String): String =
      getMetric(
        map = MethodCounterMap,
        prefix = JsonRpcMethodCounterPrefix,
        suffix = JsonRpcMethodCounterSuffix,
        key = method
      )

    private[this] final val MethodTotalCallsNumberMap = newMetricMap
    private[this] final val JsonRpcMethodTotalCallsNumberPrefix = "json.rpc.method."
    private[this] final val JsonRpcMethodTotalCallsNumberSuffix = ".total.calls.number"

    /**
     * Creates the metric (name) that:
     *    counts the number of times a specific endpoint method is called.
     */
    def mkJsonRpcMethodTotalCallsNumber(method: String): String =
      getMetric(
        map = MethodTotalCallsNumberMap,
        prefix = JsonRpcMethodTotalCallsNumberPrefix,
        suffix = JsonRpcMethodTotalCallsNumberSuffix,
        key = method
      )

    private[this] final val MethodSuccessfulCallsNumberMap = newMetricMap
    private[this] final val JsonRpcMethodSuccessCallsNumberPrefix = "json.rpc.method."
    private[this] final val JsonRpcMethodSuccessCallsNumberSuffix = ".success.calls.number"

    /**
     * Creates the metric (name) that:
     *    counts the number of times a specific endpoint method returns successfully.
     */
    def mkJsonRpcMethodSuccessfulCallsNumber(method: String): String =
      getMetric(
        map = MethodSuccessfulCallsNumberMap,
        prefix = JsonRpcMethodSuccessCallsNumberPrefix,
        suffix = JsonRpcMethodSuccessCallsNumberSuffix,
        key = method
      )

  }
}
