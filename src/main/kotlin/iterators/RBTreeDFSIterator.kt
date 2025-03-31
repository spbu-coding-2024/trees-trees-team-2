package iterators

import RBNode
import dto.Node
import kotlin.collections.ArrayDeque

class RBTreeDFSIterator<K : Comparable<K>, V>(root: RBNode<K, V>?) : Iterator<RBNode<K, V>> {
    private val stack = ArrayDeque<RBNode<K, V>>()

    init {
        pushLeft(root)
    }

    private fun pushLeft(node: RBNode<K, V>?) {
        var current = node
        while (current != null) {
            stack.addLast(current)
            current = current.left
        }
    }

    override fun hasNext(): Boolean = stack.isNotEmpty()

    override fun next(): RBNode<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = stack.removeLast()
        pushLeft(node.right)
        return node
    }
}