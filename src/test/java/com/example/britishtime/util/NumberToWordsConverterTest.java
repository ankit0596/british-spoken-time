package com.example.britishtime.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class NumberToWordsConverterTest {

    @Test
    @DisplayName("Singleton property - same instance returned every time")
    void singletonProperty_sameInstance() {
        NumberToWordsConverter instance1 = NumberToWordsConverter.getInstance();
        NumberToWordsConverter instance2 = NumberToWordsConverter.getInstance();

        assertSame(instance1, instance2, "Expected same instance for singleton");
    }

    @Test
    @DisplayName("Hour mapping - all 24 hours should map correctly")
    void hourMapping_allHours() {
        NumberToWordsConverter converter = NumberToWordsConverter.getInstance();

        assertEquals("twelve", converter.wordForHour(0));
        assertEquals("one", converter.wordForHour(1));
        assertEquals("twelve", converter.wordForHour(12)); // 12-hour wrap
        assertEquals("one", converter.wordForHour(13));
        assertEquals("eleven", converter.wordForHour(23));
    }

    @Test
    @DisplayName("Minute mapping - test specific known values")
    void minuteMapping_knownValues() {
        NumberToWordsConverter converter = NumberToWordsConverter.getInstance();

        assertEquals("o'clock", converter.wordForMinute(0));
        assertEquals("quarter", converter.wordForMinute(15));
        assertEquals("half", converter.wordForMinute(30));
        assertEquals("twenty-nine", converter.wordForMinute(29));
    }

    @Test
    @DisplayName("Minute mapping - all valid range should not throw exception")
    void minuteMapping_allValid() {
        NumberToWordsConverter converter = NumberToWordsConverter.getInstance();
        for (int i = 0; i <= 30; i++) {
            assertNotNull(converter.wordForMinute(i), "Minute " + i + " should have a mapping");
        }
    }

    @Test
    @DisplayName("Thread safety - all threads should get the same instance")
    void threadSafety_sameInstanceAcrossThreads() throws InterruptedException, ExecutionException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        Callable<NumberToWordsConverter> task = NumberToWordsConverter::getInstance;
        Set<NumberToWordsConverter> instances = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            Future<NumberToWordsConverter> future = executor.submit(task);
            instances.add(future.get());
        }

        executor.shutdown();
        assertEquals(1, instances.size(), "All threads should return the same singleton instance");
    }

    @Test
    @DisplayName("Concurrency stress test - multiple threads requesting hours/minutes")
    void concurrencyStressTest() throws InterruptedException {
        NumberToWordsConverter converter = NumberToWordsConverter.getInstance();

        Runnable hourTask = () -> {
            for (int i = 0; i < 1000; i++) {
                assertNotNull(converter.wordForHour(i % 24));
            }
        };

        Runnable minuteTask = () -> {
            for (int i = 0; i < 1000; i++) {
                assertNotNull(converter.wordForMinute(i % 31));
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 50; i++) {
            executor.execute(hourTask);
            executor.execute(minuteTask);
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS),
                "Executor should finish within timeout");
    }
}
