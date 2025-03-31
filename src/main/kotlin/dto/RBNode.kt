import dto.Node

open class RBNode<K : Comparable<K>, V>(
    var key: K,
    var value: V,
    var left: RBNode<K, V>? = null,
    var right: RBNode<K, V>? = null,
    var parent: RBNode<K, V>? = null,
    var color: Boolean = true,
){
    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)
}