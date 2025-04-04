package dto

class BNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, BNode<K,V>>(key, value) {
}