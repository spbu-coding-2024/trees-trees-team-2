package dto


class TreapNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, TreapNode<K, V>>(key, value){
    var priority:Int = 0
}
