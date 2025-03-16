package trees

class AVLTree<K : Comparable<K>, V>: BinarySearchTree<K, V>() {
    override fun insert(key: K, value: V) {
        super.insert(key, value)
    }

    override fun delete(key: K) {
        super.delete(key)
    }
}