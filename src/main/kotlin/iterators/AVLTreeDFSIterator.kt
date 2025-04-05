package iterators

import dto.AVLNode
import kotlin.collections.ArrayDeque

class AVLTreeDFSIterator<K : Comparable<K>, V>(root: AVLNode<K, V>?) : Iterator<AVLNode<K, V>> {
    private val stack = ArrayDeque<AVLNode<K, V>>()

    init {
        pushLeft(root)
    }

    private fun pushLeft(node: AVLNode<K, V>?) {
        var current = node
        while (current != null) {
            stack.addLast(current)
            current = current.left
        }
    }

    override fun hasNext(): Boolean = stack.isNotEmpty()

    override fun next(): AVLNode<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = stack.removeLast()
        pushLeft(node.right)
        return node
    }
}
