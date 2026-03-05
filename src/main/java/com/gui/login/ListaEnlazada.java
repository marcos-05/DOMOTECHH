package com.gui.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListaEnlazada<T> {
    public class Node<T> {  // Especificando T dentro de Node
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    Node<T> head;
    Node<T> tail;
    int size;

    public ListaEnlazada() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            this.tail = newNode;
        }
        newNode.next = this.head;
        this.head = newNode;
        size++;
    }

    // Método que convierte la lista enlazada en una lista de tipo T
    public List<T> toList() {
        List<T> lista = new ArrayList<>();
        Node<T> current = this.head;
        while (current != null) {
            lista.add(current.data);
            current = current.next;
        }
        return lista;
    }

    // Otros métodos...
}

