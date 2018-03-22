package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.coin.Address;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Email;
import seedu.address.model.coin.Name;
import seedu.address.model.coin.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Coin objects.
 */
public class CoinBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TAGS = "friends";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public CoinBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the CoinBuilder with the data of {@code coinToCopy}.
     */
    public CoinBuilder(Coin coinToCopy) {
        name = coinToCopy.getName();
        phone = coinToCopy.getPhone();
        email = coinToCopy.getEmail();
        address = coinToCopy.getAddress();
        tags = new HashSet<>(coinToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Coin} that we are building.
     */
    public CoinBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Coin} that we are building.
     */
    public CoinBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Coin} that we are building.
     */
    public CoinBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Coin} that we are building.
     */
    public CoinBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Coin} that we are building.
     */
    public CoinBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Coin build() {
        return new Coin(name, phone, email, address, tags);
    }

}