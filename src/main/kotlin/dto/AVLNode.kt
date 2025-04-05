package dto
open class AVLNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, AVLNode<K,V>>(key, value) {
    var height = 1
}