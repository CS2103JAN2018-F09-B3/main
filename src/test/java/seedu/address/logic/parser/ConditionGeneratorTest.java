package seedu.address.logic.parser;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TestUtil.AND_TOKEN;
import static seedu.address.testutil.TestUtil.DECIMAL_STRING;
import static seedu.address.testutil.TestUtil.DECIMAL_TOKEN;
import static seedu.address.testutil.TestUtil.EOF_TOKEN;
import static seedu.address.testutil.TestUtil.GREATER_TOKEN;
import static seedu.address.testutil.TestUtil.LEFT_PAREN_TOKEN;
import static seedu.address.testutil.TestUtil.NOT_TOKEN;
import static seedu.address.testutil.TestUtil.OR_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_BOUGHT_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_CODE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_HELD_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_MADE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_PRICE_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_SOLD_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_TAG_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_WORTH_TOKEN;
import static seedu.address.testutil.TestUtil.RIGHT_PAREN_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_ONE_STRING;
import static seedu.address.testutil.TestUtil.STRING_ONE_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_THREE_STRING;
import static seedu.address.testutil.TestUtil.STRING_THREE_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_TWO_STRING;
import static seedu.address.testutil.TestUtil.STRING_TWO_TOKEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.Price;
import seedu.address.testutil.CoinBuilder;

//@@author Eldon-Chung
public class ConditionGeneratorTest {

    private static final Token INVALID_TAG_NAME = new Token(TokenType.STRING, "invalid-name");
    private static final double DECIMAL_OFFSET = 1.0;
    private static final double NEW_PRICE = 2.0;
    private static Amount greaterAmount;
    private static Amount lesserAmount;
    private static Price greaterPrice;
    private static Price lesserPrice;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initializeTestValues() throws IllegalValueException {
        // Load the offset values to test numerical conditions
        greaterAmount = new Amount(ParserUtil.parseDouble(DECIMAL_STRING) + DECIMAL_OFFSET);
        lesserAmount = new Amount(ParserUtil.parseDouble(DECIMAL_STRING) - DECIMAL_OFFSET);
        greaterPrice = new Price(ParserUtil.parseDouble(DECIMAL_STRING) + DECIMAL_OFFSET);
        lesserPrice = new Price(ParserUtil.parseDouble(DECIMAL_STRING) - DECIMAL_OFFSET);
    }


    @Test
    public void cond_generatesAmountHeldCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_HELD_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesCodeCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_CODE_TOKEN, STRING_ONE_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withName(STRING_ONE_STRING).build();
        Coin failCoin = new CoinBuilder().withName(STRING_TWO_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesDollarsBoughtCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_BOUGHT_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesDollarsSoldCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_SOLD_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount)
                .withAmountSold(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(greaterAmount)
                .withAmountSold(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesMadeCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_MADE_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(greaterAmount).build();

        // Increase the price on the pass coin to generate some profit
        passCoin = new Coin(passCoin, NEW_PRICE);

        passCoin.addTotalAmountSold(greaterAmount);

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesPriceCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_PRICE_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withPrice(greaterPrice).build();
        Coin failCoin = new CoinBuilder().withPrice(lesserPrice).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesTagCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin failCoin = new CoinBuilder().withTags(STRING_TWO_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void cond_generatesWorthCondition() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_WORTH_TOKEN, GREATER_TOKEN, DECIMAL_TOKEN);
        Predicate<Coin> condition = conditionGenerator.cond();
        Coin passCoin = new CoinBuilder().withAmountBought(greaterAmount).build();
        Coin failCoin = new CoinBuilder().withAmountBought(lesserAmount).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void term_generatesConditionWithParentheses() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(LEFT_PAREN_TOKEN, PREFIX_CODE_TOKEN,
                STRING_ONE_TOKEN, RIGHT_PAREN_TOKEN);
        Predicate<Coin> condition = conditionGenerator.term();
        Coin passCoin = new CoinBuilder().withName(STRING_ONE_STRING).build();
        Coin failCoin = new CoinBuilder().withName(STRING_TWO_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void term_generatesConditionWithNegation() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(NOT_TOKEN, PREFIX_CODE_TOKEN,
                STRING_ONE_TOKEN);
        Predicate<Coin> condition = conditionGenerator.term();
        Coin passCoin = new CoinBuilder().withName(STRING_TWO_STRING).build();
        Coin failCoin = new CoinBuilder().withName(STRING_ONE_STRING).build();

        assertTrue(condition.test(passCoin));
        assertFalse(condition.test(failCoin));
    }

    @Test
    public void expression_generationConditionWithConjunction() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, AND_TOKEN,
                PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.expression();

        // Test to see if the conjunction operation commutes
        Coin passCoin1 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
        Coin passCoin2 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_ONE_STRING).build();

        Coin failCoin1 = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin failCoin2 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
        Coin failCoin3 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();

        assertTrue(condition.test(passCoin1));
        assertTrue(condition.test(passCoin2));

        assertFalse(condition.test(failCoin1));
        assertFalse(condition.test(failCoin2));
        assertFalse(condition.test(failCoin3));
    }

    @Test
    public void expression_generationConditionWithDisjunction() throws Exception {
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, OR_TOKEN,
                PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.expression();

        Coin passCoin1 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
        Coin passCoin2 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_ONE_STRING).build();
        Coin passCoin3 = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin passCoin4 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
        Coin passCoin5 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();

        Coin failCoin = new CoinBuilder().withTags(STRING_THREE_STRING).build();

        assertTrue(condition.test(passCoin1));
        assertTrue(condition.test(passCoin2));
        assertTrue(condition.test(passCoin3));
        assertTrue(condition.test(passCoin4));
        assertTrue(condition.test(passCoin5));

        assertFalse(condition.test(failCoin));
    }

    @Test
    public void generate_correctOrderOfPrecedenceWithoutParentheses() throws Exception {
        // Asserts the condition evaluation is done from left to right.
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, OR_TOKEN,
                PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, AND_TOKEN, PREFIX_TAG_TOKEN, STRING_THREE_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.generate();

        Coin coin0 = new CoinBuilder().withTags().build();
        Coin coin1 = new CoinBuilder().withTags(STRING_THREE_STRING).build();
        Coin coin2 = new CoinBuilder().withTags(STRING_TWO_STRING).build();
        Coin coin3 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();
        Coin coin4 = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin coin5 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
        Coin coin6 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
        Coin coin7 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING, STRING_THREE_STRING).build();

        assertFalse(condition.test(coin0));
        assertFalse(condition.test(coin1));
        assertFalse(condition.test(coin2));
        assertTrue(condition.test(coin3));
        assertFalse(condition.test(coin4));
        assertTrue(condition.test(coin5));
        assertFalse(condition.test(coin6));
        assertTrue(condition.test(coin7));
    }

    @Test
    public void generate_correctOrderOfPrecedenceWithParentheses() throws Exception {
        // Asserts the conditions in parentheses are prioritized.
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, OR_TOKEN,
                LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, AND_TOKEN, PREFIX_TAG_TOKEN, STRING_THREE_TOKEN,
                RIGHT_PAREN_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = conditionGenerator.generate();

        Coin coin0 = new CoinBuilder().withTags().build();
        Coin coin1 = new CoinBuilder().withTags(STRING_THREE_STRING).build();
        Coin coin2 = new CoinBuilder().withTags(STRING_TWO_STRING).build();
        Coin coin3 = new CoinBuilder().withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();
        Coin coin4 = new CoinBuilder().withTags(STRING_ONE_STRING).build();
        Coin coin5 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
        Coin coin6 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
        Coin coin7 = new CoinBuilder().withTags(STRING_ONE_STRING, STRING_TWO_STRING, STRING_THREE_STRING).build();

        assertFalse(condition.test(coin0));
        assertFalse(condition.test(coin1));
        assertFalse(condition.test(coin2));
        assertTrue(condition.test(coin3));
        assertTrue(condition.test(coin4));
        assertTrue(condition.test(coin5));
        assertTrue(condition.test(coin6));
        assertTrue(condition.test(coin7));
    }

    @Test
    public void getPredicateFromPrefix_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ConditionGenerator conditionGenerator = initGenerator(PREFIX_TAG_TOKEN, INVALID_TAG_NAME);
        conditionGenerator.generate();
    }

    private static ConditionGenerator initGenerator(Token... tokens) {
        return new ConditionGenerator(new TokenStack(new ArrayList<Token>(Arrays.asList(tokens))));
    }

}
