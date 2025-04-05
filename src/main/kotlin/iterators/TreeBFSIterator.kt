package iterators

import dto.Node

class TreeBFSIterator<K : Comparable<K>, V,  T : Node<K, V, T>>(root: T?) : Iterator<T> {
    private val queue = ArrayDeque<T>()

    init {
        if (root != null) {
            queue.addLast(root)
        }
    }

    override fun hasNext(): Boolean = queue.isNotEmpty()

    override fun next(): T {
        if (!hasNext()) throw NoSuchElementException()

        val node = queue.removeFirst()

        node.left?.let { queue.addLast(it) }
        node.right?.let { queue.addLast(it) }

        return node
    }
}