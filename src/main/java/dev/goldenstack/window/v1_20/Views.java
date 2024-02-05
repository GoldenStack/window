package dev.goldenstack.window.v1_20;

import dev.goldenstack.window.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Stores commonly-used views of Minecraft inventories.
 * This is for version 1.20.
 */
@SuppressWarnings("unused")
public interface Views extends dev.goldenstack.window.v1_19.Views {

    static @NotNull Anvil anvil() {
        return Views.ANVIL;
    }

    static @NotNull Barrel barrel() {
        return Views.BARREL;
    }

    static @NotNull Beacon beacon() {
        return Views.BEACON;
    }

    static @NotNull BlastFurnace blastFurnace() {
        return Views.BLAST_FURNACE;
    }

    static @NotNull BrewingStand brewingStand() {
        return Views.BREWING_STAND;
    }

    static @NotNull CartographyTable cartographyTable() {
        return Views.CARTOGRAPHY_TABLE;
    }

    static @NotNull Chest chest() {
        return Views.CHEST;
    }

    static @NotNull CraftingTable craftingTable() {
        return Views.CRAFTING_TABLE;
    }

    static @NotNull Dispenser dispenser() {
        return Views.DISPENSER;
    }

    static @NotNull DoubleChest doubleChest() {
        return Views.DOUBLE_CHEST;
    }

    static @NotNull Dropper dropper() {
        return Views.DROPPER;
    }

    static @NotNull EnchantingTable enchantingTable() {
        return Views.ENCHANTING_TABLE;
    }

    static @NotNull EnderChest enderChest() {
        return Views.ENDER_CHEST;
    }

    static @NotNull Furnace furnace() {
        return Views.FURNACE;
    }

    static @NotNull Grindstone grindstone() {
        return Views.GRINDSTONE;
    }

    static @NotNull Hopper hopper() {
        return Views.HOPPER;
    }

    static @NotNull Lectern lectern() {
        return Views.LECTERN;
    }

    static @NotNull Loom loom() {
        return Views.LOOM;
    }

    static @NotNull Merchant merchant() {
        return Views.MERCHANT;
    }

    static @NotNull Player player() {
        return Views.PLAYER;
    }

    static @NotNull ShulkerBox shulkerBox() {
        return Views.SHULKER_BOX;
    }

    @NotNull Smithing SMITHING = new Smithing();

    static @NotNull Smithing smithing() {
        return Views.SMITHING;
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
        public static final @NotNull InventoryView.Singular TEMPLATE = Views.Smithing.VIEW.fork(0);
        public static final @NotNull InventoryView.Singular BASE = Views.Smithing.VIEW.fork(1);
        public static final @NotNull InventoryView.Singular ADDITION = Views.Smithing.VIEW.fork(2);
        public static final @NotNull InventoryView.Singular OUTPUT = Views.Smithing.VIEW.fork(3);

        public Smithing() {
            this(VIEW, TEMPLATE, BASE, ADDITION, OUTPUT);
        }
    }

    static @NotNull Smoker smoker() {
        return Views.SMOKER;
    }

    static @NotNull Stonecutter stonecutter() {
        return Views.STONECUTTER;
    }

}
