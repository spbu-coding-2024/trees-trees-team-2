package dto

open class Node<K : Comparable<K>, V, T>(
    var key: K,
    var value: V,
    var left: T? = null,
    var right: T? = null
)