# Библиотека (Kotlin)

Этот проект реализует различные **деревья поиска** на Kotlin, включая:
- **Обычное двоичное дерево поиска (BST)**
- **Красно-черное дерево (Red-Black Tree)**
- **AVL-дерево**

## 📂 Структура проекта
```
com/example/datastructures/
│── common/               # Общие интерфейсы и класс узла
│   ├── Tree.kt           # Интерфейс для деревьев
│── dto/                  # Общие объекты
│   ├── Node.kt           # Самый базовый узел
│── iterators/            # Общие итераторы
│   ├── TreeBFSIterator.kt  # Итератор обхода в ширину
│   ├── TreeDFSIterator.kt  # Итератор обхода в глубину
│── trees/                # Реализации различных деревьев
│   ├── BinarySearchTree.kt    # Реализация BST
│   ├── RedBlackTree.kt        # Реализация красно-черного дерева
│   ├── AVLTree.kt             # Реализация AVL-дерева
│
└── test/                 # Юнит-тесты для деревьев
    ├── TreeTest.kt
    ├── BSTTest.kt
    ├── RedBlackTreeTest.kt
    ├── AVLTreeTest.kt
```

## 🚀 Начало работы
### 1️⃣ Клонирование репозитория
```sh
git clone https://github.com/alken1t15/datastructures.git
cd datastructures
```

### 2️⃣ Сборка проекта
Используйте **Gradle** для сборки проекта:
```sh
gradle build
```

### 3️⃣ Запуск тестов
```sh
gradle test
```

## 🛠 Использование
Вы можете создать и использовать любое дерево следующим образом:
```kotlin
import com.example.datastructures.trees.BinarySearchTree

fun main() {
    val bst = BinarySearchTree<Int, Int>()
    bst.insert(5, 10)
    bst.insert(3, 22)
    bst.insert(7, 7)
    
    println("Поиск 10: ${bst.search(5)}") // Вернет значение 10
    println("Поиск 20: ${bst.search(3)}") // Вернет значение 22
}
```

## 📌 Возможности
✅ Вставка, удаление, поиск элементов, перебор элементов

## 📜 Лицензия
Этот проект распространяется под лицензией MIT.
