class VerticalDrawableGraph(directed: Boolean) : LinearDrawableGraph(directed) {
    override fun drawEdge(parent: Node, child: Node) {
        val start = parent.num()
        val end = child.num()
        when {
            start == end -> drawSelfLoop(parent)
            end - 1 == start -> drawEdgeToNextNode(parent)
            start < end -> drawDownEdge(parent, child)
            end < start -> drawUpEdge(parent, child)
        }
    }
    
    fun drawSelfLoop(node: Node) {
        
    }
    
    fun drawEdgeToNextNode(parent: Node) {
        val endNode = parent.linesNum()?.second ?: return
        lines[endNode + 1] = addSymbol(lines[endNode + 1], EdgeView.V_BAR, offset + 1)
        lines[endNode + 2] = addSymbol(lines[endNode + 2], EdgeView.ARROW_DOWN, offset + 1)
    }
    
    fun drawDownEdge(parent: Node, child: Node) {
        val freeEdgeNum = findFreeDownEdge(parent.num())
        val indexEdge = maxLineLength - 1 + (freeEdgeNum + 1) * factor
        val (startParent, endParent) = parent.linesNum() ?: return
        var startLine = (startParent + endParent) / 2 + 1
        var originalStartLineLength = originalLength(parent)
        if (startParent == endParent) {
            startLine = startParent
            if (isOtherArrow(parent)) {
                startLine++
                lines[endParent + 1] = addSymbol(lines[endParent + 1], EdgeView.UP_RIGHT, originalLength(parent) - 1)
                originalStartLineLength--
            }
        }
        lines[startLine] = addOutDownEdge(lines[startLine], indexEdge, originalStartLineLength)
//        lines[endLine] = addInDownEdge(lines[endLine], indexEdge, endParent)
//        for (i in startLine + 1 until endLine) {
//            lines[i] = addSymbol(lines[i], '|', indexEdge)
//        }
    }
    
    fun drawUpEdge(parent: Node, child: Node) {
        
    }

    private fun addOutDownEdge(line: String, indexEdge: Int, originalLength: Int): String {
        val length = line.length
        if (length < indexEdge) {
            if (length == originalLength) {
                return line + EdgeView.H_BAR.toString().repeat(indexEdge - length) + EdgeView.DOWN_LEFT
            }
            return line.substring(0, originalLength) + 
                    line.substring(originalLength, length).replace(' ', EdgeView.H_BAR).replace(EdgeView.DOWN_LEFT, EdgeView.H_DOWN) +
                    EdgeView.H_BAR.toString().repeat(indexEdge - length) + EdgeView.DOWN_LEFT
        }
        val symbol = if (line[indexEdge] == EdgeView.H_BAR) EdgeView.H_DOWN else EdgeView.DOWN_LEFT
        return line.substring(0, originalLength) + line.substring(originalLength, indexEdge).replace(' ', EdgeView.H_BAR) + symbol + line.substring(indexEdge + 1)
    }

    private fun addSymbol(line: String, symbol: Char, index: Int): String {
        if (line.length <= index) {
            return line + " ".repeat(index - line.length) + symbol
        }
        return line.substring(0, index) + symbol + line.substring(index + 1, line.length)
    }
    
    private fun isOtherArrow(node: Node): Boolean {
        val parents = node.parents
        //only arrow from previous node
        if (parents.size == 1 && node.num() > 0 && parents.contains(nodes[node.num() - 1])) {
            return false
        }
        return parents.isNotEmpty()
    }
    
    private fun originalLength(node: Node): Int {
        //TODO add multilines
        return node.text.length + 2 + offset
    }
}
