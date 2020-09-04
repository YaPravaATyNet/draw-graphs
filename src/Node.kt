open class Node(val text: String) {
    val parents = mutableSetOf<Node>()
    val children = mutableSetOf<Node>()
    
    fun addParent(node: Node) {
        parents.add(node)
    }
    
    fun addParent(text: String): Node {
        val node = Node(text)
        parents.add(node)
        return node
    }
    
    fun addChild(node: Node) {
        children.add(node)
    }
    
    fun addChild(text: String): Node {
        val node = Node(text)
        children.add(node)
        return node
    }

    override fun toString(): String {
        return text
    }
}