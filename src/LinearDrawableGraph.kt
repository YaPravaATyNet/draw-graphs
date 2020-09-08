abstract class LinearDrawableGraph(directed: Boolean) : Graph(directed), DrawableGraph {
    //TODO: non-directed
    protected var startIndent = 6
    protected var factor = 3
    protected var fixed = false
    protected var offset = 0
    protected var edgeView: EdgeView = EdgeViewSimple
    protected var nodeView: NodeView = NodeViewBrackets
    
    private val linesNum = mutableMapOf<Node, Pair<Int, Int>>()
    protected val lines = mutableListOf<String>()
    protected var maxLineLength = 0   //от этого зависит идекс первого ребра с правой стороны

    private val freeDownEdges = mutableListOf<Int>()
    private val freeUpEdges = mutableListOf<Int>()
    
    override fun draw(): String {
        init()
        for (node in nodes) {
            for (child in node.children) {
                if (child.num() < node.num()) {
                    freeUpEdge(child.num())
                } else {
                    drawEdge(node, child)
                }
            }
            for (parent in node.parents) {
                if (parent.num() + 1 < node.num()) {
                    freeDownEdge(parent.num())
                }
                if (node.num() < parent.num()) {
                    drawEdge(parent, node)
                }
            }
        }
        return lines.subList(0, lines.size - 2).joinToString("\n")
    }
    
    private fun init() {
        //TODO do for non-fixed
        val maxBalance = maxBalance()
        offset += if (maxBalance == 0) 0 else maxBalance * factor + startIndent
        fillLines()
    }
    
    private fun maxBalance(): Int {
        var balance = 0
        var maxBalance = 0
        for (node in nodes) {
            for (parent in node.parents) {
                if (parent.num() > node.num()) {
                    balance++
                }
            }
            if (balance > maxBalance) {
                maxBalance = balance
            }
            for (child in node.children) {
                if (child.num() < node.num()) {
                    balance--
                }
            }
        }
        return maxBalance
    }
    
    private fun fillLines() {
        //TODO manyline nodes
        val indent = " ".repeat(offset)
        for (node in nodes) {
            val text = nodeView.LEFT_BORDER + node.text + nodeView.RIGHT_BORDER
            if (text.length > maxLineLength) {
                maxLineLength = text.length
            }
            lines.add(indent + text)
            linesNum[node] = Pair(lines.size - 1, lines.size - 1)
            lines.add(indent)
            lines.add(indent)
        }
    }
    
    abstract fun drawEdge(parent: Node, child: Node)

    protected fun findFreeDownEdge(num: Int): Int {
        return findFreeEdge(num, freeDownEdges)
    }

    protected fun findFreeUpEdge(num: Int): Int {
        return findFreeEdge(num, freeUpEdges)
    }

    private fun findFreeEdge(num: Int, freeEdges: MutableList<Int>): Int {
        for (i in 0 until freeEdges.size) {
            if (freeEdges[i] == -1) {
                freeEdges[i] = num
                return i
            }
        }
        freeEdges.add(num)
        return freeEdges.size - 1
    }

    private fun freeDownEdge(num: Int) {
        freeEdge(num, freeDownEdges)
    }

    private fun freeUpEdge(num: Int) {
        freeEdge(num, freeUpEdges)
    }

    private fun freeEdge(num: Int, freeEdges: MutableList<Int>) {
        for (i in 0 until freeEdges.size) {
            if (freeEdges[i] == num) {
                freeEdges[i] = -1
            }
        }
    }
    
    fun Node.linesNum(): Pair<Int, Int>? {
        return linesNum[this]
    }
}

//directed или нет
//view Node
//view Edge
//type graph (vert, hor, matrix)
//indent between line for (vert & hor) - factor 
//indent left and up - startIndent
//уже отсорчено или нет 