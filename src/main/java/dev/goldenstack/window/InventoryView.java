package dev.goldenstack.window;

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import net.minestom.server.inventory.AbstractInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.StackingRule;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Provides a view into an inventory via the manipulation of slot IDs. They aren't tied to any specific inventory, so it
 * must be provided with each usage of the view that would need an inventory.<br>
 * <b>All valid slot IDs, regardless of the context, should be greater than or equal to zero.</b><br>
 * Note: Any changes that involve multiple slots may require clients to make sure that other threads can't mess them up.
 */
public interface InventoryView {

    /**
     * Creates a new view that provides a window into a contiguous section, with the local slot {@code 0} being mapped
     * to external slot {@code min} and the local slot {@code max - 1} being mapped to external slot
     * {@code max + min - 1}, and vice versa, including all values in-between.
     * @param externalMin the minimum external slot value (inclusive)
     * @param externalMax the maximum external slot value (exclusive)
     * @return an inventory view providing a window into the provided range
     */
    static @NotNull InventoryView contiguous(int externalMin, int externalMax) {
        return new InventoryViewImpl.ContiguousFork(externalMin, externalMax);
    }

    /**
     * Creates a new view that provides a window into a specific slot, with the external ID {@code slot} being mapped to
     * the local ID {@code 0}, and vice versa.
     * @param externalSlot the external slot to view
     * @return an inventory view providing a window into the provided slot
     */
    static @NotNull InventoryView.Singular singular(int externalSlot) {
        return new InventoryViewImpl.ContiguousFork(externalSlot, externalSlot + 1); // Add 1 because the max is exclusive
    }

    /**
     * Adds together the provided views into a new view that represents the union of all of them. Essentially, this
     * adds the local IDs of the provided views together into one solid block.<br>
     * For example, consider two views that each have local slot IDs 0 through 3. A union of the two would have local
     * slots 0 through 7, with 0-3 being mapped to the first view's slots 0-3 and with local slots 4-7 being mapped to
     * the second view's slots 0-3.
     * @param views the views to add together
     * @return the unionized view
     */
    static @NotNull InventoryView union(@NotNull InventoryView @NotNull ... views) {
        return new InventoryViewImpl.Union(List.of(views));
    }

    /**
     * Creates a new view, with the provided external slot IDs being mapped to local IDs linearly, from 0. This should
     * generally only be used for complex views that really require it.
     * @param externalSlots the external slot IDs to be mapped
     * @return the created view that represents the slots
     */
    static @NotNull InventoryView arbitrary(int @NotNull ... externalSlots) {
        return new InventoryViewImpl.Arbitrary(new IntImmutableList(externalSlots));
    }

    /**
     * Joins the two provided views, mapping the child's external slot IDs to the parent's local slot IDs.
     * @param parent the parent in this relationship
     * @param child the child in this relationship
     * @return a view joining the two provided views
     */
    static @NotNull InventoryView join(@NotNull InventoryView parent, @NotNull InventoryView child) {
        return new InventoryViewImpl.Joiner(parent, child);
    }

    /**
     * Joins the two provided views, mapping the child's external slot ID to one of the parent's local slot IDs. The
     * sole difference between this and {@link #join(InventoryView, InventoryView)} is that the child and returned value
     * are treated as singular views.
     * @param parent the parent in this relationship
     * @param child the child in this relationship
     * @return a view joining the two provided views
     */
    static @NotNull InventoryView.Singular join(@NotNull InventoryView parent, @NotNull InventoryView.Singular child) {
        return new InventoryViewImpl.Joiner(parent, child);
    }

    /**
     * A generic interface for a view that has only one slot, and thus can have simple getters and setters that don't
     * require a slot to be specified.<br>
     * This interface is meant to exclusively make it easier to use single-slot views, so it can be relied upon that
     * implementing this won't have any side effects (e.g. method overriding) and that it will simply add a new API.
     */
    interface Singular extends InventoryView {

        /**
         * Gets the item in the inventory. Because this interface assumes that inventories only have one slot, the local
         * slot ID can be omitted.
         * @param inv the inventory to get the item of
         * @return the item in the inventory
         */
        default @NotNull ItemStack get(@NotNull AbstractInventory inv) {
            return get(inv, 0);
        }

        /**
         * Sets the item in the inventory to the provided item. Because this interface assumes that inventories only
         * have one slot, the local slot ID can be omitted.
         * @param inv the inventory to set the item of
         * @param item the item that the inventory should be set to
         */
        default void set(@NotNull AbstractInventory inv, @NotNull ItemStack item) {
            set(inv, 0, item);
        }

    }

    /**
     * Returns the size of this view, which is the number of slots that it has.<br>
     * This number must always be greater than or equal to zero, and it indicates that the local slot IDs of 0
     * (inclusive) to {@code size()} (exclusive) must always be valid. With a size of zero, no IDs are valid.<br>
     * This value should, when possible, kept constant for each view.
     * @return the size of this view
     */
    int size();

    /**
     * Converts the local slot ID, which is between (inclusive) 0 and (exclusive) {@link #size()}, to a valid "external"
     * slot ID. Importantly, this resultant "external" ID may be converted further, so, when considering a tree-based
     * example, it should only convert it to an ID that would be valid to its parent.<br>
     * If the provided slot ID is invalid, behaviour is undefined, but -1 should generally be returned.<br>
     * This value should, when possible, be constant for each local slot ID.
     * @param localSlot the local slot ID to convert
     * @return the non-local slot ID
     */
    int localToExternal(int localSlot);

    /**
     * Returns whether or not the provided local slot ID is valid in this view. Generally, {@link #localToExternal(int)}
     * should return an invalid ID (e.g. -1) if and only if this method returns false.<br>
     * This value should, when possible, be constant for each local slot ID.
     * @param localSlot the local slot ID to verify
     * @return true if the id is valid, and false if not
     */
    default boolean isValidLocal(int localSlot) {
        return localSlot >= 0 && localSlot < size();
    }

    /**
     * Creates a new view that provides a window into a contiguous section of this inventory, following the same
     * semantics as {@link #contiguous(int, int)} except for that the new view's external IDs are equivalent to the
     * local IDs for this view.
     * @param localMin the minimum local slot value
     * @param localMax the maximum local slot value
     * @return an inventory view providing a window into the provided range of this inventory
     */
    default @NotNull InventoryView forkRange(int localMin, int localMax) {
        return join(this, contiguous(localMin, localMax));
    }

    /**
     * Creates a new view that provides a window into a specific slot, following the same semantics as
     * {@link #singular(int)} except for that the new view's external ID is equal to the provided local ID for this
     * view.
     * @param localSlot the local slot to view
     * @return an inventory view providing a window into the specific slot of this inventory
     */
    default @NotNull InventoryView.Singular fork(int localSlot) {
        return join(this, singular(localSlot));
    }

    /**
     * Creates a new view that provides a window into the provided slots, from 0, following the same semantics as
     * {@link #arbitrary(int...)}.
     * @param localSlots the local slots to incorporate into a view
     * @return an inventory view providing a window into the provided external slot IDs, preserving their order
     */
    default @NotNull InventoryView fork(int @NotNull ... localSlots) {
        return join(this, arbitrary(localSlots));
    }

    /**
     * Gets the item at location of the provided local slot ID in the provided inventory.
     * @param inv the inventory to get the item from
     * @param localSlot the specific local slot to read
     * @return the item at the slot in the inventory
     */
    default @NotNull ItemStack get(@NotNull AbstractInventory inv, int localSlot) {
        // No need to verify if the slot is valid in the inventory because the inventory will handle it
        return inv.getItemStack(localToExternal(localSlot));
    }

    /**
     * Sets the item at the location of the provided local slot ID in the provided inventory to the item.
     * @param inv the inventory to set the item in
     * @param localSlot the specific local slot to set
     * @param item the item to set the slot to
     */
    default void set(@NotNull AbstractInventory inv, int localSlot, @NotNull ItemStack item) {
        // No need to verify if the slot is valid in the inventory because the inventory will handle it
        inv.setItemStack(localToExternal(localSlot), item);
    }

    /**
     * Adds the provided item to the provided inventory, reducing the count of the item when applicable, following the
     * rules of the stacking rule ({@link StackingRule#get()}).
     * @param inv the inventory to add items to
     * @param item the item to add to the inventory
     * @return the remaining item after adding
     */
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    default @NotNull ItemStack add(@NotNull AbstractInventory inv, @NotNull ItemStack item) {
        var rule = StackingRule.get();

        synchronized (inv) {
            for (int slot = 0; slot < size(); slot++) {
                var get = get(inv, slot);
                if (!get.isAir() && rule.canBeStacked(item, get)) {
                    var getAmount = rule.getAmount(get);
                    var max = rule.getMaxSize(get);
                    if (getAmount < max) {
                        var itemAmount = rule.getAmount(item);
                        var total = itemAmount + getAmount;
                        if (rule.canApply(item, total)) {
                            set(inv, slot, rule.apply(get, total));
                            return rule.apply(item, 0);
                        } else {
                            set(inv, slot, rule.apply(get, max));
                            item = rule.apply(item, total - max);
                        }
                    }
                }
            }

            for (int slot = 0; slot < size(); slot++) {
                if (get(inv, slot).isAir()) {
                    set(inv, slot, item);
                    return rule.apply(item, 0);
                }
            }
        }

        return item;
    }

    /**
     * Fills the provided inventory with items, according to what the filler provides. Each valid local slot ID will be
     * provided to the filler exactly once.
     * @param inv the inventory to fill
     * @param filler the function that provides items for each slot
     */
    default void fill(@NotNull AbstractInventory inv, @NotNull Int2ObjectFunction<@NotNull ItemStack> filler) {
        for (int i = 0; i < size(); i++) {
            set(inv, i, filler.apply(i));
        }
    }

    /**
     * Clears this inventory by setting each slot to air.
     * @param inv the inventory to clear
     */
    default void clear(@NotNull AbstractInventory inv) {
        fill(inv, slot -> ItemStack.AIR);
    }

    /**
     * Collects the items in the provided inventory into a list. This list is guaranteed to have a size of
     * {@link #size()}, and the items in the list are guaranteed to be non-null. The items in the list are collected
     * from local slot IDs.
     * @param inv the source of the items
     * @return the list of items from the inventory
     */
    default @NotNull List<@NotNull ItemStack> collect(@NotNull AbstractInventory inv) {
        ItemStack[] items = new ItemStack[size()];
        for (int i = 0; i < size(); i++) {
            items[i] = get(inv, i);
        }
        return List.of(items);
    }

}