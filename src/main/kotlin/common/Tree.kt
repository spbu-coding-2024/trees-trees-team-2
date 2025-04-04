package common

import dto.Node

abstract class Tree<K : Comparable<K>, V> {
    private var root: Node<K, V>? = null
    abstract fun insert(key: K, value: V)

    fun search(key: K): V? {
        var node = root;
        while (node != null) {
            node = when {
                key < node.key -> node.left
                key > node.key -> node.right
                else -> return node.value
            }
        }
        return null
    }

    abstract fun delete(key: K): Boolean
}