package trees

import Color
import RBNode
import common.Tree
import dto.Node
import iterators.TreeBFSIterator
import iterators.TreeDFSIterator


class RedBlackTree<K : Comparable<K>, V> : Tree<K, V,RBNode<K,V>>() {
    var root: RBNode<K, V>? = null


    private fun  rotateRight(RBNode: RBNode<K, V>?){
        val leftChild = RBNode?.left ?: return


        if (RBNode == root) {
            root = leftChild
        }
            leftChild.parent = RBNode.parent
        if (RBNode.parent!=null){
            if (RBNode.parent?.left == RBNode){
                RBNode.parent?.left = leftChild
            }
            else{
                RBNode.parent?.right = leftChild
            }
        }
        RBNode.left = leftChild.right
        if (leftChild.right!=null){
            leftChild.right?.parent = RBNode


        }
        RBNode.parent = leftChild
        leftChild.right = RBNode
    }
    private fun  rotateLeft(RBNode: RBNode<K, V>?){
        val rightChild = RBNode?.right ?: return
        if (RBNode == root) {
            root = rightChild
        }
        rightChild.parent = RBNode.parent
        if (RBNode.parent!=null){
            if (RBNode.parent?.left == RBNode){
                RBNode.parent?.left = rightChild
            }
            else{
                RBNode.parent?.right = rightChild
            }
        }
        RBNode.right = rightChild?.left
        if (rightChild.left!=null){
            rightChild.left?.parent = RBNode
        }
        RBNode.parent = rightChild
        rightChild.left = RBNode
    }
    private fun  uncle(RBNode: RBNode<K, V>?): RBNode<K, V>?{
        if (RBNode?.parent?.parent == null){
            return null
        }
        if (RBNode.parent == RBNode.parent?.parent?.left){
            return RBNode.parent?.parent?.right
        }else{
            return RBNode.parent?.parent?.left
        }
    }
    private fun  insertCase1(RBNode: RBNode<K, V>?) {
        if (RBNode?.parent == null) {
            RBNode?.color = Color.BLACK
        }
        else{
            insertCase2(RBNode)
        }
        return
    }
    private fun  insertCase2(RBNode: RBNode<K, V>){
        if (RBNode.parent?.color == Color.BLACK){
            return
        }else{
            insertCase3(RBNode)
        }
    }
    private fun  insertCase3(RBNode: RBNode<K, V>?){
        val uncle = uncle(RBNode)
        if((uncle != null) && uncle.color == Color.RED){
            RBNode?.parent?.color = Color.BLACK
            uncle.color = Color.BLACK
            RBNode?.parent?.parent?.color = Color.RED
            insertCase1(RBNode?.parent?.parent)
        }
        else{
            insertCase4(RBNode)
        }
        return
    }
    private fun  insertCase4(RBNode: RBNode<K, V>?) {
        var node1 = RBNode
        val parent = node1?.parent
        val g = parent?.parent
        if (parent != null && g != null) {
            if (node1 == parent.right && parent == g.left) {
                rotateLeft(node1?.parent)
                node1 = node1?.left
            } else if (node1 == parent.left && parent == g.right) {
                rotateRight(node1?.parent)
                node1 = node1?.right
            }
        }
        insertCase5(node1)

    }
    private fun insertCase5(RBNode: RBNode<K, V>?) {
        val parent = RBNode?.parent
        val g = parent?.parent
        parent?.color = Color.BLACK
        g?.color = Color.RED
        if (g == null) return
        val checkRoot = (g.parent == null)
        if (RBNode == parent.left && parent == g.left) {
            rotateRight(g)
        } else {
            rotateLeft(g)
        }
        if (checkRoot) {
            root = g.parent
            root?.color = Color.BLACK
        }

    }
    private fun deleteCase1(RBNode: RBNode<K, V>) {
        val parent = RBNode.parent
        if (parent !=null ){
            deleteCase2(RBNode)
        }

    }
    private fun deleteCase2(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if (s?.color == Color.RED) {
            if (RBNode == parent?.left) {
                rotateLeft(parent)
            } else if (RBNode == parent?.right) {
                rotateRight(parent)
            }
            if (root == RBNode.parent){
                root = RBNode.parent?.parent
            }
        }
        deleteCase3(RBNode)
    }
    private fun sibling(RBNode: RBNode<K, V>): RBNode<K, V>? {
        val parent = RBNode.parent

        if (RBNode == parent?.left){
            return parent.right
        }
        else{
            return parent?.left
        }
    }
    private fun deleteCase3(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if ((parent?.color == Color.BLACK) && (s?.color == Color.BLACK) &&
            (s.right == null || s.right?.color == Color.BLACK) && (s.left == null || s.left?.color == Color.BLACK)) {
            s.color = Color.RED
            deleteCase1(parent)
        } else {
            deleteCase4(RBNode)
        }
    }
    private fun deleteCase4(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if ((parent?.color == Color.RED) && (s?.color == Color.BLACK) &&
            (s.right == null || s.right?.color == Color.BLACK) && (s.left == null || s.left?.color == Color.BLACK)) {
            s.color = Color.RED
            parent.color = Color.BLACK
        } else {
            deleteCase5(RBNode)
        }
    }
    private fun deleteCase5(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if (s?.color == Color.BLACK) {
            if ((RBNode == parent?.left) && (s.right == null || s.right?.color == Color.BLACK) && (s.left?.color == Color.RED)) {
                rotateRight(s)
            } else if ((RBNode == parent?.right) && (s.left == null || s.left?.color == Color.BLACK) && (s.right?.color == Color.RED)) {
                rotateLeft(s)
            }
        }
        deleteCase6(RBNode)
    }
    private fun deleteCase6(RBNode: RBNode<K, V>){
        val s=  sibling(RBNode)
        val parent = RBNode.parent


        if (RBNode == parent?.left){
            s?.right?.color = Color.BLACK
            rotateLeft(parent)
        }
        else{
            s?.left?.color = Color.BLACK
            rotateRight(parent)
        }
        if (root== RBNode.parent){
            root=RBNode.parent?.parent

        }
    }
    private fun findNode(key: K): RBNode<K, V>? {
        var current = root

        while (current != null ) {
            if (key == current.key) {
                return current
            }
            if (key < current.key) {
                current = current.left
            }
            else
                current = current.right
        }
        return null
    }
    private fun deleteNode(RBNode: RBNode<K, V>?) {
        if (RBNode == null) return
        if (RBNode.left != null && RBNode.right != null) {
            val prev = findMax(RBNode.left)
            if (prev != null) {
                RBNode.key = prev.key
                RBNode.value = prev.value
                deleteNode(prev)

            }
            return
        }
        if (RBNode == root && RBNode.isLeaf()) {
            root = null
            return
        }
        if (RBNode.color == Color.RED && RBNode.isLeaf()) {
            val parent = RBNode.parent
            if (parent != null) {
                if (parent.left == RBNode) parent.left = null
                else parent.right = null
            }
            return
        }

        if (RBNode.color == Color.BLACK && RBNode.left != null){
            RBNode.key = RBNode.left?.key ?: return
            RBNode.value = RBNode.left?.value ?: return
            RBNode.left = null
            return
        }
        if (RBNode.color == Color.BLACK && RBNode.right != null){
            RBNode.key = RBNode.right?.key ?: return
            RBNode.value = RBNode.right?.value ?: return
            RBNode.right = null
            return
        }
        else{
            deleteCase1(RBNode)
        }
        if (RBNode == RBNode.parent?.left)
            RBNode.parent?.left = null
        else
            RBNode.parent?.right = null

    }

    private fun findMax(RBNode: RBNode<K, V>?): RBNode<K, V>? {
        if (RBNode?.right == null)
            return RBNode
        else
            return findMax(RBNode.right)
    }
    override fun insert(key: K, value: V) {
        val newRBNode = RBNode(key, value)
        if (root == null) {
            root = RBNode(key = key, value = value)
            root?.color = Color.BLACK

            return
        }
        var currentRBNode: RBNode<K, V>? = root
        var parentRBNode: RBNode<K, V>? = null
        while (currentRBNode != null) {
            parentRBNode = currentRBNode
            if (key == currentRBNode.key) {
                currentRBNode.value = value
                return
            } else if (key < currentRBNode.key) {
                currentRBNode = currentRBNode.left
            } else {
                currentRBNode = currentRBNode.right
            }
        }
        if (parentRBNode != null) {
            newRBNode.parent = parentRBNode
            if (key < parentRBNode.key) {
                parentRBNode.left = newRBNode
            } else {
                parentRBNode.right = newRBNode
            }
        }
        insertCase1(newRBNode)
        root?.color = Color.BLACK
    }

    override fun search(key: K): V? {
        return searchValue(root,key)
    }

    override fun delete(key: K): Boolean {
        val node = findNode(key) ?: return false
        deleteNode(node)
        return true
    }

    override fun treeBFSIterator(): Iterator<RBNode<K, V>> {
        return TreeBFSIterator(root)
    }

    override fun treeDFSIterator(): Iterator<RBNode<K, V>> {
        return TreeDFSIterator(root)
    }

}