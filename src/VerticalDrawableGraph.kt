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
        lines[endNode + 1] = addSymbol(lines[endNode + 1], edgeView.V_BAR, offset + 1)
        lines[endNode + 2] = addSymbol(lines[endNode + 2], edgeView.ARROW_DOWN, offset + 1)
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
                lines[endParent + 1] = addSymbol(lines[endParent + 1], edgeView.UP_RIGHT, originalLength(parent) - 1)
                originalStartLineLength--
            }
        }
        lines[startLine] = drawOutDownEdge(lines[startLine], indexEdge, originalStartLineLength)

        val (startChild, endChild) = child.linesNum() ?: return
        val endLine = (startChild + endChild) / 2
        val originalEndLineLength = originalLength(child)
        lines[endLine] = drawInDownEdge(lines[endLine], indexEdge, originalEndLineLength)
        for (i in startLine + 1 until endLine) {
            lines[i] = addSymbol(lines[i], edgeView.V_BAR, indexEdge)
        }
    }

    fun drawUpEdge(parent: Node, child: Node) {

    }

    private fun drawOutDownEdge(line: String, indexEdge: Int, originalLength: Int): String {
        val length = line.length
        if (length < indexEdge) {
            if (length == originalLength) {
                return line + edgeView.H_BAR.toString().repeat(indexEdge - length) + edgeView.DOWN_LEFT
            }
            return line.substring(0, originalLength) +
                    line.substring(originalLength, length).replace(' ', edgeView.H_BAR)
                        .replace(edgeView.DOWN_LEFT, edgeView.H_DOWN) +
                    edgeView.H_BAR.toString().repeat(indexEdge - length) + edgeView.DOWN_LEFT
        }
        val symbol = if (line[indexEdge] == edgeView.H_BAR) edgeView.H_DOWN else edgeView.DOWN_LEFT
        return line.substring(0, originalLength) + line.substring(originalLength, indexEdge)
            .replace(' ', edgeView.H_BAR) + symbol + line.substring(indexEdge + 1)
    }

    private fun drawInDownEdge(line: String, indexEdge: Int, originalLength: Int): String {
        val length = line.length
        if (length < indexEdge) {
            if (length == originalLength) {
                return line + edgeView.ARROW_LEFT +
                        edgeView.H_BAR.toString().repeat(indexEdge - length - 1) +
                        edgeView.UP_LEFT
            }
            return line.substring(0, originalLength) + edgeView.ARROW_LEFT +
                    line.substring(originalLength + 1, length).replace(' ', edgeView.H_BAR)
                        .replace(edgeView.UP_LEFT, edgeView.H_UP) +
                    edgeView.H_BAR.toString().repeat(indexEdge - length) + edgeView.UP_LEFT
        }
        val symbol = if (line[indexEdge] == edgeView.H_BAR) edgeView.H_UP else edgeView.UP_LEFT
        return line.substring(0, originalLength) + edgeView.ARROW_LEFT + line.substring(originalLength + 1, indexEdge)
            .replace(' ', edgeView.H_BAR) + symbol + line.substring(indexEdge + 1)
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
