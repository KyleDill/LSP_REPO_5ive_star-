package org.howard.edu.lsp.assignment5;

import java.util.TreeSet;

/**
 * Represents a mathematical set of integers: an unordered collection with
 * no duplicate values. Internally backed by a {@link TreeSet}, which
 * gives automatic de-duplication and ascending order for free, which in
 * turn makes {@link #toString()} and the ordering-sensitive behavior of
 * {@link #largest()} / {@link #smallest()} straightforward to implement
 * correctly. All set-algebra operations ({@link #union}, {@link #intersect},
 * {@link #diff}, {@link #complement}, {@link #symmetricDiff}) return a new,
 * independent {@code IntegerSet} and never modify either operand.
 *
 * @author Kyle Dill
 */
public class IntegerSet {

    private final TreeSet<Integer> elements;

    /**
     * Creates a new, empty IntegerSet.
     */
    public IntegerSet() {
        this.elements = new TreeSet<>();
    }

    /**
     * Removes all elements from this set, leaving it empty.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Returns the number of unique elements currently in this set.
     *
     * @return the number of elements
     */
    public int length() {
        return elements.size();
    }

    /**
     * Determines whether this set contains exactly the same elements as
     * another set, regardless of insertion order.
     *
     * @param b the set to compare against
     * @return true if both sets contain exactly the same elements
     */
    public boolean equals(IntegerSet b) {
        if (b == null) {
            return false;
        }
        if (this == b) {
            return true;
        }
        return this.elements.equals(b.elements);
    }

    /**
     * Determines whether the given value is present in this set.
     *
     * @param value the value to look for
     * @return true if the value is present
     */
    public boolean contains(int value) {
        return elements.contains(value);
    }

    /**
     * Returns the largest value in this set.
     *
     * @return the largest element
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot get largest element of an empty set.");
        }
        return elements.last();
    }

    /**
     * Returns the smallest value in this set.
     *
     * @return the smallest element
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot get smallest element of an empty set.");
        }
        return elements.first();
    }

    /**
     * Adds the given value to this set. If the value is already present,
     * this has no effect (duplicates are ignored).
     *
     * @param item the value to add
     */
    public void add(int item) {
        elements.add(item);
    }

    /**
     * Removes the given value from this set. If the value is not present,
     * this has no effect.
     *
     * @param item the value to remove
     */
    public void remove(int item) {
        elements.remove(item);
    }

    /**
     * Returns a new set containing every element that appears in either
     * this set or {@code b}. Neither this set nor {@code b} is modified.
     *
     * @param b the other set
     * @return a new IntegerSet containing the union of both sets
     */
    public IntegerSet union(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(this.elements);
        result.elements.addAll(b.elements);
        return result;
    }

    /**
     * Returns a new set containing every element shared by both this set
     * and {@code b}. Neither this set nor {@code b} is modified.
     *
     * @param b the other set
     * @return a new IntegerSet containing the intersection of both sets
     */
    public IntegerSet intersect(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(this.elements);
        result.elements.retainAll(b.elements);
        return result;
    }

    /**
     * Returns a new set containing every element that is in this set but
     * not in {@code b}. Neither this set nor {@code b} is modified.
     *
     * @param b the other set
     * @return a new IntegerSet containing the elements of this set that
     *         are absent from {@code b}
     */
    public IntegerSet diff(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(this.elements);
        result.elements.removeAll(b.elements);
        return result;
    }

    /**
     * Returns a new set containing every element that is in {@code b} but
     * not in this set. This method uses the assignment-specific
     * definition of complement (the reverse difference, {@code b - this}),
     * not the universal-set complement. Neither this set nor {@code b}
     * is modified.
     *
     * @param b the other set
     * @return a new IntegerSet containing the elements of {@code b} that
     *         are absent from this set
     */
    public IntegerSet complement(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(b.elements);
        result.elements.removeAll(this.elements);
        return result;
    }

    /**
     * Determines whether every element of this set is also present in
     * {@code b}. An empty set is considered a subset of every set,
     * including another empty set.
     *
     * @param b the set to test against
     * @return true if this set is a subset of {@code b}
     */
    public boolean isSubsetOf(IntegerSet b) {
        return b.elements.containsAll(this.elements);
    }

    /**
     * Determines whether this set and {@code b} share no elements. An
     * empty set is considered disjoint from every set.
     *
     * @param b the other set
     * @return true if the two sets share no elements
     */
    public boolean isDisjoint(IntegerSet b) {
        for (Integer value : this.elements) {
            if (b.elements.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new set containing every element that occurs in exactly
     * one of this set and {@code b} (the union minus the intersection).
     * Neither this set nor {@code b} is modified.
     *
     * @param b the other set
     * @return a new IntegerSet containing the symmetric difference of
     *         both sets
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        IntegerSet result = new IntegerSet();
        result.elements.addAll(this.elements);
        result.elements.addAll(b.elements);

        IntegerSet both = new IntegerSet();
        both.elements.addAll(this.elements);
        both.elements.retainAll(b.elements);

        result.elements.removeAll(both.elements);
        return result;
    }

    /**
     * Determines whether this set contains no elements.
     *
     * @return true if this set is empty
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns a string representation of this set: elements in ascending
     * numerical order, separated by a comma and one space, enclosed in
     * square brackets (for example, {@code [-3, 0, 8, 15]}). An empty
     * set is represented as {@code []}.
     *
     * @return the formatted string representation of this set
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (Integer value : elements) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(value);
            first = false;
        }
        sb.append("]");
        return sb.toString();
    }
}