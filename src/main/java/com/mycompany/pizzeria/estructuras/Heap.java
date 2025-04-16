
package com.mycompany.pizzeria.estructuras;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Heap<T extends Comparable<T>> implements IHeap<T> {
    private ArrayList<T> heap;
    private Comparator<T> comparator;

    // Constructor para max-heap
    public Heap() {
        this.heap = new ArrayList<>();
        this.comparator = null;  // Por defecto, usa el compareTo de T (max-heap)
    }

    // Constructor para min-heap con un comparator
    public Heap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    @Override
    public void add(T elemento) {
        heap.add(elemento);
        siftUp();
    }

    @Override
    public T peek() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    @Override
    public T poll() {
        if (heap.isEmpty()) return null;
        T root = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown();
        }
        return root;
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void siftUp() {
        int idx = heap.size() - 1;
        while (idx > 0) {
            int parentIdx = (idx - 1) / 2;
            if (compare(heap.get(idx), heap.get(parentIdx)) > 0) {
                Collections.swap(heap, idx, parentIdx);
                idx = parentIdx;
            } else {
                break;
            }
        }
    }

    @Override
    public void siftDown() {
        int idx = 0;
        while (2 * idx + 1 < heap.size()) {
            int leftIdx = 2 * idx + 1;
            int rightIdx = 2 * idx + 2;
            int largerChildIdx = leftIdx;

            if (rightIdx < heap.size() && compare(heap.get(rightIdx), heap.get(leftIdx)) > 0) {
                largerChildIdx = rightIdx;
            }

            if (compare(heap.get(idx), heap.get(largerChildIdx)) >= 0) {
                break;
            }

            Collections.swap(heap, idx, largerChildIdx);
            idx = largerChildIdx;
        }
    }

    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return a.compareTo(b);  // Usa compareTo por defecto para max-heap
        }
    }

    // Método para convertir el heap a lista
    public ArrayList<T> toList() {
        return new ArrayList<>(heap);
    }
}
