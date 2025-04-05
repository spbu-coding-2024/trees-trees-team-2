package iterators

import dto.BNode
import dto.Node
import kotlin.collections.ArrayDeque

class TreeDFSIterator<K : Comparable<K>, V>(root: BNode<K, V>?) : Iterator<BNode<K, V>> {
    private val stack = ArrayDeque<BNode<K, V>>()

    init {
        pushLeft(root)
    }

    private fun pushLeft(node: BNode<K, V>?) {
        var current = node
        while (current != null) {
            stack.addLast(current)
            current = current.left
        }
    }

    override fun hasNext(): Boolean = stack.isNotEmpty()

    override fun next(): BNode<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = stack.removeLast()
        pushLeft(node.right)
        return node
    }
}