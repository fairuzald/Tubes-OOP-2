package org.bro.tubesoop2.quantifiable;


import java.util.Objects;

public class Quantifiable<T> {
    private T value;
    private int quantity;

    /**
     * Constructor to initialize Quantifiable object
     *
     * @param val Initial value
     * @param qty Initial quantity
     */
    public Quantifiable(T val, int qty) {
        this.value = val;
        this.quantity = qty;
    }

    /**
     * Get the value of the Quantifiable object
     *
     * @return T Value of the object
     */
    public T getValue() {
        return value;
    }

    /**
     * Get the quantity of the Quantifiable object
     *
     * @return int Quantity of the object
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the value of the Quantifiable object
     *
     * @param val New value to set
     */
    public void setValue(T val) {
        this.value = val;
    }

    /**
     * Set the quantity of the Quantifiable object
     *
     * @param qty New quantity to set
     */
    public void setQuantity(int qty) {
        this.quantity = qty;
    }

    /**
     * Increment the quantity by a given value
     *
     * @param qty Value to add to the quantity
     */
    public void incrementQuantity(int qty) {
        this.quantity += qty;
    }

    /**
     * Increment the quantity by the quantity of another Quantifiable
     *
     * @param other Quantifiable object to add the quantity from
     */
    public void incrementQuantity(Quantifiable<T> other) {
        this.quantity += other.getQuantity();
    }

    /**
     * Decrement the quantity by a given value
     *
     * @param qty Value to subtract from the quantity
     */
    public void decrementQuantity(int qty) {
        this.quantity -= qty;
    }

    /**
     * Increment the quantity by 1 (postfix increment)
     */
    public Quantifiable<T> increment() {
        Quantifiable<T> temp = new Quantifiable<>(this.value, this.quantity);
        this.quantity++;
        return temp;
    }

    /**
     * Decrement the quantity by 1 (postfix decrement)
     */
    public Quantifiable<T> decrement() {
        Quantifiable<T> temp = new Quantifiable<>(this.value, this.quantity);
        this.quantity--;
        return temp;
    }

    /**
     * Check if both Quantifiable objects have the same value
     *
     * @param other Quantifiable object to compare with
     * @return true if same
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Quantifiable<?> that = (Quantifiable<?>) other;

        if (quantity != that.quantity) return false;
        return value != null ? Objects.equals(value, that.value) : that.value == null;
    }

    /**
     * Check if the value is infinite
     *
     * @return true if infinite
     */
    public boolean isInfinite() {
        return this.quantity == INFINITE;
    }

    /**
     * Check if the value is infinite (static version)
     *
     * @param q Quantifiable object to check
     * @return true if infinite
     */
    public static <T> boolean isInfinite(Quantifiable<T> q) {
        return q.getQuantity() == INFINITE;
    }

    public static final int INFINITE = -1;
}

