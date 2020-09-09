interface Graph {
    fun addNode(node: Node)
    fun addNode(text: String): Node
    fun addEdge(parent: Node, child: Node)
    fun addEdge(parent: Int, child: Int): Boolean
}