package trees


import common.Tree
import dto.TreapNode
import iterators.TreeBFSIterator
import iterators.TreeDFSIterator
import kotlin.random.Random


class TreapTree<K : Comparable<K>, V> : Tree<K, V,TreapNode<K,V>>() {
    private var root: TreapNode<K,V>? = null
    internal fun getRootForTesting():TreapNode<K, V>? = root
    private fun newNode(key:K, value:V): TreapNode<K,V> {
        val tmp = TreapNode(key, value)
        tmp.priority = Random.nextInt(100000)
        tmp.left = null
        tmp.right = null
        return tmp
    }
    private fun rotateRight(node: TreapNode<K,V>): TreapNode<K,V>? { // поворот вокруг нода
        val firstRotateNode = node.left
        val secondRotateNode = firstRotateNode?.right
        firstRotateNode?.right = node
        node.left = secondRotateNode
        return firstRotateNode
    }
    private fun rotateLeft(node: TreapNode<K,V>): TreapNode<K,V>?{ //поворот вокруг нода
        val firstRotateNode = node.right
        val secondRotateNode = firstRotateNode?.left
        firstRotateNode?.left = node
        node.right = secondRotateNode
        return firstRotateNode
    }
    private fun insertTreapNode(insertRoot: TreapNode<K,V>?, key: K, value: V): TreapNode<K, V> {
        var insertRoot2 = insertRoot
        if (insertRoot2 == null){
            return newNode(key, value)
        }
        if (key <= insertRoot.key){
            insertRoot2.left = insertTreapNode(insertRoot2.left, key, value)
            insertRoot2.left?.priority?.let {
                if (it > insertRoot2.priority){
                    insertRoot2 = rotateRight(insertRoot2)
                }
            }
        }
        else{
            insertRoot2.right = insertTreapNode(insertRoot2.right, key, value)
            insertRoot2.right?.priority?.let {
                if (it > insertRoot2.priority){
                    insertRoot2 = rotateLeft(insertRoot2)
                }
            }
        }
        return insertRoot2
    }
    private fun deleteNode(node: TreapNode<K,V>?, key: K): TreapNode<K,V>? {
        var node2 = node
        val leftPriority = node2?.left?.priority ?:0
        val rightPriority = node2?.right?.priority ?: 0
        if (node2 == null){
            return node2
        }
        if (key < node2.key){
            node2.left = deleteNode(node2.left, key)
        }
        else if (key > node2.key){
            node2.right = deleteNode(node2.right, key)

        }
        else if (node2.left == null && node2.right !=null){
            val tmp = node2.right
            node2 = tmp

        }

        else if (node2.right == null && node2.left!=null) {
                val tmp  = node2.left
                node2 = tmp

        }
        else if (leftPriority < rightPriority){
            node2 = rotateLeft(node2)
            node2?.left  =deleteNode(node2.left, key)
        }
        else{
            node2 = rotateRight(node2)
            node2?.right = deleteNode(node2.right,key)
        }
        return node2
    }
    override fun insert(key: K, value: V) {
        root = insertTreapNode(root ,key, value)
    }

    override fun search(key: K): V? {
        return searchValue(root, key)
    }

    override fun delete(key: K): Boolean {
        if (searchValue(root,key) == null) return false
        root = deleteNode(root, key)
        return true
    }

    override fun treeBFSIterator(): Iterator<TreapNode<K, V>> {
        return TreeBFSIterator(root)
    }

    override fun treeDFSIterator(): Iterator<TreapNode<K, V>> {
        return TreeDFSIterator(root)
    }

}



