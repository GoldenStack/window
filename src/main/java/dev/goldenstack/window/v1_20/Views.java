package dev.goldenstack.window.v1_20;

import dev.goldenstack.window.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * Stores commonly-used views of Minecraft inventories.
 * This is for version 1.20.
 */
@SuppressWarnings("unused")
public interface Views extends dev.goldenstack.window.v1_19.Views {

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

}
