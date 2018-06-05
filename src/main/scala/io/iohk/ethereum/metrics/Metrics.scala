package io.iohk.ethereum.metrics

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
  final val JsonRpcEndpointTotalSuccessfulCallsNumber = "json.rpc.endpoint.total.successful.calls.number"

  /**
   * Ratio of `number of JSON-RPC endpoint calls that had a successful response` to `total number of JSON-RPC endpoint calls`.
   */
  final val JsonRpcEndpointSuccessfulCallsRatio = "json.rpc.endpoint.successful.calls.ratio"

  /**
   * Counts the rate at which the JSON RPC endpoint is called.
   */
  final val JsonRpcEndpointCounter = "json.rpc.endpoint.counter"

  // These are not metrics per se but are used in order to dynamically create metric names.
  object Fragment {
    final val JsonRpcMethodCounterPrefix = "json.rpc.method."
    final val JsonRpcMethodCounterSuffix = ".counter"
    def mkJsonRpcMethodCounter(method: String): String = JsonRpcMethodCounterPrefix + method + JsonRpcMethodCounterSuffix
  }

}
