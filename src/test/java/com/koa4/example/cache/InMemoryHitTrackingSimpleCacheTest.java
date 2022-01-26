package com.koa4.example.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InMemoryHitTrackingSimpleCacheTest {

    @Test
    public void givenEmptyCache_whenContainsKey_thenReturnFalse() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();

        Assertions.assertFalse(unitUnderTest.containsKey("key"));
    }

    @Test
    public void givenKeyDoesNotExist_whenContainsKey_thenReturnFalse() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");

        Assertions.assertFalse(unitUnderTest.containsKey("key2"));
    }

    @Test
    public void givenKeyExists_whenContainsKey_thenReturnTrue() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");

        Assertions.assertTrue(unitUnderTest.containsKey("key1"));
    }

    @Test
    public void givenEmptyCache_whenGet_thenReturnNull() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();

        Assertions.assertNull(unitUnderTest.get("key"));
    }

    @Test
    public void givenKeyDoesNotExist_whenGet_thenReturnNull() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");

        Assertions.assertNull(unitUnderTest.get("key2"));
    }

    @Test
    public void givenKeyExists_whenGet_thenReturnValue() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");

        Assertions.assertEquals("value1", unitUnderTest.get("key1"));
    }

    @Test
    public void givenKeyExists_whenGet_thenIncrementHitCount() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");

        Assertions.assertEquals(1, unitUnderTest.topItems(1).get(0).getHitCount());
        unitUnderTest.get("key1");
        Assertions.assertEquals(2, unitUnderTest.topItems(1).get(0).getHitCount());
    }

    @Test
    public void givenKeyDoesNotExist_whenPut_thenKeyIsAdded() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");

        Assertions.assertEquals("value1", unitUnderTest.get("key1"));
    }

    @Test
    public void givenKeyAlreadyExists_whenPut_thenDoNothing() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");
        unitUnderTest.put("key1", "value2");

        Assertions.assertEquals("value1", unitUnderTest.get("key1"));
    }

    @Test
    public void givenEmptyCache_whenRequestTopThreeItems_thenReturnEmptyList() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();

        Assertions.assertEquals(0, unitUnderTest.topItems(3).size());
    }

    @Test
    public void givenCacheWithOneItem_whenRequestTopThreeItems_thenReturnOneItem() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");

        Assertions.assertEquals(1, unitUnderTest.topItems(3).size());
    }

    @Test
    public void givenCacheWithFourItems_whenRequestTopThreeItems_thenReturnThreeItems() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        unitUnderTest.put("key1", "value1");
        unitUnderTest.put("key2", "value2");
        unitUnderTest.put("key3", "value3");
        unitUnderTest.put("key4", "value4");

        Assertions.assertEquals(3, unitUnderTest.topItems(3).size());
    }

    @Test
    public void givenCacheWithFourItems_whenRequestTopThreeItems_thenReturnTopThreeItemsByHitCount() {
        InMemoryHitTrackingSimpleCache<String, String> unitUnderTest = new InMemoryHitTrackingSimpleCache<>();
        simulateNCacheHits(unitUnderTest, "key1", "value1", 1);
        simulateNCacheHits(unitUnderTest, "key2", "value2", 2);
        simulateNCacheHits(unitUnderTest, "key3", "value3", 3);
        simulateNCacheHits(unitUnderTest, "key4", "value4", 4);

        Assertions.assertEquals("key4", unitUnderTest.topItems(3).get(0).getKey());
        Assertions.assertEquals("value4", unitUnderTest.topItems(3).get(0).getValue());
        Assertions.assertEquals(4, unitUnderTest.topItems(3).get(0).getHitCount());

        Assertions.assertEquals("key3", unitUnderTest.topItems(3).get(1).getKey());
        Assertions.assertEquals("value3", unitUnderTest.topItems(3).get(1).getValue());
        Assertions.assertEquals(3, unitUnderTest.topItems(3).get(1).getHitCount());

        Assertions.assertEquals("key2", unitUnderTest.topItems(3).get(2).getKey());
        Assertions.assertEquals("value2", unitUnderTest.topItems(3).get(2).getValue());
        Assertions.assertEquals(2, unitUnderTest.topItems(3).get(2).getHitCount());
    }

    private static void simulateNCacheHits(InMemoryHitTrackingSimpleCache<String, String> unitUnderTest, String key, String value, int count) {
        unitUnderTest.put(key, value);
        for (int i = 0; i < count - 1; i++) {
            unitUnderTest.get(key);
        }
    }

}
