import java.lang.StringBuilder

open class Graph(private val directed: Boolean) {
    protected val nodes = mutableListOf<Node>()
    private val nodesNum = mutableMapOf<Node, Int>()

    fun addNode(node: Node) {
        if (nodesNum.containsKey(node)) return
        nodesNum[node] = nodes.size
        nodes.add(node)
    }

    fun addNode(text: String): Node {
        val node = Node(text)
        addNode(node)
        return node
    }

    fun addEdge(parent: Node, child: Node) {
        parent.addChild(child)
        child.addParent(parent)
        if (!directed) {                // is this if needed?
            parent.addParent(child)
            child.addChild(parent)
        }
    }

    fun addEdge(parent: Int, child: Int): Boolean {
        if (parent < 0 || child < 0 || parent >= nodes.size || child >= nodes.size) {
            return false
        }
        addEdge(nodes[parent], nodes[child])
        return true
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (node in nodes) {
            builder.append(node.text, "${node.children}\n")
        }
        return builder.toString()
    }

    fun Node.num(): Int {
        return nodesNum[this] ?: -1
    }
}