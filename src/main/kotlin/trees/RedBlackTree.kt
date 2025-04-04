package trees

import RBNode
import common.Tree


class RedBlackTree<K : Comparable<K>, V> : Tree<K, V>() {
    var root: RBNode<K, V>? = null


    private fun  rotateRight(RBNode: RBNode<K, V>?){
        val leftChild = RBNode?.left ?: return


        if (RBNode == root) {
            root = leftChild
        }
        val p = RBNode.left
        if (p != null) {
            p.parent = RBNode.parent
        }

        if (RBNode.parent!=null){
            if (RBNode.parent?.left == RBNode){
                RBNode.parent?.left = p
            }
            else{
                RBNode.parent?.right = p
            }
        }
        RBNode.left = p?.right
        if (p?.right!=null){
            p.right?.parent = RBNode


        }
        RBNode.parent = p
        p?.right = RBNode
    }
    private fun  rotateLeft(RBNode: RBNode<K, V>?){
        val rightChild = RBNode?.right ?: return
        if (RBNode == root) {
            root = rightChild
        }
        val right1 = RBNode.right
        if (right1 != null) {
            right1.parent = RBNode.parent
        }

        if (RBNode.parent!=null){
            if (RBNode.parent?.left == RBNode){
                RBNode.parent?.left = right1
            }
            else{
                RBNode.parent?.right = right1
            }
        }
        RBNode.right = right1?.left
        if (right1?.left!=null){
            right1.left?.parent = RBNode


        }
        RBNode.parent = right1
        right1?.left = RBNode
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
    private fun  insert_case1(RBNode: RBNode<K, V>?) {
        if (RBNode?.parent == null) {
            RBNode?.color = false
        }
        else{
            insert_case2(RBNode)
        }
        return
    }
    private fun  insert_case2(RBNode: RBNode<K, V>){
        if (RBNode.parent?.color == false){
            return
        }else{
            insert_case3(RBNode)
        }
    }
    private fun  insert_case3(RBNode: RBNode<K, V>?){
        val uncle = uncle(RBNode)
        if((uncle != null) && uncle.color){
            RBNode?.parent?.color = false
            uncle.color = false
            RBNode?.parent?.parent?.color = true
            insert_case1(RBNode?.parent?.parent)
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
        parent?.color = false
        g?.color = true
        if (g == null) return
        val checkRoot = (g.parent == null)
        if (RBNode == parent.left && parent == g.left) {
            rotateRight(g)
        } else {
            rotateLeft(g)
        }
        if (checkRoot) {
            root = g.parent
            root?.color = false
        }

    }
    fun deleteCase1(RBNode: RBNode<K, V>) {
        val parent = RBNode.parent
        if (parent !=null ){
            deleteCase2(RBNode)
        }

    }
    fun deleteCase2(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if (s?.color == true) {
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
    fun sibling(RBNode: RBNode<K, V>): RBNode<K, V>? {
        val parent = RBNode.parent

        if (RBNode == parent?.left){
            return parent.right
        }
        else{
            return parent?.left
        }
    }
    fun deleteCase3(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if ((parent?.color == false) && (s?.color == false) &&
            (s.right == null || s.right?.color == false) && (s.left == null || s.left?.color == false)) {
            s.color = true
            deleteCase1(parent)
        } else {
            deleteCase4(RBNode)
        }
    }
    fun deleteCase4(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if ((parent?.color == true) && (s?.color == false) &&
            (s.right == null || s.right?.color == false) && (s.left == null || s.left?.color == false)) {
            s.color = true
            parent.color = false
        } else {
            deleteCase5(RBNode)
        }
    }
    fun deleteCase5(RBNode: RBNode<K, V>) {
        val s = sibling(RBNode)
        val parent = RBNode.parent
        if (s?.color == false) {
            if ((RBNode == parent?.left) && (s.right == null || s.right?.color == false) && (s.left?.color == true)) {
                rotateRight(s)
            } else if ((RBNode == parent?.right) && (s.left == null || s.left?.color == false) && (s.right?.color == true)) {
                rotateLeft(s)
            }
        }
        deleteCase6(RBNode)
    }
    fun deleteCase6(RBNode: RBNode<K, V>){
        val s=  sibling(RBNode)
        val parent = RBNode.parent


        if (RBNode == parent?.left){
            s?.right?.color = false
            rotateLeft(parent)
        }
        else{
            s?.left?.color = false
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
        if (RBNode.color && RBNode.isLeaf()) {
            val parent = RBNode.parent
            if (parent != null) {
                if (parent.left == RBNode) parent.left = null
                else parent.right = null
            }
            return
        }

        if (RBNode.color && RBNode.left != null && !(RBNode.left?.color == true)){
            RBNode.key = RBNode.left?.key ?: return
            RBNode.value = RBNode.left?.value ?: return
            RBNode.left = null
            return
        }
        if (RBNode.color && RBNode.right != null && !(RBNode.right?.color == true)){
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
            root?.color = false

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
        insert_case1(newRBNode)
        root?.color = false
    }

    override fun search(key: K): V? {
        var current = root
        while (current!=null){
            if (key<current.key){
                current = current.left

            }
            else if(key>current.key){
                current = current.right
            }else{
                return current.value
            }

        }
        return null
    }

    override fun delete(key: K): Boolean {
        val node = findNode(key) ?: return false
        deleteNode(node)
        return true
    }

}