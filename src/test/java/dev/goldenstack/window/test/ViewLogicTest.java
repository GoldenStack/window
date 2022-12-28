package dev.goldenstack.window.test;

import dev.goldenstack.window.InventoryView;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViewLogicTest {

    @Test
    public void testContiguousConversion() {
        var view1 = InventoryView.contiguous(4, 12);
        assertSize(view1, 8);
        assertSlotRange(view1, 0, 4, 8);

        var view2 = InventoryView.contiguous(0, 22);
        assertSize(view2, 22);
        assertSlotRange(view2, 0, 0, 22);

        var view3 = InventoryView.contiguous(15000, 15010);
        assertSize(view3, 10);
        assertSlotRange(view3, 0, 15000, 10);

        assertThrows(IllegalArgumentException.class, () -> InventoryView.contiguous(-5, 1));
        assertThrows(IllegalArgumentException.class, () -> InventoryView.contiguous(1, -5));
        assertThrows(IllegalArgumentException.class, () -> InventoryView.contiguous(-50, -40));
        assertThrows(IllegalArgumentException.class, () -> InventoryView.contiguous(10, 4));
    }

    @Test
    public void testSingularConversion() {
        var view1 = InventoryView.singular(2);
        assertSize(view1, 1);
        assertSlotRange(view1, 0, 2, 1);

        var view2 = InventoryView.singular(0);
        assertSize(view2, 1);
        assertSlotRange(view2, 0, 0, 1);

        var view3 = InventoryView.singular(15000);
        assertSize(view2, 1);
        assertSlotRange(view3, 0, 15000, 1);

        assertThrows(IllegalArgumentException.class, () -> InventoryView.singular(-5));
    }

    @Test
    public void testUnionConversions() {
        var union1 = InventoryView.union(
                InventoryView.contiguous(30, 40),
                InventoryView.contiguous(10, 20)
        );
        assertSize(union1, 20);
        assertSlotRange(union1, 0, 30, 10);
        assertSlotRange(union1, 10, 10, 10);

        var union2 = InventoryView.union(
                InventoryView.contiguous(15000, 15010),
                InventoryView.contiguous(40, 50),
                InventoryView.contiguous(60, 70)
        );
        assertSize(union2, 30);
        assertSlotRange(union2, 0, 15000, 10);
        assertSlotRange(union2, 10, 40, 10);
        assertSlotRange(union2, 20, 60, 10);

        var union3 = InventoryView.union();
        assertSize(union3, 0);
    }

    @Test
    public void testOverlappingChildBounds() {
        var union1 = InventoryView.union(
                InventoryView.contiguous(10, 20),
                InventoryView.contiguous(10, 20)
        );

        assertSize(union1, 20);

        // Should only consider the first of the two children because they have overlapping slots
        assertSlotRange(union1, 0, 10, 10);
    }

    @Test
    public void testArbitraryForkConversions() {
        var view1 = InventoryView.arbitrary(10, 12, 14, 16, 18);
        assertSize(view1, 5);
        assertSlots(view1, IntList.of(0, 1, 2, 3, 4), IntList.of(10, 12, 14, 16, 18));

        var view2 = InventoryView.arbitrary(15000, 0, 10, 400);
        assertSize(view2, 4);
        assertSlots(view2, IntList.of(0, 1, 2, 3), IntList.of(15000, 0, 10, 400));

        var view3 = InventoryView.arbitrary();
        assertSize(view3, 0);

        assertThrows(IllegalArgumentException.class, () -> InventoryView.arbitrary(1, 2, 3, -4));
        assertThrows(IllegalArgumentException.class, () -> InventoryView.arbitrary(-500));
    }

    @Test
    public void testViewJoining() {
        var joined1 = InventoryView.join(
                InventoryView.contiguous(10, 60),
                InventoryView.contiguous(20, 30)
        );
        assertSize(joined1, 10);
        assertSlotRange(joined1, 0, 30, 10);

        var joined2 = InventoryView.join(
                InventoryView.contiguous(50, 15000),
                InventoryView.contiguous(40, 70)
        );
        assertSize(joined2, 30);
        assertSlotRange(joined2, 0, 90, 30);

        var joined3 = InventoryView.join(
                InventoryView.contiguous(50, 15000),
                InventoryView.singular(5)
        );
        assertSize(joined3, 1);
        assertSlotRange(joined3, 0, 55, 1);

        assertThrows(IllegalArgumentException.class, () -> InventoryView.join(
                InventoryView.contiguous(0, 10),
                InventoryView.contiguous(0, 20)
        ));

        assertThrows(IllegalArgumentException.class, () -> InventoryView.join(
                InventoryView.contiguous(10, 20),
                InventoryView.contiguous(0, 20)
        ));

        assertThrows(IllegalArgumentException.class, () -> InventoryView.join(
                InventoryView.contiguous(0, 10),
                InventoryView.contiguous(50, 70)
        ));
    }

    @Test
    public void testChildForking() {
        var view = InventoryView.contiguous(10, 20);

        var contiguous = view.forkRange(3, 7);
        assertSize(contiguous, 4);
        assertSlotRange(contiguous, 0, 13, 4);

        var singular = view.fork(6);
        assertSize(singular, 1);

        var arbitrary = view.fork(5, 7, 3);
        assertSize(arbitrary, 3);
        assertSlots(arbitrary, IntList.of(0, 1, 2), IntList.of(15, 17, 13));
    }

    private static void assertSize(@NotNull InventoryView view, int size) {
        assertEquals(size, view.size());
        assertLocalFailures(view, -1, size);
    }

    private static void assertSlotRange(@NotNull InventoryView view, int localStart, int externalStart, int size) {
        for (int i = 0; i < size; i++) {
            assertTrue(view.isValidLocal(localStart + i));
            assertTrue(view.isValidExternal(externalStart + i));

            assertEquals(externalStart + i, view.localToExternal(localStart + i));
            assertEquals(localStart + i, view.externalToLocal(externalStart + i));
        }
    }

    private static void assertSlots(@NotNull InventoryView view, @NotNull IntList localValues, @NotNull IntList externalValues) {
        for (int i = 0; i < localValues.size(); i++) {
            var local = localValues.getInt(i);
            var external = externalValues.getInt(i);

            // Local
            assertTrue(view.isValidLocal(local));
            assertEquals(external, view.localToExternal(local));

            // External
            assertTrue(view.isValidExternal(external));
            assertEquals(local, view.externalToLocal(external));
        }
    }

    private static void assertLocalFailures(@NotNull InventoryView view, int @NotNull ... localFailures) {
        for (var failure : localFailures) {
            assertFalse(view.isValidLocal(failure));
            assertEquals(-1, view.localToExternal(failure));
        }
    }

}
