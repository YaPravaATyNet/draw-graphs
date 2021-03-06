package views

object EdgeViewSimple: EdgeView {
    override val H_BAR = '─'
    override val V_BAR = '│'
    override val DOWN_RIGHT = '┌'
    override val DOWN_LEFT = '┐'
    override val UP_RIGHT = '└'
    override val UP_LEFT = '┘'
    override val V_RIGHT = '├'
    override val V_LEFT = '┤'
    override val H_DOWN = '┬'
    override val H_UP = '┴'
    override val CROSS = '┼'
    override val ARROW_LEFT = '←'
    override val ARROW_UP = '↑'
    override val ARROW_RIGHT = '→'
    override val ARROW_DOWN = '↓'
}