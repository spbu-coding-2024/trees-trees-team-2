package iterators

import dto.AVLNode

class AVLTreeBFSIterator<K : Comparable<K>, V>(root: AVLNode<K, V>?) : Iterator<AVLNode<K, V>> {
    private val queue = ArrayDeque<AVLNode<K, V>>()

    init {
        if (root != null) {
            queue.addLast(root)
        }
    }

    override fun hasNext(): Boolean = queue.isNotEmpty()

    override fun next(): AVLNode<K, V> {
        if (!hasNext()) throw NoSuchElementException()

        val node = queue.removeFirst()

        node.left?.let { queue.addLast(it) }
        node.right?.let { queue.addLast(it) }

        return node
    }
}
