package seedu.address.model.coin;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Coin in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Coin {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    private final Amount amount;
    private final Price price;

    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Coin(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.amount = new Amount();
        this.price = new Price();
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Amount getAmount() { return amount; }

    public Price getPrice() { return price; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Coin)) {
            return false;
        }

        Coin otherCoin = (Coin) other;
        return otherCoin.getName().equals(this.getName())
                && otherCoin.getPhone().equals(this.getPhone())
                && otherCoin.getEmail().equals(this.getEmail())
                && otherCoin.getAddress().equals(this.getAddress())
                && otherCoin.getAmount().equals(this.getAmount())
                && otherCoin.getPrice().equals(this.getPrice());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, amount, price);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Price: ")
                .append(getPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}