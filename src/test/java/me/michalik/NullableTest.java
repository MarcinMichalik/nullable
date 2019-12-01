package me.michalik;

import junit.framework.Assert;
import org.junit.Test;


public class NullableTest {

    @Test
    public void testEmpty() {
        Assert.assertFalse( Nullable.empty().isPresent());
    }

    @Test
    public void testOfNotNull() {
        Assert.assertTrue(Nullable.of("TEST").isPresent());
    }

    @Test
    public void testOfNull() {
        Assert.assertFalse(Nullable.of(null).isPresent());
    }

    @Test
    public void testGet() {
        Assert.assertEquals("TEST", Nullable.of("TEST").get());
    }

    @Test
    public void testIsPresent() {
        Assert.assertTrue(Nullable.of("TEST").isPresent());
        Assert.assertFalse(Nullable.of(null).isPresent());
    }

    @Test
    public void testIfPresent() {
        ValueChecker valueChecker = new ValueChecker(false);
        Nullable.of("TEST").ifPresent(s -> valueChecker.setValue(true));

        Assert.assertTrue(valueChecker.isValue());
    }

    @Test
    public void testIfNotPresent() {
        ValueChecker valueChecker = new ValueChecker(false);
        Nullable<String> stringNullable = Nullable.of(null);
        stringNullable.ifNotPresent(() -> valueChecker.setValue(true));

        Assert.assertTrue(valueChecker.isValue());
    }

    @Test
    public void testFilter() {
        Assert.assertTrue(Nullable.of("TEST").filter(s -> s.length()==4).isPresent());
        Assert.assertFalse(Nullable.of("xTESTx").filter(s -> s.length()==4).isPresent());
    }

    @Test
    public void testMap() {
        int len = Nullable.of("TEST").map(String::length).get();
        Assert.assertEquals(4, len);
    }

    @Test
    public void testFlatMap() {
        int len = Nullable.of("TEST").flatMap(s -> Nullable.of(s.length())).get();
        Assert.assertEquals(4, len);
    }

    @Test
    public void testOrElse() {
        Assert.assertEquals("X", Nullable.empty().orElse("X"));
    }

    @Test
    public void testOrElseGet() {
        Assert.assertEquals("X", Nullable.empty().orElseGet(() -> "X"));
    }

    @Test(expected = Exception.class)
    public void testOrElseThrow() throws Exception {
        Nullable.empty().orElseThrow(Exception::new);
    }

    private static class ValueChecker {
        private boolean value;

        public ValueChecker(boolean value) {
            this.value = value;
        }

        public ValueChecker() {
        }

        public boolean isValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }
}

