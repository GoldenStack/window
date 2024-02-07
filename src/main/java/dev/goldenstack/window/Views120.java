package dev.goldenstack.window;

import org.jetbrains.annotations.NotNull;

/**
 * Stores commonly-used views of Minecraft inventories.
 * This is for version 1.20.
 */
@SuppressWarnings("unused")
public interface Views120 extends Views119 {

    static @NotNull Anvil anvil() {
        return Views120.ANVIL;
    }

    static @NotNull Barrel barrel() {
        return Views120.BARREL;
    }

    static @NotNull Beacon beacon() {
        return Views120.BEACON;
    }

    static @NotNull BlastFurnace blastFurnace() {
        return Views120.BLAST_FURNACE;
    }

    static @NotNull BrewingStand brewingStand() {
        return Views120.BREWING_STAND;
    }

    static @NotNull CartographyTable cartographyTable() {
        return Views120.CARTOGRAPHY_TABLE;
    }

    static @NotNull Chest chest() {
        return Views120.CHEST;
    }

    static @NotNull CraftingTable craftingTable() {
        return Views120.CRAFTING_TABLE;
    }

    static @NotNull Dispenser dispenser() {
        return Views120.DISPENSER;
    }

    static @NotNull DoubleChest doubleChest() {
        return Views120.DOUBLE_CHEST;
    }

    static @NotNull Dropper dropper() {
        return Views120.DROPPER;
    }

    static @NotNull EnchantingTable enchantingTable() {
        return Views120.ENCHANTING_TABLE;
    }

    static @NotNull EnderChest enderChest() {
        return Views120.ENDER_CHEST;
    }

    static @NotNull Furnace furnace() {
        return Views120.FURNACE;
    }

    static @NotNull Grindstone grindstone() {
        return Views120.GRINDSTONE;
    }

    static @NotNull Hopper hopper() {
        return Views120.HOPPER;
    }

    static @NotNull Lectern lectern() {
        return Views120.LECTERN;
    }

    static @NotNull Loom loom() {
        return Views120.LOOM;
    }

    static @NotNull Merchant merchant() {
        return Views120.MERCHANT;
    }

    static @NotNull Player player() {
        return Views120.PLAYER;
    }

    static @NotNull ShulkerBox shulkerBox() {
        return Views120.SHULKER_BOX;
    }

    @NotNull Smithing SMITHING = new Smithing();

    static @NotNull Smithing smithing() {
        return Views120.SMITHING;
    }
    
    /**
     * Provides a view into a smithing table
     * @param view a contiguous section of slots 0 to 3
     * @param template {@link Smithing#VIEW} slot 0
     * @param base {@link Smithing#VIEW} slot 1
     * @param addition {@link Smithing#VIEW} slot 2
     * @param output {@link Smithing#VIEW} slot 3
     */
    record Smithing(@NotNull InventoryView view,
                    @NotNull InventoryView.Singular template,
                    @NotNull InventoryView.Singular base,
                    @NotNull InventoryView.Singular addition,
                    @NotNull InventoryView.Singular output) implements DelegateView {
        public static final @NotNull InventoryView VIEW = InventoryView.contiguous(0, 4);
        public static final @NotNull InventoryView.Singular TEMPLATE = Views120.Smithing.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular BASE = Views120.Smithing.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular ADDITION = Views120.Smithing.VIEW.fork(2);
        public static final @NotNull InventoryView.Singular OUTPUT = Views120.Smithing.VIEW.fork(3);

        public Smithing() {
            this(VIEW, TEMPLATE, BASE, ADDITION, OUTPUT);
        }
    }

    static @NotNull Smoker smoker() {
        return Views120.SMOKER;
    }

    static @NotNull Stonecutter stonecutter() {
        return Views120.STONECUTTER;
    }

}
