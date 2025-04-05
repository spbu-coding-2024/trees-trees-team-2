import dto.Node

class RBNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K,V>>(key, value) {
    var parent: RBNode<K, V>? = null
    var color: Boolean = true
    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)
}

