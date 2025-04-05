import dto.Node
enum class Color{BLACK, RED}
class RBNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K,V>>(key, value) {
    var parent: RBNode<K, V>? = null
    var color:Color = Color.RED
    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)
}

